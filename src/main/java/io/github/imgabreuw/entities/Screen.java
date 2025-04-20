package io.github.imgabreuw.entities;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class Screen {

    private final StringBuilder screen = new StringBuilder();
    private final Editor editor;

    // Controle de FPS para debounce de renderização
    private static final int DEFAULT_FPS = 60;
    private static final long FRAME_TIME_MS = 1000 / DEFAULT_FPS;
    private static final ScheduledExecutorService renderScheduler = Executors.newSingleThreadScheduledExecutor();
    private static final AtomicBoolean renderPending = new AtomicBoolean(false);
    private static long lastRenderTime = 0;

    public static Screen builder(Editor editor) {
        return new Screen(editor);
    }

    public Screen setCursorToTopLeft() {
        screen.append("\033[H");
        return this;
    }

    public Screen drawCursor() {
        screen.append("\033[%d;%dH".formatted(
                editor.getCursorY() - editor.getOffsetY() + 1,
                editor.getCursorX() - editor.getOffsetX() + 1)
        );
        return this;
    }

    public Screen drawStatusBar() {
        String statusMessage = "X:" + editor.getCursorX() + " Y: " + editor.getCursorY();
        screen.append("\033[7m")
                .append(statusMessage)
                .append(" ".repeat(Math.max(0, editor.getColumns() - statusMessage.length())))
                .append("\033[0m");
        return this;
    }

    public Screen drawContent() {
        for (int i = 0; i < editor.getRows(); i++) {
            int lineIndex = editor.getOffsetY() + i;

            if (lineIndex >= editor.getContent().size()) {
                screen.append("~");
            } else {
                String line = editor.getContent().get(lineIndex);
                int lengthToDraw = line.length() - editor.getOffsetX();

                if (lengthToDraw < 0) {
                    lengthToDraw = 0;
                }
                if (lengthToDraw > editor.getColumns()) {
                    lengthToDraw = editor.getColumns();
                }

                if (lengthToDraw > 0) {
                    screen.append(line, editor.getOffsetX(), editor.getOffsetX() + lengthToDraw);
                }
            }
            screen.append("\033[K\r\n");
        }

        return this;
    }

    public Screen clearScreen() {
        screen.append("\033[2J"); // Limpa toda a tela
        return this;
    }

    public Screen hideCursor() {
        screen.append("\033[?25l"); // Oculta o cursor durante renderização
        return this;
    }

    public Screen showCursor() {
        screen.append("\033[?25h"); // Mostra o cursor após posicionar
        return this;
    }

    public String build() {
        return screen.toString();
    }

    public void render() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastRender = currentTime - lastRenderTime;
        if (timeSinceLastRender >= FRAME_TIME_MS && renderPending.compareAndSet(false, true)) {
            System.out.print(screen);
            System.out.flush();
            lastRenderTime = System.currentTimeMillis();
            renderPending.set(false);
        } else {
            if(renderPending.compareAndSet(false, true)) {
                long delayTime = Math.max(0, FRAME_TIME_MS - timeSinceLastRender);
                renderScheduler.schedule(() -> {
                    System.out.print(screen);
                    System.out.flush();
                    lastRenderTime = System.currentTimeMillis();
                    renderPending.set(false);
                }, delayTime, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static void shutdown() {
        renderScheduler.shutdownNow();
    }
}
