package io.github.imgabreuw.usecases.keys;

import io.github.imgabreuw.commands.Command;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final Map<String, Command> commands = new HashMap<>();

    public void register(String trigger, Command command) {
        commands.put(trigger, command);
    }

    @SneakyThrows
    public void listen() {
        StringBuilder buffer = new StringBuilder();

        while (commands.get(buffer.toString()) == null) {
            int input = System.in.read();

            buffer.append((char) input);
            String key = buffer.toString();

            if (commands.keySet().stream().noneMatch(k -> k.startsWith(key))) {
                return;
            }

        }

        Command command = commands.get(buffer.toString());

        if (command != null) {
            command.execute();
        }
    }
}
