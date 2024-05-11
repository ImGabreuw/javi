package io.github.imgabreuw.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ANSIEscape {

    CLEAR_SCREEN("\033[2J"),
    CURSOR_HOME("\033[H"),
    INVERT_COLORS("\033[7m"),
    RESET_FORMATTING("\033[0m");

    private final String code;

}
