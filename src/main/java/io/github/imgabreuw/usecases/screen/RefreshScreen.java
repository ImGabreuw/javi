package io.github.imgabreuw.usecases.screen;

import io.github.imgabreuw.usecases.UseCase;

public class RefreshScreen implements UseCase<RefreshScreen.InputValues, RefreshScreen.OutputValues> {

    @Override
    public OutputValues execute(InputValues input) {
        var columns = input.columns();
        var rows = input.rows();

        String statusMessage = "JaVI - v1.0-SNAPSHOT";

        String content = "\033[2J" +
                "\033[H" +
                "~\r\n".repeat(Math.max(0, rows - 1)) +
                "\033[7m" +
                statusMessage +
                " ".repeat(Math.max(0, columns - statusMessage.length())) +
                "\033[0m" +
                "\033[H";

        System.out.println(content);

        return new OutputValues();
    }

    public record InputValues(int columns, int rows) implements UseCase.InputValues {
    }

    public record OutputValues() implements UseCase.OutputValues {
    }

}
