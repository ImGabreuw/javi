package io.github.imgabreuw.infrastructure.windows;

import com.sun.jna.Structure;

// typedef struct _CONSOLE_SCREEN_BUFFER_INFO {
//   COORD      dwSize;
//   COORD      dwCursorPosition;
//   WORD       wAttributes;
//   SMALL_RECT srWindow;
//   COORD      dwMaximumWindowSize;
// } CONSOLE_SCREEN_BUFFER_INFO;
public class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
    public COORD dwSize;
    public COORD dwCursorPosition;
    public short wAttributes;
    public SMALL_RECT srWindow;
    public COORD dwMaximumWindowSize;

    private static String[] fieldOrder = {"dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize"};

    @Override
    protected java.util.List<String> getFieldOrder() {
        return java.util.Arrays.asList(fieldOrder);
    }

    public int windowWidth() {
        return this.srWindow.width() + 1;
    }

    public int windowHeight() {
        return this.srWindow.height() + 1;
    }
}
