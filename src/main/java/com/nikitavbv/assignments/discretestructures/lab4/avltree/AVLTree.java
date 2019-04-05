package com.nikitavbv.assignments.discretestructures.lab4.avltree;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AVLTree<T extends Comparable<T>> {

  public class AVLTreeNode<E extends T> {
    private E data;
    private int height = 0;

    private AVLTreeNode<T> left = null;
    private AVLTreeNode<T> right = null;

    private AVLTreeNode(E data) {
      this.data = data;
    }

    /** Calculates subtree height. */
    void updateSubtreeHeight() {
      int leftHeight = getHeight(left);
      int rightHeight = getHeight(right);
      this.height = (leftHeight > rightHeight ? leftHeight : rightHeight) + 1;
    }

    /** Calculates balance factor. */
    int getBalanceFactor() {
      return getHeight(right) - getHeight(left);
    }

    public E getData() {
      return data;
    }

    int getHeight() {
      return height;
    }

    /** Returns subtree height. */
    int getHeight(AVLTreeNode<T> node) {
      return node != null ? node.getHeight() : 0;
    }

    public AVLTreeNode<T> getLeft() {
      return left;
    }

    public AVLTreeNode<T> getRight() {
      return right;
    }

    void printStructured(OutputStream out) throws IOException {
      printStructured(out, "", true, true);
    }

    void printStructured(OutputStream out, String prefix, boolean isEnd, boolean isRoot) throws IOException {
      String extSpace = "    ";
      String extLine = "|   ";
      if (isRoot) {
        out.write((prefix + data.toString() + "\n").getBytes());
        extSpace = "";
        extLine = "";
      } else {
        out.write((prefix + (isEnd ? "'- " : "|- ") + data.toString() + "\n").getBytes());
      }
      if (left != null) left.printStructured(out, prefix + (isEnd ? extSpace : extLine), right == null, false);
      if (right != null) right.printStructured(out, prefix + (isEnd ? extSpace : extLine), true, false);
    }
  }

  private AVLTreeNode<T> top = null;

  private AVLTreeNode<T> leftRotate(AVLTreeNode<T> node) {
    AVLTreeNode<T> right = node.right;
    node.right = right.left;
    right.left = node;

    node.updateSubtreeHeight();
    right.updateSubtreeHeight();

    return right;
  }

  private AVLTreeNode<T> rightRotate(AVLTreeNode<T> node) {
    AVLTreeNode<T> left = node.left;
    node.left = left.right;
    left.right = node;

    left.updateSubtreeHeight();
    node.updateSubtreeHeight();

    return left;
  }

  private AVLTreeNode<T> doBalancing(AVLTreeNode<T> node) {
    node.updateSubtreeHeight();
    if (node.getBalanceFactor() == 2) {
      if (node.right.getBalanceFactor() < 0) {
        node.right = rightRotate(node.right);
      }
      node = leftRotate(node);
      return node;
    } else if (node.getBalanceFactor() == -2) {
      if (node.left.getBalanceFactor() > 0) {
        node.left = leftRotate(node.left);
      }
      node = rightRotate(node);
      return node;
    }
    return node;
  }

  private AVLTreeNode<T> insertNode(AVLTreeNode<T> node, T data) {
    if (node == null) {
      return new AVLTreeNode<>(data);
    }

    if (data.compareTo(node.data) < 0) {
      node.left = insertNode(node.left, data);
    } else {
      node.right = insertNode(node.right, data);
    }

    return doBalancing(node);
  }

  AVLTreeNode<T> findLeftNode(AVLTreeNode<T> node) {
    return node.left != null ? findLeftNode(node.left) : node;
  }

  AVLTreeNode<T> removeSmallestNode(AVLTreeNode<T> node) {
    if (node.left == null) {
      return node.right;
    }
    node.left = removeSmallestNode(node.left);
    return doBalancing(node);
  }

  private AVLTreeNode<T> removeNode(AVLTreeNode<T> node, T data) {
    if (node == null) {
      return null;
    }
    if (data.compareTo(node.data) < 0) {
      node.left = removeNode(node.left, data);
    } else if (data.compareTo(node.data) > 0) {
      node.right = removeNode(node.right, data);
    } else {
      if (node.right == null) {
        return node.left;
      }

      AVLTreeNode<T> min = findLeftNode(node.right);
      min.right = removeSmallestNode(node.right);
      min.left = node.left;
      return doBalancing(min);
    }

    return doBalancing(node);
  }

  public void add(T data) {
    top = insertNode(top, data);
  }

  public void remove(T data) {
    top = removeNode(top, data);
  }

  public boolean containsElement(T data) {
    AVLTreeNode<T> cursor = top;
    while (cursor != null) {
      if (data.compareTo(cursor.data) < 0) {
        cursor = cursor.left;
      } else if (data.compareTo(cursor.data) > 0) {
        cursor = cursor.right;
      } else {
        return true;
      }
    }

    return false;
  }

  public AVLTreeNode<T> getTopNode() {
    return top;
  }

  public void printStructured(OutputStream out) throws IOException {
    this.getTopNode().printStructured(out);
  }

  public List<T> toInfixForm() {
    return toInfixForm(top);
  }

  private List<T> toInfixForm(AVLTreeNode<T> startNode) {
    List<T> elements = new ArrayList<>();
    if (startNode.left != null) {
      elements.addAll(toInfixForm(startNode.left));
    }
    elements.add(startNode.data);
    if (startNode.right != null) {
      elements.addAll(toInfixForm(startNode.right));
    }
    return elements;
  }

  public List<T> toPrefixForm() {
    return toPrefixForm(top);
  }

  private List<T> toPrefixForm(AVLTreeNode<T> startNode) {
    List<T> elements = new ArrayList<>();
    elements.add(startNode.data);
    if (startNode.left != null) {
      elements.addAll(toInfixForm(startNode.left));
    }
    if (startNode.right != null) {
      elements.addAll(toInfixForm(startNode.right));
    }
    return elements;
  }

  public List<T> toPostfixForm() {
    return toPostfixForm(top);
  }

  private List<T> toPostfixForm(AVLTreeNode<T> startNode) {
    List<T> elements = new ArrayList<>();
    if (startNode.left != null) {
      elements.addAll(toInfixForm(startNode.left));
    }
    if (startNode.right != null) {
      elements.addAll(toInfixForm(startNode.right));
    }
    elements.add(startNode.data);
    return elements;
  }

}
