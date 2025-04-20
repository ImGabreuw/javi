package io.github.imgabreuw.usecases.screen;

import io.github.imgabreuw.entities.Editor;
import io.github.imgabreuw.entities.Screen;
import io.github.imgabreuw.usecases.UseCase;

public class RefreshScreen implements UseCase<RefreshScreen.InputValues, RefreshScreen.OutputValues> {

    @Override
    public OutputValues execute(InputValues input) {
        var editor = input.editor();

        Screen.builder(editor)
                .hideCursor()
                .setCursorToTopLeft()
                .clearScreen()
                .drawContent()
                .drawStatusBar()
                .drawCursor()
                .showCursor()
                .render();

        return new OutputValues();
    }

    public record InputValues(Editor editor) implements UseCase.InputValues {
    }

    public record OutputValues() implements UseCase.OutputValues {
    }

}
