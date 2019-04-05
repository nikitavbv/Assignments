package com.nikitavbv.assignments.discretestructures.lab5.huffmanTree;

import java.io.IOException;
import java.io.OutputStream;

class Node implements Comparable<Node> {
  private char character;
  private int frequency = 0;

  private Node left = null;
  private Node right = null;

  Node(Node left, Node right) {
    this.left = left;
    this.right = right;
    if (left != null) {
      this.frequency += left.getFrequency();
    }
    if (right != null) {
      this.frequency += right.getFrequency();
    }
  }

  Node(char character, int frequency) {
    this.character = character;
    this.frequency = frequency;
  }

  boolean isCharacter(char character) {
    return this.character == character;
  }

  void increaseFrequency() {
    this.frequency += 1;
  }

  int getFrequency() {
    return frequency;
  }

  String encode(char c) {
    if (isCharacterNode()) {
      return isCharacter(c) ? "" : null;
    }

    String leftResult = null;
    String rightResult = null;

    if (left != null) {
      leftResult = left.encode(c);
    }
    if (right != null) {
      rightResult = right.encode(c);
    }

    if (leftResult == null && rightResult == null) {
      return null;
    }
    return leftResult != null ? "0" + leftResult : "1" + rightResult;
  }

  @Override
  public int compareTo(Node other) {
    if (this.frequency == other.frequency) {
      return 0;
    } else {
      return other.frequency - this.frequency < 0 ? -1 : 1;
    }
  }

  @Override
  public String toString() {
    if (character == '\n') {
      return "[newline]";
    } else if (character == ' ') {
      return "[space]";
    }
    return Character.toString(character);
  }

  String toCharacterTableString() {
    return String.format("%9s | %d", toString(), frequency);
  }

  void printInfix(OutputStream out, HuffmanTree tree) throws IOException {
    if (left != null) {
      left.printInfix(out, tree);
    }
    if (isCharacterNode()) {
      out.write(toString().getBytes());
      out.write('-');
      out.write(tree.encode(character).getBytes());
      out.write(' ');
    }
    if (right != null) {
      right.printInfix(out, tree);
    }
  }

  Node getLeftNode() {
    return left;
  }

  Node getRightNode() {
    return right;
  }

  char getCharacter() {
    return character;
  }

  boolean isCharacterNode() {
    return left == null && right == null;
  }
}