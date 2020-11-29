package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.javarx.data.Sound;
import rx.Observable;

import static rx.Observable.just;

public class MorseCommand implements Command {
    @Override
    public void execute() {
        just('S','p','a','r','t','a')
                .map(Character::toLowerCase)
                .flatMap(MorseCommand::toMorseCode)
                .subscribe(System.out::println);
    }

    public static Observable<Sound> toMorseCode(char ch) {
        switch (ch) {
            case 'a':
                return just(Sound.DI, Sound.DAH);
            case 'b':
                return just(Sound.DAH, Sound.DI, Sound.DI, Sound.DI);
            case 'c':
                return just(Sound.DAH, Sound.DI, Sound.DAH, Sound.DI);
            case 'd':
                return just(Sound.DAH, Sound.DI, Sound.DI);
            case 'e':
                return just(Sound.DI);
            case 'f':
                return just(Sound.DI, Sound.DI, Sound.DAH, Sound.DI);
            case 'g':
                return just(Sound.DAH, Sound.DAH, Sound.DI);
            case 'h':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DI);
            case 'i':
                return just(Sound.DI, Sound.DI);
            case 'j':
                return just(Sound.DI, Sound.DAH, Sound.DAH, Sound.DAH);
            case 'k':
                return just(Sound.DAH, Sound.DI, Sound.DAH);
            case 'l':
                return just(Sound.DI, Sound.DAH, Sound.DI, Sound.DI);
            case 'm':
                return just(Sound.DAH, Sound.DAH);
            case 'n':
                return just(Sound.DAH, Sound.DI);
            case 'o':
                return just(Sound.DAH, Sound.DAH, Sound.DAH);
            case 'p':
                return just(Sound.DI, Sound.DAH, Sound.DAH, Sound.DI);
            case 'q':
                return just(Sound.DAH, Sound.DAH, Sound.DI, Sound.DAH);
            case 'r':
                return just(Sound.DI, Sound.DAH, Sound.DI);
            case 's':
                return just(Sound.DI, Sound.DI, Sound.DI);
            case 't':
                return just(Sound.DAH);
            case 'u':
                return just(Sound.DI, Sound.DI, Sound.DAH);
            case 'v':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DAH);
            case 'w':
                return just(Sound.DI, Sound.DAH, Sound.DAH);
            case 'x':
                return just(Sound.DAH, Sound.DI, Sound.DI, Sound.DAH);
            case 'y':
                return just(Sound.DAH, Sound.DI, Sound.DAH, Sound.DAH);
            case 'z':
                return just(Sound.DAH, Sound.DAH, Sound.DI, Sound.DI);
            case '0':
                return just(Sound.DAH, Sound.DAH, Sound.DAH, Sound.DAH, Sound.DAH);
            case '1':
                return just(Sound.DI, Sound.DAH, Sound.DAH, Sound.DAH, Sound.DAH);
            case '2':
                return just(Sound.DI, Sound.DI, Sound.DAH, Sound.DAH, Sound.DAH);
            case '3':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DAH, Sound.DAH);
            case '4':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DI, Sound.DAH);
            case '5':
                return just(Sound.DI, Sound.DI, Sound.DI, Sound.DI, Sound.DI);
            case '6':
                return just(Sound.DAH, Sound.DI, Sound.DI, Sound.DI, Sound.DI);
            case '7':
                return just(Sound.DAH, Sound.DAH, Sound.DI, Sound.DI, Sound.DI);
            case '8':
                return just(Sound.DAH, Sound.DAH, Sound.DAH, Sound.DI, Sound.DI);
            case '9':
                return just(Sound.DAH, Sound.DAH, Sound.DAH, Sound.DAH, Sound.DI);
            default:
                return Observable.empty();
        }
    }
}
