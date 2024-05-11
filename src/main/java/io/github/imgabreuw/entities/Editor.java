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
        cursorX = columns - 1;
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
}
