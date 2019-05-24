package com.nikitavbv.assignments.algorithms.lab3;

import com.nikitavbv.assignments.algorithms.lab3.btree.BTree;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class BTreeBenchmark {

  public static void main(String[] args) {
    BTree tree = new BTree(1000, 101,  new File("data/btree/benchmark"));
    long time = System.currentTimeMillis();
    /*for (int i = 0; i < 10000; i++) {
      tree.insertData(i, "lolololololo".getBytes());
    }
    System.out.println(System.currentTimeMillis() - time);*/
    try {
      PrintWriter pw = new PrintWriter("benchmark_file");
      Random random = new Random();
      random.setSeed(1234542);
      for (int i = 0; i < 1000; i++) {
        pw.write(random.nextInt() + ";" + randomString(random.nextInt(200) + 50) +"\n");
      }
      pw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static String randomString(int targetStringLength) {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
      int randomLimitedInt = leftLimit + (int)
              (random.nextFloat() * (rightLimit - leftLimit + 1));
      buffer.append((char) randomLimitedInt);
    }
    return buffer.toString();
  }

}
