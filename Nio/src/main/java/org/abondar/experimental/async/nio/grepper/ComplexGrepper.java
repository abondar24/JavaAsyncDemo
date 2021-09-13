package org.abondar.experimental.async.nio.grepper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexGrepper {
    private final Pattern pattern;


    public ComplexGrepper(String regex, boolean ignoreCase) {
        this.pattern = Pattern.compile(regex, (ignoreCase) ? Pattern.CASE_INSENSITIVE : 0);
    }


    public MatchedLine[] grep(File file) throws IOException {
        List list = grepList(file);
        MatchedLine[] matches = new MatchedLine[list.size()];

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

        MatchedLine[] matches = new MatchedLine[aggregate.size()];
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

    public void doGrep(boolean oneByOne, String[] fileNames) throws IOException {

        if (oneByOne) {

            for (String fn : fileNames) {
                System.out.println(fn + ":");
                MatchedLine[]  matches = grep(fn);

                showResult(matches);
            }

        } else {
            File[] files = new File[fileNames.length];
            for (int i = 0; i < files.length; i++) {
                files[i] = new File((fileNames[i]));
            }
            MatchedLine[] matches = grep(files);
            showResult(matches);

        }
    }

    private void showResult(MatchedLine[] matches){
        Arrays.asList(matches).forEach(match -> System.out
                .printf("  %d [%d-%d]: %s\n", match.getLineNumber(),
                        match.start, (match.end - 1), match.getLineText()));
    };

    public static class MatchedLine {
        private final File file;
        private final int lineNumber;
        private final String lineText;
        private final int start;
        private final int end;

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

    }


}
