package io.github.imgabreuw;

/*
 * INTEGRANTES:
 * - Enzo Ribeiro (10418262)
 * - Gabriel Ken Kazama Geronazzo (10418247)
 * - Lucas Zanini (10417361)
 *
 * REFERÊNCIA TÉCNICA:
 * - https://profkishimoto.github.io/edi03d-2024-1/
 */
public class Node {
    String data;
    Node next;
    Node prev;

    public Node(String data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

