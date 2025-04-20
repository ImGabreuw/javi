package io.github.imgabreuw;

import io.github.imgabreuw.commands.*;
import io.github.imgabreuw.config.GraalVMHints;
import io.github.imgabreuw.entities.Editor;
import io.github.imgabreuw.entities.Screen;
import io.github.imgabreuw.gateways.Terminal;
import io.github.imgabreuw.shared.KeyCode;
import io.github.imgabreuw.usecases.editor.InitEditor;
import io.github.imgabreuw.usecases.editor.OpenFile;
import io.github.imgabreuw.usecases.keys.CommandHandler;
import io.github.imgabreuw.usecases.screen.RefreshScreen;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;

import static io.github.imgabreuw.shared.KeyCode.*;

@SpringBootApplication
@ImportRuntimeHints(GraalVMHints.class)
public class JaviApplication {

    public static void main(String[] args) {
        SpringApplication.run(JaviApplication.class, args);
    }

    @Bean
    public CommandLineRunner javiRunner() {
        return args -> {
            try {
                Terminal terminal = Terminal.getInstance();

                var initEditor = new InitEditor(terminal);
                var windowSize = initEditor.execute(new InitEditor.InputValues());

                var openFile = new OpenFile();
                var fileContent = openFile
                        .execute(new OpenFile.InputValues(args))
                        .content();

                var editor = new Editor(fileContent, windowSize.columns(), windowSize.rows());

                var refreshScreen = new RefreshScreen();

                var commandHandler = new CommandHandler();
                commandHandler.register(":q", new QuitCommand(terminal));
                commandHandler.register(MOVE_UP.getCode(), new MoveUpCommand(editor));
                commandHandler.register(MOVE_DOWN.getCode(), new MoveDownCommand(editor));
                commandHandler.register(MOVE_RIGHT.getCode(), new MoveRightCommand(editor));
                commandHandler.register(MOVE_LEFT.getCode(), new MoveLeftCommand(editor));
                commandHandler.register(MOVE_HOME.getCode(), new MoveToHomeCommand(editor));
                commandHandler.register(MOVE_END.getCode(), new MoveToEndCommand(editor));
                commandHandler.register(PAGE_UP.getCode(), new PageUpCommand(editor));
                commandHandler.register(PAGE_DOWN.getCode(), new PageDownCommand(editor));

                while (true) {
                    editor.scroll();
                    refreshScreen.execute(new RefreshScreen.InputValues(editor));
                    commandHandler.listen();
                }
            } finally {
                Screen.shutdown();
            }
        };
    }
}
