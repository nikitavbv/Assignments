package com.nikitavbv.assignments.algorithms.lab3;

import com.nikitavbv.assignments.algorithms.lab3.btree.BTree;
import java.io.File;

public class Lab3 {

  public static void main(String[] args) {
    BTree bTree = new BTree(3, 0, new File("data/btree/first"));
    // System.out.println(new BTreeNode(bTree, 1).findInsertPosition(15));
    bTree.addKeys(8, 12, 5, 0, 15, 7, 23, 48, 16, 51);
    //bTree.addKeys(8, 12, 5, 1);
    bTree.removeKey(0);
  }

}
