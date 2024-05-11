package io.github.imgabreuw.gateways;

import com.sun.jna.Platform;
import io.github.imgabreuw.entities.WindowSize;
import io.github.imgabreuw.infrastructure.unix.UnixTerminal;
import io.github.imgabreuw.infrastructure.windows.WindowsTerminal;

public interface Terminal {

    void enableRawMode();

    void disableRawMode();

    WindowSize getWindowSize();

    static Terminal getInstance() {
        if (Platform.isWindows()) {
            return new WindowsTerminal();
        }

        return new UnixTerminal();
    }

}
