package io.github.imgabreuw.commands;

import io.github.imgabreuw.infrastructure.libc.LibC;
import io.github.imgabreuw.infrastructure.libc.Termios;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuitCommand implements Command {

    private final Termios originalAttributes;

    @Override
    public void execute() {
        System.out.print("\033[2J");
        System.out.print("\033[H");

        LibC.INSTANCE.tcsetattr(LibC.SYSTEM_OUT_FD, LibC.TCSAFLUSH, originalAttributes);

        System.exit(0);
    }
}
