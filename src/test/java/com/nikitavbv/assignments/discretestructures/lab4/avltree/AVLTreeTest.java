package com.nikitavbv.assignments.discretestructures.lab4.avltree;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import org.junit.Test;

public class AVLTreeTest {

  @Test
  public void testTree() throws IOException {
    AVLTree<Integer> tree = new AVLTree<>();
    tree.add(10);
    tree.add(9);
    tree.add(8);
    tree.add(7);
    tree.add(6);
    tree.add(5);
    tree.add(4);
    tree.add(3);
    tree.add(2);
    tree.add(1);

    assertTrue(tree.containsElement(5));
    assertEquals(5, tree.getTopNode().getData().intValue());

    tree.remove(5);
    assertEquals(6, tree.getTopNode().getData().intValue());
  }

  @Test
  public void testToInfixForm() {
    AVLTree<Integer> tree = new AVLTree<>();
    IntStream.range(1, 10).boxed().forEach(tree::add);
    assertTrue(Arrays.equals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, tree.toInfixForm().toArray()));
  }

}
