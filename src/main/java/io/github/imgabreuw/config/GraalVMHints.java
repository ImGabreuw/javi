package io.github.imgabreuw.config;

import io.github.imgabreuw.commands.*;
import io.github.imgabreuw.entities.Editor;
import io.github.imgabreuw.gateways.Terminal;
import org.springframework.aot.hint.*;

public class GraalVMHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection()
                .registerType(Terminal.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(Editor.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(QuitCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(MoveUpCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(MoveDownCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(MoveLeftCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(MoveRightCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(MoveToHomeCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(MoveToEndCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(PageUpCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(PageDownCommand.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(io.github.imgabreuw.infrastructure.unix.UnixTermios.class, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(io.github.imgabreuw.infrastructure.unix.UnixLibC.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_PUBLIC_METHODS);
        ;

        hints.proxies()
                .registerJdkProxy(io.github.imgabreuw.infrastructure.unix.UnixLibC.class)
        ;


        hints.jni()
                .registerType(System.class, type ->
                        type.withMethod("setProperty", TypeReference.listOf(String.class, String.class), ExecutableMode.INTROSPECT)
                )
                .registerType(com.sun.jna.Pointer.class, type ->
                        type.withField("peer")
                )
                .registerType(io.github.imgabreuw.infrastructure.unix.UnixTermios.class, type ->
                        type.withField("c_iflag")
                                .withField("c_oflag")
                                .withField("c_cflag")
                                .withField("c_lflag")
                                .withField("c_cc")
                                .withMethod("getFieldOrder", TypeReference.listOf(), ExecutableMode.INTROSPECT)
                )
                .registerType(io.github.imgabreuw.infrastructure.unix.UnixLibC.class)
        ;
    }

}