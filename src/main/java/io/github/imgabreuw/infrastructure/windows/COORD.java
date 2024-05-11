package io.github.imgabreuw.infrastructure.windows;

import com.sun.jna.Structure;

// typedef struct _COORD {
//    SHORT X;
//    SHORT Y;
//  } COORD, *PCOORD;
public class COORD extends Structure implements Structure.ByReference {
    public COORD() {
    }

    public COORD(short X, short Y) {
        this.X = X;
        this.Y = Y;
    }

    public short X;
    public short Y;

    private static String[] fieldOrder = {"X", "Y"};

    @Override
    protected java.util.List<String> getFieldOrder() {
        return java.util.Arrays.asList(fieldOrder);
    }
}
