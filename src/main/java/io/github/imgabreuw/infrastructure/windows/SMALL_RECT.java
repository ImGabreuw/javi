package io.github.imgabreuw.infrastructure.windows;

import com.sun.jna.Structure;

public class SMALL_RECT extends Structure {
    public SMALL_RECT() {
    }

    public SMALL_RECT(SMALL_RECT org) {
        this(org.Top, org.Left, org.Bottom, org.Right);
    }

    public SMALL_RECT(short Top, short Left, short Bottom, short Right) {
        this.Top = Top;
        this.Left = Left;
        this.Bottom = Bottom;
        this.Right = Right;
    }

    public short Left;
    public short Top;
    public short Right;
    public short Bottom;

    private static String[] fieldOrder = {"Left", "Top", "Right", "Bottom"};

    @Override
    protected java.util.List<String> getFieldOrder() {
        return java.util.Arrays.asList(fieldOrder);
    }

    public short width() {
        return (short) (this.Right - this.Left);
    }

    public short height() {
        return (short) (this.Bottom - this.Top);
    }

}
