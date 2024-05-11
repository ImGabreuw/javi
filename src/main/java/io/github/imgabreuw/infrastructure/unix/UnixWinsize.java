package io.github.imgabreuw.infrastructure.unix;

import com.sun.jna.Structure;

@Structure.FieldOrder(value = {"ws_row", "ws_col", "ws_xpixel", "ws_ypixel"})
public class UnixWinsize extends Structure {
    public short ws_row, ws_col, ws_xpixel, ws_ypixel;
}
