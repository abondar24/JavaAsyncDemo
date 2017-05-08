package org.abondar.experimental.javaasyncdemo.nio;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Complex grep.  Features: instances tied to a specific regex and can be used many times
 * and they are thread safe
 */
public class ComplexGrep {
    private Pattern pattern;

    public ComplexGrep(Pattern pattern) {
        this.pattern = pattern;
    }

    public ComplexGrep(String regex, boolean ignoreCase) {
        this.pattern = Pattern.compile(regex, (ignoreCase) ? Pattern.CASE_INSENSITIVE : 0);

    }

    public ComplexGrep(String regex) {
        this(regex, false);
    }

    public MatchedLine[] grep(File file) throws IOException {
        List list = grepList(file);
        MatchedLine matches[] = new MatchedLine[list.size()];

        list.toArray(matches);
        return matches;
    }

    public MatchedLine[] grep(String fileName) throws IOException {
        return grep(new File(fileName));
    }

    public MatchedLine[] grep(File[] files) throws IOException {
        List aggregate = new ArrayList();

        for (int i = 0; i < files.length; i++) {
            List temp = grepList(files[i]);
            aggregate.addAll(temp);
        }

        MatchedLine matches[] = new MatchedLine[aggregate.size()];
        aggregate.toArray(matches);
        return matches;
    }

    private List grepList(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Doesn't exist: " + file);
        }

        if (!file.isFile()) {
            throw new IOException("Not a regular file: " + file);
        }

        if (!file.canRead()) {
            throw new IOException("Unreadable file: " + file);
        }

        ArrayList list = new ArrayList();
        FileReader fr = new FileReader(file);
        LineNumberReader lnr = new LineNumberReader(fr);
        Matcher matcher = this.pattern.matcher("");
        String line;

        while ((line = lnr.readLine()) != null) {
            matcher.reset(line);

            if (matcher.find()) {
                list.add(new MatchedLine(file, lnr.getLineNumber(),
                        line, matcher.start(), matcher.end()));
            }
        }

        lnr.close();

        return list;
    }

    public static class MatchedLine {
        private File file;
        private int lineNumber;
        private String lineText;
        private int start;
        private int end;

        MatchedLine(File file, int lineNumber, String lineText, int start, int end) {
            this.file = file;
            this.lineNumber = lineNumber;
            this.lineText = lineText;
            this.start = start;
            this.end = end;
        }


        public File getFile() {
            return this.file;
        }

        public int getLineNumber() {
            return this.lineNumber;
        }

        public String getLineText() {
            return lineText;
        }

        public int getStart() {
            return start;
        }


        public int getEnd() {
            return end;
        }
    }

    // parms : -i or --ignore-case - ignore case,
    // -l - grep each file separately, not together
    public static void main(String[] args) {
        boolean ignoreCase = false;
        boolean oneByOne = false;

        List argList = new LinkedList();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {

                if (args[i].equals("-i") || args[i].equals("--ignore-case")) {
                    ignoreCase = true;
                }

                if (args[i].equals("-l")) {
                    oneByOne = true;
                }

                continue;
            }

            argList.add(args[i]);
        }

        if (argList.size() < 2) {
            System.err.println("usage: [options] pattern filename...");
            System.exit(1);
        }

        ComplexGrep grepper = new ComplexGrep((String) argList.remove(0), ignoreCase);

        if (oneByOne) {
            Iterator it = argList.iterator();

            while (it.hasNext()) {
                String fileName = (String) it.next();

                System.out.println(fileName + ":");
                MatchedLine[] matches = null;

                try {
                    matches = grepper.grep(fileName);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    continue;
                }

                Arrays.asList(matches).forEach(match -> System.out
                        .printf("  %d [%d-%d]: %s\n", match.getLineNumber(),
                                match.start, (match.end - 1), match.getLineText()));

            }
        } else {
            File[] files = new File[argList.size()];
            for (int i = 0; i < files.length; i++) {
                files[i] = new File((String) argList.get(i));
            }

            try {
                MatchedLine[] matches = grepper.grep(files);
                Arrays.asList(matches).forEach(match -> System.out
                        .printf("%s, %d: %s\n", match.getFile().getName(),
                                match.getLineNumber(), match.getLineText()));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }

    }
}

