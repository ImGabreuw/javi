package io.github.imgabreuw.usecases.editor;

import io.github.imgabreuw.gateways.LibCGateway;
import io.github.imgabreuw.usecases.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InitEditor implements UseCase<InitEditor.InputValues, InitEditor.OutputValues> {

    private final LibCGateway libCGateway;

    @Override
    public OutputValues execute(InputValues input) {
        var windowSize = libCGateway.getWindowSize();

        int columns = windowSize.ws_col;
        int rows = windowSize.ws_row;

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
