package com.nikitavbv.assignments.discretestructures.lab6.rbtree;

import java.io.IOException;
import java.io.OutputStream;

public class RBTree<T extends Comparable> {

  public class Node<E extends T> {
    private boolean red = true; // init as red
    public E data;
    Node[] links = new Node[2]; // 0 is left child, 1 is right child

    private Node(E data) {
      this.data = data;
    }
  }

  public Node<T> root = null;

  Node single(Node root, boolean turnRight) {
    Node save = root.links[turnRight ? 0 : 1];
    root.links[turnRight ? 0 : 1] = save.links[turnRight ? 1 : 0];
    save.links[turnRight ? 1 : 0] = root;
    root.red = true;
    save.red = false;
    return save;
  }

  Node doubleTurn(Node root, boolean turnRight) {
    root.links[turnRight ? 0 : 1] = single(root.links[turnRight ? 0 : 1], !turnRight);
    return single(root, turnRight);
  }

  public void insert(T data) {
    if (root == null) {
      // if tree is empty
      root = new Node<>(data);
    } else {
      // if tree already contains at least one element
      Node head = new Node(null); // temporary tree root
      Node g, t;
      Node p, q;
      boolean dir = false;
      boolean last = false;

      t = head;
      g = null;
      p = null;
      q = root;
      t.links[1] = root;

      // iterate over tree
      while (true) {
        if (q == null) {
          // insert node
          q = new Node(data);
          p.links[dir ? 1 : 0] = q;
        } else if (q.links[0] != null && q.links[0].red && q.links[1] != null && q.links[1].red) {
          // change color, first case.
          q.red = true;
          q.links[0].red = false;
          q.links[1].red = false;
        }

        if (q != null && q.red && p != null && p.red) {
          // two red colors
          boolean dir2 = t.links[1] == g;

          if (q == p.links[last ? 1 : 0]) {
            t.links[dir2 ? 1 : 0] = single(g, !last); // single turn, second case.
          } else {
            t.links[dir2 ? 1 : 0] = doubleTurn(g, !last); // double turn, third case.
          }
        }

        // if this node already exists - break.
        if (q.data.equals(data)) {
          break;
        }

        last = dir;
        dir = q.data.compareTo(data) < 0;

        if (g != null) {
          t = g;
        }
        g = p;
        p = q;
        q = q.links[dir ? 1 : 0];
      }

      // update root
      root = head.links[1];
    }

    // root is always black
    root.red = false;
  }

  public void remove(T data) {
    if (root != null) {
      Node head = new Node(null); // temporary pointer to tree root
      Node q, p, g; // helpers
      Node f = null; // node to be removed
      boolean dir = true;

      q = head;
      g = null;
      p = null;
      q.links[1] = root;

      while (q.links[dir ? 1 : 0] != null) {
        boolean last = dir;

        // save structure to helpers
        g = p;
        p = q;
        q = q.links[dir ? 1 : 0];
        dir = q.data.compareTo(data) < 0;

        if (q.data.equals(data)) {
          // save found node
          f = q;
        }

        if (!(q != null && q.red) && !(q.links[dir ? 1 : 0] != null && q.links[dir ? 1 : 0] != null)) {
          if (q.links[dir ? 0 : 1] != null && q.links[dir ? 0 : 1].red) {
            p.links[last ? 1 : 0] = single(q, dir); // single turn of first type
            p = p.links[last ? 1 : 0];
          } else if (!(q.links[dir ? 0 : 1] != null && q.links[dir ? 0 : 1].red)) {
            Node s = p.links[last ? 0 : 1];
            if (s != null) {
              if (!(s.links[last ? 0 : 1] != null && s.links[last ? 0 : 1].red) && !(s.links[last ? 1 : 0] != null && s.links[last ? 1 : 0].red)) {
                // first case - change colors
                p.red = false;
                s.red = true;
                q.red = true;
              } else {
                boolean dir2 = (g.links[1] == p);

                if (s.links[last ? 1 : 0]  != null && s.links[last ? 1 : 0].red) {
                  g.links[dir2 ? 1 : 0] = doubleTurn(p, last); // third case - double turn
                } else if (s.links[last ? 0 : 1] != null && s.links[last ? 0 : 1].red) {
                  g.links[dir2 ? 1 : 0] = single(p, last); // fourth case - single turn of second type
                }

                q.red = true;
                g.links[dir2 ? 1 : 0].red = true;
                g.links[dir2 ? 1 : 0].links[0].red = false;
                g.links[dir2 ? 1 : 0].links[1].red = false;
              }
            }
          }
        }
      }

      // remove node
      if (f != null) {
        f.data = q.data;
        p.links[p.links[1] == q ? 1 : 0] = q.links[q.links[0] == null ? 1 : 0];
      }

      // tree root is always black
      root = head.links[1];
      if (root != null) {
        root.red = false;
      }
    }
  }

  public void printInfix(OutputStream out) throws IOException {
    printInfix(out, root);
  }

  public void printInfix(OutputStream out, Node<T> top) throws IOException {
    if (top == null) {
      return;
    }
    printInfix(out, top.links[0]);
    out.write(top.data.toString().getBytes());
    out.write(' ');
    printInfix(out, top.links[1]);
  }

}
