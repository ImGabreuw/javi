package io.github.imgabreuw.entities;

import lombok.Getter;

import java.util.List;

@Getter
public class Editor {
    private final List<String> content;
    private final int columns, rows;

    private int cursorX, cursorY, offsetY, offsetX;

    public Editor(List<String> content, int columns, int rows) {
        this.content = content;
        this.columns = columns;
        this.rows = rows;
        this.cursorX = 0;
        this.cursorY = 0;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void moveUp() {
        if (cursorY > 0) {
            cursorY--;
        }
    }

    public void moveDown() {
        if (cursorY < content.size()) {
            cursorY++;
        }
    }

    public void moveLeft() {
        if (cursorX > 0) {
            cursorX--;
        }
    }

    public void moveRight() {
        if (cursorX < content.get(cursorY).length()) {
            cursorX++;
        }
    }

    public void moveToHome() {
        cursorX = 0;
    }

    public void moveToEnd() {
        var line = currentLine();

        if (line != null) {
            cursorX = line.length();
        }
    }

    public void pageUp() {
        cursorY = offsetY;

        for (int i = 0; i < rows; i++) {
            moveUp();
        }
    }

    public void pageDown() {
        cursorY = offsetY + rows - 1;

        if (cursorY > content.size()) {
            cursorY = content.size();
        }

        for (int i = 0; i < rows; i++) {
            moveDown();
        }
    }

    public void scroll() {
        if (cursorY >=  rows + offsetY) {
            offsetY = cursorY - rows + 1;
        }

        if (cursorY < offsetY) {
            offsetY = cursorY;
        }

        if (cursorX >=  columns + offsetX) {
            offsetX = cursorX - columns + 1;
        }

        if (cursorX < offsetX) {
            offsetX = cursorX;
        }
    }

    public void defaultMove() {
        String newLine = currentLine();

        if (newLine != null && cursorX > newLine.length()) {
            cursorX = newLine.length();
        }
    }

    private String currentLine() {
        return cursorY < content.size() ? content.get(cursorY) : null;
    }
}
