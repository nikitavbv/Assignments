package com.nikitavbv.assignments.discretestructures.lab4;

import com.nikitavbv.assignments.discretestructures.lab4.avltree.AVLTree;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lab4 {
  
  public static void main(String[] args) throws IOException {
      int treeSize = requestTreeSize();

      AVLTree<Integer> tree = new AVLTree<>();
      fillTreeWithRandomElements(tree, treeSize);
      tree.printStructured(System.out);

      int maxElement = getTreeMaxElement(tree);
      System.out.println("Max element: " + maxElement);
  }

  private static int getTreeMaxElement(AVLTree<Integer> tree) {
    AVLTree<Integer>.AVLTreeNode<Integer> cursor = tree.getTopNode();
    while (cursor != null) {
      if (cursor.getRight() != null && cursor.getRight().getData() > cursor.getData()) {
        cursor = cursor.getRight();
      } else {
        return cursor.getData();
      }
    }
    return 0;
  }

  private static void fillTreeWithRandomElements(AVLTree<Integer> tree, int totalElements) {
    List<Integer> numbers = IntStream.range(0, totalElements * 10).boxed().collect(Collectors.toList());
    Collections.shuffle(numbers);

    for (int i = 0; i < totalElements; i++) {
      tree.add(numbers.get(i));
    }
  }

  private static int requestTreeSize() {
    Scanner in = new Scanner(System.in);
    System.out.println("Number of elements to add to tree: ");
    int queueSize = in.nextInt();
    in.close();
    return queueSize;
  }

}
