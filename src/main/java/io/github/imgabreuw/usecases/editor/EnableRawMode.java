package io.github.imgabreuw.usecases.editor;

import io.github.imgabreuw.infrastructure.unix.UnixLibC;
import io.github.imgabreuw.infrastructure.unix.UnixTermios;
import io.github.imgabreuw.usecases.UseCase;

public class EnableRawMode implements UseCase<EnableRawMode.InputValues, EnableRawMode.OutputValues> {

    @Override
    public OutputValues execute(InputValues input) {
        UnixTermios unixTermios = new UnixTermios();
        int rc = UnixLibC.INSTANCE.tcgetattr(UnixLibC.SYSTEM_OUT_FD, unixTermios);

        if (rc != 0) {
            System.err.println("There was a problem calling tcgetattr");
            System.exit(rc);
        }

        UnixTermios originalAttributes = UnixTermios.of(unixTermios);

        unixTermios.c_lflag &= ~(UnixLibC.ECHO | UnixLibC.ICANON | UnixLibC.IEXTEN | UnixLibC.ISIG);
        unixTermios.c_iflag &= ~(UnixLibC.IXON | UnixLibC.ICRNL);
        unixTermios.c_oflag &= ~(UnixLibC.OPOST);

        UnixLibC.INSTANCE.tcsetattr(UnixLibC.SYSTEM_OUT_FD, UnixLibC.TCSAFLUSH, unixTermios);

        return new OutputValues(originalAttributes);
    }

    public record InputValues() implements UseCase.InputValues {
    }

    public record OutputValues(UnixTermios unixTermios) implements UseCase.OutputValues {
    }

}
