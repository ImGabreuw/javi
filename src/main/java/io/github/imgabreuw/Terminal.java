package io.github.imgabreuw;

import io.github.imgabreuw.commands.QuitCommand;
import io.github.imgabreuw.gateways.LibCGateway;
import io.github.imgabreuw.gateways.LibCGatewayImpl;
import io.github.imgabreuw.usecases.editor.EnableRawMode;
import io.github.imgabreuw.usecases.editor.InitEditor;
import io.github.imgabreuw.usecases.keys.KeyObserver;
import io.github.imgabreuw.usecases.screen.RefreshScreen;

public class Terminal {

    public static void main(String[] args) {
        LibCGateway libCGateway = new LibCGatewayImpl();

        var enableRawMode = new EnableRawMode();
        var originalAttributes = enableRawMode
                .execute(new EnableRawMode.InputValues())
                .termios();

        var editor = new InitEditor(libCGateway);
        var windowSize = editor.execute(new InitEditor.InputValues());

        var refreshScreen = new RefreshScreen();
        var inputValues = new RefreshScreen.InputValues(
                windowSize.columns(),
                windowSize.rows()
        );

        var keyObserver = new KeyObserver();
        keyObserver.register('q', new QuitCommand(originalAttributes));

        while (true) {
            refreshScreen.execute(inputValues);
            keyObserver.listen();
        }
    }

}
