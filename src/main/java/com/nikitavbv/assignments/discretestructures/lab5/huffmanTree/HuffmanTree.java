package com.nikitavbv.assignments.discretestructures.lab5.huffmanTree;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HuffmanTree {

  private List<Node> frequencyTable;
  private Node top = null;

  HuffmanTree(List<Node> frequencyTable) {
    this.frequencyTable = frequencyTable;
  }

  public static HuffmanTree withFrequenciesFromText(String text) {
    List<Node> frequencyTable = new ArrayList<>();
    for (int i = 0; i < text.length(); i++) {
      char character = text.charAt(i);
      Node characterNode = null;

      for (Node node : frequencyTable) {
        if (node.isCharacter(character)) {
          characterNode = node;
          break;
        }
      }

      if (characterNode != null) {
        characterNode.increaseFrequency();
        continue;
      }

      frequencyTable.add(new Node(character, 1));
    }

    frequencyTable.sort(Node::compareTo);

    return new HuffmanTree(frequencyTable);
  }

  public void runHuffmanAlgorithm() {
    List<Node> table = new ArrayList<>(frequencyTable);

    while (table.size() > 1) {
      Node leastFreq1 = table.remove(table.size() - 1);
      Node leastFreq2 = table.remove(table.size() - 1);
      Node mergedNode = new Node(leastFreq1, leastFreq2);
      table.add(0, mergedNode);
      table.sort(Node::compareTo);
    }

    top = table.remove(0);
  }

  int getCharacterFrequency(char character) {
    for (Node node : frequencyTable) {
      if (node.isCharacter(character)) {
        return node.getFrequency();
      }
    }

    throw new NoSuchElementException();
  }

  public void printFrequenciesTableTo(OutputStream out) throws IOException  {
    out.write("character | frequency".getBytes());
    out.write(System.lineSeparator().getBytes());
    for (Node node : frequencyTable) {
      out.write(node.toCharacterTableString().getBytes());
      out.write(System.lineSeparator().getBytes());
    }
  }

  public void printInfix(OutputStream out) throws IOException {
    out.write('[');
    top.printInfix(out, this);
    out.write(']');
  }

  String encode(char c) {
    return top.encode(c);
  }

  public String encodeText(String text) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      result.append(encode(text.charAt(i)));
    }
    return result.toString();
  }

  char decode(String code) {
    Node cursor = top;
    for (int i = 0; i < code.length(); i++) {
      if (code.charAt(i) == '0') {
        cursor = cursor.getLeftNode();
      } else {
        cursor = cursor.getRightNode();
      }
    }

    return cursor.getCharacter();
  }

  public String decodeText(String code) {
    Node cursor = top;
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < code.length(); i++) {
      if (code.charAt(i) == '0') {
        cursor = cursor.getLeftNode();
      } else {
        cursor = cursor.getRightNode();
      }
      if (cursor.isCharacterNode()) {
        result.append(cursor.getCharacter());
        cursor = top;
      }
    }
    return result.toString();
  }

  int getCodeWeight() {
    int weight = 0;
    for (Node node : frequencyTable) {
      weight += node.getFrequency() * encode(node.getCharacter()).length();
    }
    return weight;
  }

  public int getTreeHeight() {
    int maxHeight = 0;
    for (Node node : frequencyTable) {
      maxHeight = Math.max(maxHeight, encode(node.getCharacter()).length());
    }
    return maxHeight;
  }

}
