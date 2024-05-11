package io.github.imgabreuw.entities;

import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class Screen {

    private final StringBuilder screen = new StringBuilder();

    private final Editor editor;

    public static Screen builder(Editor editor) {
        return new Screen(editor);
    }

    public Screen setCursorToTopLeft() {
        screen.append("\033[H");
        return this;
    }

    public Screen drawCursor() {
        screen.append("\033[%d;%dH".formatted(
                editor.getCursorY() - editor.getOffsetY() + 1,
                editor.getCursorX() - editor.getOffsetX() + 1)
        );
        return this;
    }

    public Screen drawStatusBar() {
        String statusMessage = "X:" + editor.getCursorX() + " Y: " + editor.getCursorY();
        screen.append("\033[7m")
                .append(statusMessage)
                .append(" ".repeat(Math.max(0, editor.getColumns() - statusMessage.length())))
                .append("\033[0m");
        return this;
    }

    public Screen drawContent() {
        for (int i = 0; i < editor.getRows(); i++) {
            int lineIndex = editor.getOffsetY() + i;

            if (lineIndex >= editor.getContent().size()) {
                screen.append("~");
            } else {
                String line = editor.getContent().get(lineIndex);
                int lengthToDraw = line.length() - editor.getOffsetX();

                if (lengthToDraw < 0) {
                    lengthToDraw = 0;
                }
                if (lengthToDraw > editor.getColumns()) {
                    lengthToDraw = editor.getColumns();
                }

                if (lengthToDraw > 0) {
                    screen.append(line, editor.getOffsetX(), editor.getOffsetX() + lengthToDraw);
                }


            }
            screen.append("\033[K\r\n");
        }

        return this;
    }

    public String build() {
        return screen.toString();
    }

    public void render() {
        System.out.println(screen);
    }

}
