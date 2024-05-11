package io.github.imgabreuw.usecases.editor;

import io.github.imgabreuw.infrastructure.libc.LibC;
import io.github.imgabreuw.infrastructure.libc.Termios;
import io.github.imgabreuw.usecases.UseCase;

public class EnableRawMode implements UseCase<EnableRawMode.InputValues, EnableRawMode.OutputValues> {

    @Override
    public OutputValues execute(InputValues input) {
        Termios termios = new Termios();
        int rc = LibC.INSTANCE.tcgetattr(LibC.SYSTEM_OUT_FD, termios);

        if (rc != 0) {
            System.err.println("There was a problem calling tcgetattr");
            System.exit(rc);
        }

        Termios originalAttributes = Termios.of(termios);

        termios.c_lflag &= ~(LibC.ECHO | LibC.ICANON | LibC.IEXTEN | LibC.ISIG);
        termios.c_iflag &= ~(LibC.IXON | LibC.ICRNL);
        termios.c_oflag &= ~(LibC.OPOST);

        LibC.INSTANCE.tcsetattr(LibC.SYSTEM_OUT_FD, LibC.TCSAFLUSH, termios);

        return new OutputValues(originalAttributes);
    }

    public record InputValues() implements UseCase.InputValues {
    }

    public record OutputValues(Termios termios) implements UseCase.OutputValues {
    }

}
