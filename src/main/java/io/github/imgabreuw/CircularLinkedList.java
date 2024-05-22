package io.github.imgabreuw;

import java.io.*;
import java.util.ArrayList;

/*
 * INTEGRANTES:
 * - Enzo Ribeiro (10418262)
 * - Gabriel Ken Kazama Geronazzo (10418247)
 * - Lucas Zanini (10417361)
 *
 * REFERÊNCIA TÉCNICA:
 * - https://profkishimoto.github.io/edi03d-2024-1/
 */
public class CircularLinkedList {
    private Node head;
    private Node tail;
    private int size;

    private ArrayList<Node> clipboard;

    public CircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.clipboard = new ArrayList<>();
    }

    public void add(String data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.next = head;
            newNode.prev = tail;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
            tail = newNode;
        }

        size++;
    }

    public void insert(int index, String data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(data);

        if (index == 0) {
            if (head == null) {
                head = newNode;
                tail = newNode;
                newNode.next = head;
                newNode.prev = tail;
            } else {
                newNode.next = head;
                newNode.prev = tail;
                head.prev = newNode;
                tail.next = newNode;
                head = newNode;
            }
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            newNode.next = current.next;
            newNode.prev = current;
            current.next.prev = newNode;
            current.next = newNode;

            if (newNode.next == head) {
                tail = newNode;
            }
        }

        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = tail;
                tail.next = head;
            }
        } else {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
            if (current == tail) tail = current.prev;
        }

        size--;
    }

    public String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public void display() {
        if (size == 0) {
            System.out.println("Lista está vazia");
            return;
        }

        Node current = head;

        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ": " + current.data);
            current = current.next;
        }
    }

    public int size() {
        return size;
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Node current = head;

            for (int i = 0; i < size; i++) {
                writer.write(current.data);
                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    public boolean loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                add(line);
            }

            return true;
        } catch (FileNotFoundException e) {
            System.out.printf("Arquivo '%s' não existe!%n", filename);
        } catch (IOException e) {
            System.out.println("Erro ao carregar do arquivo: " + e.getMessage());
        }

        return false;
    }

    public ArrayList<String> search(String element) {
        ArrayList<String> results = new ArrayList<>();
        Node current = head;

        for (int i = 0; i < size; i++) {
            if (current.data.contains(element)) {
                results.add((i + 1) + ": " + current.data);
            }

            current = current.next;
        }

        return results;
    }

    public int replace(String oldElement, String newElement) {
        int counter = 0;
        Node current = head;

        for (int i = 0; i < size; i++) {
            if (current.data.contains(oldElement)) {
                current.data = current.data.replace(oldElement, newElement);
                ++counter;
            }

            current = current.next;
        }

        return counter;
    }

    public void replaceInLine(String oldElement, String newElement, int line) {
        if (line < 1 || line > size) {
            throw new IndexOutOfBoundsException();
        }

        Node current = head;

        for (int i = 1; i < line; i++) {
            current = current.next;
        }

        current.data = current.data.replace(oldElement, newElement);
    }

    public void mark(int start, int end) {
        if (start <= 0 || start > size || end <= 0 || end > size || start > end) {
            System.out.printf("Intervalo de linha %d - %d inválido!%n", start, end);
            return;
        }

        clipboard.clear();
        Node current = head;

        for (int i = 0; i < start - 1; i++) {
            current = current.next;
        }

        for (int i = start; i <= end; i++) {
            clipboard.add(current);
            current = current.next;
        }
    }

    public void copy() {
        ArrayList<Node> newClipboard = new ArrayList<>();

        for (Node node : clipboard) {
            newClipboard.add(new Node(node.data));
        }

        clipboard = newClipboard;
    }

    public void cut() {
        for (Node node : clipboard) {
            Node current = head;
            int index = 0;

            while (current != null) {
                if (current == node) {
                    remove(index);
                    break;
                }

                current = current.next;
                index++;
            }
        }
    }

    public void paste(int index) {
        for (Node node : clipboard) {
            insert(index, node.data);
            index++;
        }
    }
}

