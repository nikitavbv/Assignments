package com.nikitavbv.assignments.algorithms.lab3.btree;

import org.junit.Test;

public class BTreeTests {

  @Test
  public void testInsertion() {
    BTree<Integer> tree = new BTree<>(3);
    tree.insert(8, 12, 5, 0, 15, 7, 23, 48, 16, 51);
  }

}
