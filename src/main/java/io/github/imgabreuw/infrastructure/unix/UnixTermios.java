package io.github.imgabreuw.infrastructure.unix;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;


@Structure.FieldOrder(value = {"c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc"})
public class UnixTermios extends Structure {
    public int c_iflag, c_oflag, c_cflag, c_lflag;
    public byte[] c_cc = new byte[19];

    public UnixTermios() {
    }

    public static UnixTermios of(UnixTermios t) {
        UnixTermios copy = new UnixTermios();
        copy.c_iflag = t.c_iflag;
        copy.c_oflag = t.c_oflag;
        copy.c_cflag = t.c_cflag;
        copy.c_lflag = t.c_lflag;
        copy.c_cc = t.c_cc.clone();
        return copy;
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc");
    }

    @Override
    public String toString() {
        return "Termios{" +
                "c_iflag=" + c_iflag +
                ", c_oflag=" + c_oflag +
                ", c_cflag=" + c_cflag +
                ", c_lflag=" + c_lflag +
                ", c_cc=" + Arrays.toString(c_cc) +
                '}';
    }
}
