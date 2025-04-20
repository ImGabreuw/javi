package io.github.imgabreuw.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum KeyCode {

    MOVE_UP("\033[A"),
    MOVE_DOWN("\033[B"),
    MOVE_RIGHT("\033[C"),
    MOVE_LEFT("\033[D"),
    MOVE_HOME("\033[H"),
    MOVE_END("\033[F"),
    PAGE_UP("\033[5"),
    PAGE_DOWN("\033[6")
    ;

    private final String code;
}
