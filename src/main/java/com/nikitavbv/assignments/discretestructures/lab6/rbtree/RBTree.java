package com.nikitavbv.assignments.discretestructures.lab6.rbtree;

import java.io.IOException;
import java.io.OutputStream;

public class RBTree<T extends Comparable> {

  public class Node<E extends T> {
    private boolean red = true; // init as red
    E data;
    Node<E>[] links; // 0 is left child, 1 is right child

    private Node(E data) {
      this.data = data;
      //noinspection unchecked
      links = new Node[2];
    }
  }

  private Node<T> root = null;
  private int totalSingleTurns = 0;
  private int totalDoubleTurns = 0;

  private Node<T> single(Node<T> root, boolean turnRight) {
    Node<T> save = root.links[turnRight ? 0 : 1];
    root.links[turnRight ? 0 : 1] = save.links[turnRight ? 1 : 0];
    save.links[turnRight ? 1 : 0] = root;
    root.red = true;
    save.red = false;

    totalSingleTurns++;

    return save;
  }

  private Node<T> doubleTurn(Node<T> root, boolean turnRight) {
    root.links[turnRight ? 0 : 1] = single(root.links[turnRight ? 0 : 1], !turnRight);
    totalDoubleTurns++;
    return single(root, turnRight);
  }

  public void insert(T data) {
    if (root == null) {
      // if tree is empty
      root = new Node<>(data);
    } else {
      // if tree already contains at least one element
      Node<T> head = new Node<>(null); // temporary tree root
      Node<T> grandParent, parentTemp;
      Node<T> parent, cursor;
      boolean turnDirection = false;
      boolean lastTurnDirection = false;

      parentTemp = head;
      grandParent = null;
      parent = null;
      cursor = root;
      parentTemp.links[1] = root;

      // iterate over tree
      while (true) {
        if (cursor == null) {
          // insert node
          cursor = new Node<>(data);
          parent.links[turnDirection ? 1 : 0] = cursor;
        } else if (cursor.links[0] != null && cursor.links[0].red && cursor.links[1] != null && cursor.links[1].red) {
          // change color, first case.
          cursor.red = true;
          cursor.links[0].red = false;
          cursor.links[1].red = false;
        }

        if (cursor.red && parent != null && parent.red) {
          // two red colors
          boolean dir2 = parentTemp.links[1] == grandParent;

          if (cursor == parent.links[lastTurnDirection ? 1 : 0]) {
            //noinspection ConstantConditions
            parentTemp.links[dir2 ? 1 : 0] = single(grandParent, !lastTurnDirection); // single turn, second case.
          } else {
            //noinspection ConstantConditions
            parentTemp.links[dir2 ? 1 : 0] = doubleTurn(grandParent, !lastTurnDirection); // double turn, third case.
          }
        }

        // if this node already exists - break.
        if (cursor.data.equals(data)) {
          break;
        }

        lastTurnDirection = turnDirection;
        //noinspection unchecked
        turnDirection = cursor.data.compareTo(data) < 0;

        if (grandParent != null) {
          parentTemp = grandParent;
        }
        grandParent = parent;
        parent = cursor;
        cursor = cursor.links[turnDirection ? 1 : 0];
      }

      // update root
      root = head.links[1];
    }

    // root is always black
    root.red = false;
  }

  @SuppressWarnings("unused")
  public void remove(T data) {
    if (root != null) {
      Node<T> head = new Node<>(null); // temporary pointer to tree root
      Node<T> cursor, parent, grandParent; // helpers
      Node<T> nodeToRemove = null;
      boolean turnDirection = true;

      cursor = head;
      parent = null;
      cursor.links[1] = root;

      while (cursor.links[turnDirection ? 1 : 0] != null) {
        boolean last = turnDirection;

        // save structure to helpers
        grandParent = parent;
        parent = cursor;
        cursor = cursor.links[turnDirection ? 1 : 0];
        //noinspection unchecked
        turnDirection = cursor.data.compareTo(data) < 0;

        if (cursor.data.equals(data)) {
          // save found node
          nodeToRemove = cursor;
        }

        if (!cursor.red && cursor.links[turnDirection ? 1 : 0] == null) {
          if (cursor.links[turnDirection ? 0 : 1] != null && cursor.links[turnDirection ? 0 : 1].red) {
            parent.links[last ? 1 : 0] = single(cursor, turnDirection); // single turn of first type
            parent = parent.links[last ? 1 : 0];
          } else if (!(cursor.links[turnDirection ? 0 : 1] != null && cursor.links[turnDirection ? 0 : 1].red)) {
            Node<T> s = parent.links[last ? 0 : 1];
            if (s != null) {
              if (!(s.links[last ? 0 : 1] != null && s.links[last ? 0 : 1].red) && !(s.links[last ? 1 : 0] != null
                      && s.links[last ? 1 : 0].red)) {
                // first case - change colors
                parent.red = false;
                s.red = true;
                cursor.red = true;
              } else {
                assert grandParent != null;
                boolean dir2 = (grandParent.links[1] == parent);

                if (s.links[last ? 1 : 0]  != null && s.links[last ? 1 : 0].red) {
                  grandParent.links[dir2 ? 1 : 0] = doubleTurn(parent, last); // third case - double turn
                } else if (s.links[last ? 0 : 1] != null && s.links[last ? 0 : 1].red) {
                  grandParent.links[dir2 ? 1 : 0] = single(parent, last); // fourth case - single turn of second type
                }

                cursor.red = true;
                grandParent.links[dir2 ? 1 : 0].red = true;
                grandParent.links[dir2 ? 1 : 0].links[0].red = false;
                grandParent.links[dir2 ? 1 : 0].links[1].red = false;
              }
            }
          }
        }
      }

      // remove node
      if (nodeToRemove != null) {
        nodeToRemove.data = cursor.data;
        parent.links[parent.links[1] == cursor ? 1 : 0] = cursor.links[cursor.links[0] == null ? 1 : 0];
      }

      // tree root is always black
      root = head.links[1];
      if (root != null) {
        root.red = false;
      }
    }
  }

  @SuppressWarnings("unused")
  public void printInfix(OutputStream out) throws IOException {
    printInfix(out, root);
  }

  @SuppressWarnings("WeakerAccess")
  public void printInfix(OutputStream out, Node<T> top) throws IOException {
    if (top == null) {
      return;
    }
    printInfix(out, top.links[0]);
    out.write(top.data.toString().getBytes());
    out.write(' ');
    printInfix(out, top.links[1]);
  }

  public int getTotalSingleTurns() {
    return totalSingleTurns;
  }

  public int getTotalDoubleTurns() {
    return totalDoubleTurns;
  }
}
