package io.github.imgabreuw.gateways;

import io.github.imgabreuw.infrastructure.libc.LibC;
import io.github.imgabreuw.infrastructure.libc.Termios;
import io.github.imgabreuw.infrastructure.libc.Winsize;

public class LibCGatewayImpl implements LibCGateway {
    private Termios originalAttributes;

    @Override
    public Termios getOriginalAttributes() {
        if (originalAttributes == null) {
            originalAttributes = new Termios();
            int rc = LibC.INSTANCE.tcgetattr(LibC.SYSTEM_OUT_FD, originalAttributes);
            if (rc != 0) {
                throw new RuntimeException("Failed to get terminal attributes");
            }
        }
        return Termios.of(originalAttributes);
    }

    @Override
    public void setTermios(Termios termios) {
        int rc = LibC.INSTANCE.tcsetattr(LibC.SYSTEM_OUT_FD, LibC.TCSAFLUSH, termios);
        if (rc != 0) {
            throw new RuntimeException("Failed to set terminal attributes");
        }
    }

    @Override
    public void restoreMode() {
        setTermios(originalAttributes);
    }

    @Override
    public Winsize getWindowSize() {
        Winsize winsize = new Winsize();
        int rc = LibC.INSTANCE.ioctl(LibC.SYSTEM_OUT_FD, LibC.TIOCGWINSZ, winsize);
        if (rc != 0) {
            throw new RuntimeException("Failed to get window size");
        }
        return winsize;
    }
}
