package com.nikitavbv.assignments.discretestructures.lab6;

import com.nikitavbv.assignments.discretestructures.lab6.rbtree.RBTree;
import java.io.IOException;

public class Lab6 {

  public static void main(String[] args) throws IOException {
    RBTree<Integer> tree = new RBTree<>();
    tree.insert(5);
    tree.insert(7);
    tree.insert(9);
    tree.insert(10);
    tree.insert(12);
    tree.insert(14);
    tree.insert(15);
    tree.insert(17);

    System.out.println(tree.root.data);
    tree.printInfix(System.out);
    tree.remove(12);
    System.out.println(tree.root.data);
    tree.printInfix(System.out);
  }

}
