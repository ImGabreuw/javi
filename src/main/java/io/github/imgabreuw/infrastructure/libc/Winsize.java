package io.github.imgabreuw.infrastructure.libc;

import com.sun.jna.Structure;

@Structure.FieldOrder(value = {"ws_row", "ws_col", "ws_xpixel", "ws_ypixel"})
public class Winsize extends Structure {
    public short ws_row, ws_col, ws_xpixel, ws_ypixel;
}
