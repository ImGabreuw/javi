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
        String statusMessage = "X:" + editor.getCursorX() + "/" + editor.getColumns() + " Y: " + editor.getCursorY() + "/" + editor.getRows();
        screen.append("\033[7m")
                .append(statusMessage)
                .append(" ".repeat(Math.max(0, editor.getColumns() - statusMessage.length())))
                .append("\033[0m");
        return this;
    }

    public Screen drawContent() {
        for (int i = 0; i < editor.getRows(); i++) {
            int fileRow = i + editor.getOffsetY();

            if (fileRow >= editor.getContent().size()) {
                screen.append("~");
            } else {
                var line = renderLine(editor.getContent().get(i), editor.getOffsetX(), editor.getColumns());
                screen.append(line);
            }

            screen.append("\033[K\r\n");
        }

        return this;
    }

    private String renderLine(String line, int columnOffset, int screenColumns) {
        int length = Math.max(0, line.length() - columnOffset);
        if (length == 0) return "";

        if (length > screenColumns) length = screenColumns;
        return line.substring(columnOffset, columnOffset + length);
    }

    public String build() {
        return screen.toString();
    }

    public void render() {
        System.out.println(screen);
    }

}
