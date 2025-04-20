package io.github.imgabreuw.infrastructure.unix;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

@Structure.FieldOrder(value = {"ws_row", "ws_col", "ws_xpixel", "ws_ypixel"})
public class UnixWinsize extends Structure {

    public short ws_row, ws_col, ws_xpixel, ws_ypixel;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("ws_row", "ws_col", "ws_xpixel", "ws_ypixel");
    }

}
