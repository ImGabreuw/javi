package io.github.imgabreuw.commands;

import io.github.imgabreuw.gateways.Terminal;
import io.github.imgabreuw.infrastructure.unix.UnixLibC;
import io.github.imgabreuw.infrastructure.unix.UnixTermios;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuitCommand implements Command {

    private final Terminal terminal;

    @Override
    public void execute() {
        System.out.print("\033[2J");
        System.out.print("\033[H");

        terminal.disableRawMode();

        System.exit(0);
    }
}
