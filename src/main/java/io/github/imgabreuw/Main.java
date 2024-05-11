package io.github.imgabreuw;

import io.github.imgabreuw.commands.*;
import io.github.imgabreuw.entities.Editor;
import io.github.imgabreuw.gateways.Terminal;
import io.github.imgabreuw.usecases.editor.EnableRawMode;
import io.github.imgabreuw.usecases.editor.InitEditor;
import io.github.imgabreuw.usecases.editor.OpenFile;
import io.github.imgabreuw.usecases.keys.CommandHandler;
import io.github.imgabreuw.usecases.screen.RefreshScreen;

public class Main {

    public static void main(String[] args) {
        Terminal terminal = Terminal.getInstance();

        var enableRawMode = new EnableRawMode();
        var originalAttributes = enableRawMode
                .execute(new EnableRawMode.InputValues())
                .unixTermios();

        var initEditor = new InitEditor(terminal);
        var windowSize = initEditor.execute(new InitEditor.InputValues());

        var openFile = new OpenFile();
        var fileContent = openFile
                .execute(new OpenFile.InputValues(args))
                .content();

        var editor = new Editor(fileContent, windowSize.columns(), windowSize.rows());

        var refreshScreen = new RefreshScreen();

        var commandHandler = new CommandHandler();
        commandHandler.register(":q", new QuitCommand(originalAttributes));
        commandHandler.register("\033[A", new MoveUpCommand(editor));
        commandHandler.register("\033[B", new MoveDownCommand(editor));
        commandHandler.register("\033[C", new MoveRightCommand(editor));
        commandHandler.register("\033[D", new MoveLeftCommand(editor));
        commandHandler.register("\033[H", new MoveToHomeCommand(editor));
        commandHandler.register("\033[F", new MoveToEndCommand(editor));
        commandHandler.register("\033[5", new PageUpCommand(editor));
        commandHandler.register("\033[6", new PageDownCommand(editor));

        while (true) {
            editor.scroll();
            refreshScreen.execute(new RefreshScreen.InputValues(editor));
            commandHandler.listen();
        }
    }

}
