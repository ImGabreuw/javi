package io.github.imgabreuw.commands;

import io.github.imgabreuw.infrastructure.unix.UnixLibC;
import io.github.imgabreuw.infrastructure.unix.UnixTermios;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuitCommand implements Command {

    private final UnixTermios originalAttributes;

    @Override
    public void execute() {
        System.out.print("\033[2J");
        System.out.print("\033[H");

        UnixLibC.INSTANCE.tcsetattr(UnixLibC.SYSTEM_OUT_FD, UnixLibC.TCSAFLUSH, originalAttributes);

        System.exit(0);
    }
}
