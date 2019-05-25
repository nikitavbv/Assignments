package com.nikitavbv.assignments.algorithms.lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomGraphFactory implements GraphFactory {

  private Random random = new Random();
  private int totalNodes;
  private int minConnections;
  private int maxConnections;

  public RandomGraphFactory withTotalNodes(int totalNodes) {
    this.totalNodes = totalNodes;
    return this;
  }

  public RandomGraphFactory withMinConnections(int minConnections) {
    this.minConnections = minConnections;
    return this;
  }

  public RandomGraphFactory withMaxConnections(int maxConnections) {
    this.maxConnections = maxConnections;
    return this;
  }

  @Override
  public Graph makeGraph() {
    int[][] adjacencyMatrix = new int[totalNodes][totalNodes];

    for (int i = 0; i < adjacencyMatrix.length; i++) {
      int minInRow = minConnections;
      int maxInRow = maxConnections;
      for (int j = 0; j < i; j++) {
        if (adjacencyMatrix[i][j] != 0) {
          minInRow--;
          maxInRow--;
        }
      }
      int[] row = randomConnectionsArray(adjacencyMatrix.length - i, minInRow, maxInRow);
      for (int j = 0; j < row.length; j++) {
        adjacencyMatrix[i][i + j] = row[j];
        adjacencyMatrix[i + j][i] = row[j];
      }
    }

    return new Graph(adjacencyMatrix);
  }

  private int[] randomConnectionsArray(int length, int minInRow, int maxInRow) {
    int totalToAdd = minInRow + random.nextInt(maxInRow - minInRow + 1);
    int[] result = new int[length];
    for (int i = 0; i < Math.min(totalToAdd, length); i++) {
      result[i] = 1;
    }
    return shuffledArray(result);
  }

  private int[] shuffledArray(int[] arr) {
    List<Integer> list = new ArrayList<>(arr.length);
    Arrays.stream(arr).forEach(list::add);
    Collections.shuffle(list);
    return list.stream().mapToInt(i -> i).toArray();
  }
}
