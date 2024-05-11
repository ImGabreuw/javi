package io.github.imgabreuw.usecases.editor;

import io.github.imgabreuw.gateways.Terminal;
import io.github.imgabreuw.usecases.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InitEditor implements UseCase<InitEditor.InputValues, InitEditor.OutputValues> {

    private final Terminal terminal;

    @Override
    public OutputValues execute(InputValues input) {
        terminal.enableRawMode();
        var windowSize = terminal.getWindowSize();

        int columns = windowSize.columns();
        int rows = windowSize.rows() - 1;

        return new OutputValues(columns, rows);
    }

    public record InputValues() implements UseCase.InputValues {
    }

    public record OutputValues(
            int columns,
            int rows
    ) implements UseCase.OutputValues {
    }

}
