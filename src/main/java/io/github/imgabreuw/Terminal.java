package io.github.imgabreuw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {

    private static final Object lock = new Object();
    private static volatile StringBuilder buffer = new StringBuilder();
    private static volatile boolean running = true;
    private static volatile boolean inputAvailable = false;

    public static void main(String[] args) {
        // Thread para lidar com a entrada do usuário
        Thread inputThread = new Thread(Terminal::handleInput);
        inputThread.start();

        try {
            while (running) {
                synchronized (lock) {
                    // Aguarda até que haja entrada disponível
                    while (!inputAvailable) {
                        lock.wait();
                    }
                    // Exibe o conteúdo do buffer
                    System.out.print(buffer.toString());
                    inputAvailable = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void handleInput() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while (running && (line = reader.readLine()) != null) {
                synchronized (lock) {
                    if (line.equals(":q")) {
                        running = false;
                        break;
                    }
                    buffer.append(line).append("\n");
                    inputAvailable = true;
                    lock.notify(); // Notifica a thread principal para exibir o conteúdo
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
