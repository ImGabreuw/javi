package io.github.imgabreuw;

import java.util.Scanner;

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
                    if (parts.length == 2) {
                        list.loadFromFile(parts[1]);
                        list.display();
                    } else {
                        System.out.println("Uso: :e nomeDoArquivo");
                    }
                    break;
                case ":w":
                    if (parts.length == 1) {
                        list.saveToFile("saida.txt");
                    } else if (parts.length == 2) {
                        list.saveToFile(parts[1]);
                    } else {
                        System.out.println("Uso: :w [nomeDoArquivo]");
                    }
                    break;
                case ":q!":
                    System.exit(0);
                    break;
                case ":v":
                    if (parts.length == 3) {
                        try {
                            int inicio = Integer.parseInt(parts[1]);
                            int fim = Integer.parseInt(parts[2]);
                            list.mark(inicio, fim);
                        } catch (NumberFormatException e) {
                            System.out.println("Números de linha inválidos.");
                        }
                    } else {
                        System.out.println("Uso: :v linhaInicial linhaFinal");
                    }
                    break;
                case ":y":
                    list.copy();
                    break;
                case ":c":
                    list.cut();
                    break;
                case ":p":
                    if (parts.length == 2) {
                        try {
                            int linha = Integer.parseInt(parts[1]);
                            list.paste(linha);
                            list.display();  
                        } catch (NumberFormatException e) {
                            System.out.println("Número de linha inválido.");
                        }
                    } else {
                        System.out.println("Uso: :p númeroLinha");
                    }
                    break;
                case ":s":
                    if (parts.length == 1) {
                        list.display();  
                    } else if (parts.length == 3) {
                        try {
                            int inicio = Integer.parseInt(parts[1]);
                            int fim = Integer.parseInt(parts[2]);
                            for (int i = inicio - 1; i < fim; i++) {
                                System.out.println((i + 1) + ": " + list.get(i));
                            }
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            System.out.println("Números de linha inválidos.");
                        }
                    } else {
                        System.out.println("Uso: :s [linhaInicial linhaFinal]");
                    }
                    break;
                case ":x":
                    if (parts.length == 2) {
                        try {
                            int linha = Integer.parseInt(parts[1]);
                            list.remove(linha - 1);
                            list.display();
                        } catch (NumberFormatException e) {
                            System.out.println("Número de linha inválido.");
                        }
                    } else {
                        System.out.println("Uso: :x númeroLinha");
                    }
                    break;
                case ":xG":
                    if (parts.length == 2) {
                        try {
                            int linha = Integer.parseInt(parts[1]);
                            while (list.size() >= linha) {
                                list.remove(list.size() - 1);
                            }
                            list.display();
                        } catch (NumberFormatException e) {
                            System.out.println("Número de linha inválido.");
                        }
                    } else {
                        System.out.println("Uso: :xG númeroLinha");
                    }
                    break;
                case ":XG":
                    if (parts.length == 2) {
                        try {
                            int linha = Integer.parseInt(parts[1]);
                            for (int i = 1; i < linha; i++) {
                                list.remove(0);
                            }
                            list.display();
                        } catch (NumberFormatException e) {
                            System.out.println("Número de linha inválido.");
                        }
                    } else {
                        System.out.println("Uso: :XG númeroLinha");
                    }
                    break;
                case ":/":
                    if (parts.length == 2 || parts.length == 3 || parts.length == 4) {
                        String termoBusca = parts[1];
                        if (parts.length == 2) {
                            list.search(termoBusca).forEach(System.out::println);
                        } else if (parts.length == 3) {
                            String termoSubstituto = parts[2];
                            list.replace(termoBusca, termoSubstituto);
                            list.display();
                        } else {
                            String termoSubstituto = parts[2];
                            try {
                                int linha = Integer.parseInt(parts[3]);
                                list.replaceInLine(termoBusca, termoSubstituto, linha);
                                list.display();
                            } catch (NumberFormatException e) {
                                System.out.println("Número de linha inválido.");
                            }
                        }
                    } else {
                        System.out.println("Uso: :/ termoBusca [termoSubstituto] [linha]");
                    }
                    break;
                case ":a":
                    if (parts.length >= 3) {
                        try {
                            int linha = Integer.parseInt(parts[1]);
                            StringBuilder dados = new StringBuilder();
                            for (int i = 2; i < parts.length; i++) {
                                dados.append(parts[i]).append(" ");
                            }
                            list.insert(linha, dados.toString().trim());
                            list.display();
                        } catch (NumberFormatException e) {
                            System.out.println("Número de linha inválido.");
                        }
                    } else {
                        System.out.println("Uso: :a linha dados");
                    }
                    break;
                case ":i":
                    if (parts.length >= 3) {
                        try {
                            int linha = Integer.parseInt(parts[1]);
                            StringBuilder dados = new StringBuilder();
                            for (int i = 2; i < parts.length; i++) {
                                dados.append(parts[i]).append(" ");
                            }
                            list.insert(linha - 1, dados.toString().trim());
                            list.display();
                        } catch (NumberFormatException e) {
                            System.out.println("Número de linha inválido.");
                        }
                    } else {
                        System.out.println("Uso: :i linha dados");
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
