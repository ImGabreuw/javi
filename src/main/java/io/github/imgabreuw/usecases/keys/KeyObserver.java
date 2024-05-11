package io.github.imgabreuw.usecases.keys;

import io.github.imgabreuw.commands.Command;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class KeyObserver {

    private final Map<Integer, Command> keyCommandMap = new HashMap<>();

    public void register(char code, Command command) {
        keyCommandMap.put((int) code, command);
    }

    @SneakyThrows
    public void listen() {
        var commandTrigger = System.in.read();

        if (commandTrigger != ':')
            return;

        var keyCode = System.in.read();
        pressedKey(keyCode);
    }

    private void pressedKey(int keyCode) {
        Optional
                .ofNullable(keyCommandMap.get(keyCode))
                .ifPresent(Command::execute);
    }
}
