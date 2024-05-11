package io.github.imgabreuw.infrastructure.unix;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface UnixLibC extends Library {

    int SYSTEM_OUT_FD = 0;

    int ISIG = 1,
            ICANON = 2,
            ECHO = 10,
            TCSAFLUSH = 2,
            IXON = 2000,
            ICRNL = 400,
            IEXTEN = 100000,
            OPOST = 1,
            VMIN = 6,
            VTIME = 5,
            TIOCGWINSZ = 0x5413;


    UnixLibC INSTANCE = Native.load("c", UnixLibC.class);

    int tcgetattr(int fd, UnixTermios unixTermios);

    int tcsetattr(int fd, int optional_actions,
                  UnixTermios unixTermios);

    int ioctl(int fd, int opt, UnixWinsize unixWinsize);
}
