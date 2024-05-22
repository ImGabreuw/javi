package io.github.imgabreuw;

import java.util.Scanner;

/*
 * INTEGRANTES:
 * - Enzo Ribeiro (10418262)
 * - Gabriel Ken Kazama Geronazzo (10418247)
 * - Lucas Zanini (10417361)
 *
 * REFERÊNCIA TÉCNICA:
 * - https://profkishimoto.github.io/edi03d-2024-1/
 */
public class SimpleEditor {
    public static void main(String[] args) {
        CircularLinkedList list = new CircularLinkedList();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();
            String[] parts = command.split(" ");

            switch (parts[0]) {
                case ":e":
                    if (parts.length != 2) {
                        System.out.println("Uso: :e nomeDoArquivo");
                        break;
                    }

                    var filename = parts[1];
                    var isLoaded = list.loadFromFile(filename);

                    if (isLoaded) {
                        System.out.printf("Arquivo '%s' lido com sucesso!%n", filename);
                        list.display();
                    }

                    break;
                case ":w":
                    switch (parts.length) {
                        case 1 -> list.saveToFile("temp.txt");
                        case 2 -> list.saveToFile(parts[1]);
                        default -> System.out.println("Uso: :w [nomeDoArquivo]");
                    }
                    break;
                case ":q!":
                    System.exit(0);
                    break;
                case ":v":
                    if (parts.length != 3) {
                        System.out.println("Uso: :v linhaInicial linhaFinal");
                        break;
                    }

                    try {
                        int start = Integer.parseInt(parts[1]);
                        int end = Integer.parseInt(parts[2]);

                        list.mark(start, end);
                    } catch (NumberFormatException e) {
                        System.out.println("Números de linha inválidos.");
                    }
                    break;
                case ":y":
                    list.copy();
                    break;
                case ":c":
                    list.cut();
                    break;
                case ":p":
                    if (parts.length != 2) {
                        System.out.println("Uso: :p númeroLinha");
                        break;
                    }

                    try {
                        int line = Integer.parseInt(parts[1]);

                        list.paste(line);
                        list.display();
                    } catch (NumberFormatException e) {
                        System.out.println("Número de linha inválido.");
                    }
                    break;
                case ":s":
                    if (parts.length == 1) {
                        list.display();
                        break;
                    }

                    if (parts.length != 3) {
                        System.out.println("Uso: :s [linhaInicial linhaFinal]");
                        break;
                    }

                    try {
                        int start = Integer.parseInt(parts[1]);
                        int end = Integer.parseInt(parts[2]);

                        for (int i = start - 1; i < end; i++) {
                            System.out.println((i + 1) + ": " + list.get(i));
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println("Números de linha inválidos.");
                    }
                    break;
                case ":x":
                    if (parts.length != 2) {
                        System.out.println("Uso: :x númeroLinha");
                        break;
                    }

                    try {
                        int line = Integer.parseInt(parts[1]);

                        list.remove(line - 1);
                        list.display();
                    } catch (NumberFormatException e) {
                        System.out.println("Número de linha inválido.");
                    }
                    break;
                case ":xG":
                    if (parts.length != 2) {
                        System.out.println("Uso: :xG númeroLinha");
                        break;
                    }

                    try {
                        int line = Integer.parseInt(parts[1]);

                        while (list.size() >= line) {
                            list.remove(list.size() - 1);
                        }

                        list.display();
                    } catch (NumberFormatException e) {
                        System.out.println("Número de linha inválido.");
                    }
                    break;
                case ":XG":
                    if (parts.length != 2) {
                        System.out.println("Uso: :XG númeroLinha");
                        break;
                    }

                    try {
                        int line = Integer.parseInt(parts[1]);

                        for (int i = 1; i < line; i++) {
                            list.remove(0);
                        }

                        list.display();
                    } catch (NumberFormatException e) {
                        System.out.println("Número de linha inválido.");
                    }
                    break;
                case ":/":
                    if (parts.length < 2 || parts.length > 4) {
                        System.out.println("Uso: :/ termoBusca [termoSubstituto] [linha]");
                        break;
                    }

                    String searchTerm = parts[1];

                    if (parts.length == 2) {
                        list.search(searchTerm).forEach(System.out::println);
                        break;
                    }

                    if (parts.length == 3) {
                        String replaceTerm = parts[2];
                        list.replace(searchTerm, replaceTerm);
                        list.display();
                        break;
                    }

                    String replaceTerm = parts[2];

                    try {
                        int line = Integer.parseInt(parts[3]);

                        list.replaceInLine(searchTerm, replaceTerm, line);
                        list.display();
                    } catch (NumberFormatException e) {
                        System.out.println("Número de linha inválido.");
                    }
                    break;
                case ":a":
                case ":i":
                    if (parts.length < 2) {
                        System.out.println("Uso: :" + parts[0] + " linha [dados]");
                        break;
                    }

                    try {
                        int line = Integer.parseInt(parts[1]);
                        StringBuilder data = new StringBuilder();

                        if (parts.length > 2) {
                            for (int i = 2; i < parts.length; i++) {
                                data.append(parts[i]).append(" ");
                            }
                        }

                        if (parts[0].equals(":i")) {
                            --line;
                        }

                        list.insert(line, data.toString().trim());
                        list.display();
                    } catch (NumberFormatException e) {
                        System.out.println("Número de linha inválido.");
                    }
                    break;
                case ":help":
                    printHelp();
                    break;
                default:
                    System.out.println("Comando desconhecido. Digite :help para ver a lista de comandos.");
                    break;
            }
        }
    }

    private static void printHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println(":e nomeDoArquivo - Abrir um arquivo");
        System.out.println(":w [nomeDoArquivo] - Salvar em um arquivo");
        System.out.println(":q! - Sair sem salvar");
        System.out.println(":v inicio fim - Marcar linhas para copiar/recortar");
        System.out.println(":y - Copiar linhas marcadas");
        System.out.println(":c - Recortar linhas marcadas");
        System.out.println(":p linha - Colar após a linha especificada");
        System.out.println(":s [inicio fim] - Mostrar linhas");
        System.out.println(":x linha - Excluir uma linha");
        System.out.println(":xG linha - Excluir da linha até o fim");
        System.out.println(":XG linha - Excluir da linha até o início");
        System.out.println(":/ termoBusca [termoSubstituto] [linha] - Buscar/Substituir");
        System.out.println(":a linha - Anexar após uma linha");
        System.out.println(":i linha - Inserir antes de uma linha");
        System.out.println(":help - Mostrar esta ajuda");
    }
}
