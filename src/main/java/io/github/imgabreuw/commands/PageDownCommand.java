package io.github.imgabreuw.commands;

import io.github.imgabreuw.entities.Editor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PageDownCommand implements Command {

    private final Editor editor;

    @Override
    public void execute() {
        editor.pageDown();
    }
}
