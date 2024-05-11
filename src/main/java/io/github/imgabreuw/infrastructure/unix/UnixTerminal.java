package io.github.imgabreuw.infrastructure.unix;

import io.github.imgabreuw.entities.WindowSize;
import io.github.imgabreuw.gateways.Terminal;

public class UnixTerminal implements Terminal {

    private static UnixTermios originalAttributes;

    @Override
    public void enableRawMode() {
        UnixTermios unixTermios = new UnixTermios();
        int rc = UnixLibC.INSTANCE.tcgetattr(UnixLibC.SYSTEM_OUT_FD, unixTermios);

        if (rc != 0) {
            System.err.println("There was a problem calling tcgetattr");
            System.exit(rc);
        }

        originalAttributes = UnixTermios.of(unixTermios);

        unixTermios.c_lflag &= ~(UnixLibC.ECHO | UnixLibC.ICANON | UnixLibC.IEXTEN | UnixLibC.ISIG);
        unixTermios.c_iflag &= ~(UnixLibC.IXON | UnixLibC.ICRNL);
        unixTermios.c_oflag &= ~(UnixLibC.OPOST);

        UnixLibC.INSTANCE.tcsetattr(UnixLibC.SYSTEM_OUT_FD, UnixLibC.TCSAFLUSH, unixTermios);
    }

    @Override
    public void disableRawMode() {
        UnixLibC.INSTANCE.tcsetattr(UnixLibC.SYSTEM_OUT_FD, UnixLibC.TCSAFLUSH, originalAttributes);
    }

    @Override
    public WindowSize getWindowSize() {
        final UnixWinsize unixWinsize = new UnixWinsize();

        final int rc = UnixLibC.INSTANCE.ioctl(UnixLibC.SYSTEM_OUT_FD, UnixLibC.INSTANCE.TIOCGWINSZ, unixWinsize);

        if (rc != 0) {
            System.err.println("ioctl failed with return code[={}]" + rc);
            System.exit(1);
        }

        return new WindowSize(unixWinsize.ws_row, unixWinsize.ws_col);
    }

}
