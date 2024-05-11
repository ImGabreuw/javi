package io.github.imgabreuw.gateways;

import io.github.imgabreuw.infrastructure.libc.Termios;
import io.github.imgabreuw.infrastructure.libc.Winsize;

public interface LibCGateway {

    Termios getOriginalAttributes();

    void setTermios(Termios termios);

    void restoreMode();

    Winsize getWindowSize();

}
