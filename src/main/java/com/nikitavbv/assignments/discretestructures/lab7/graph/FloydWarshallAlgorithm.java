package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FloydWarshallAlgorithm implements GraphAlgorithm {

  private static final int REPORT_INTERVAL = 60000;
  private int[][] next = null;

  @Override
  public String name() {
    return "Floyd-Warshall algorithm";
  }

  @Override
  public void findAllPaths(Graph graph) {
    generateNextArr(graph);
  }

  @Override
  public List<Integer> findShortestPath(Graph graph, int from, int to, boolean traceRoute) {
    generateNextArr(graph);

    if (!traceRoute) {
      return Collections.emptyList();
    }

    List<Integer> route = new ArrayList<>();
    route.add(from);
    int cursor = from;
    while (cursor != to) {
      cursor = next[cursor][to];
      route.add(cursor);
    }

    return route;
  }

  private void generateNextArr(Graph graph) {
    int[][] adjacencyMatrix = graph.adjacencyMatrix();
    int[][] distance = new int[adjacencyMatrix.length][adjacencyMatrix.length];
    next = new int[adjacencyMatrix.length][adjacencyMatrix.length];
    Arrays.stream(distance).forEach(arr -> Arrays.fill(arr, Integer.MAX_VALUE));

    for (int i = 0; i < adjacencyMatrix.length; i++) {
      distance[i][i] = 0;
      next[i][i] = i;

      for (int j = 0; j < adjacencyMatrix[i].length; j++) {
        if (i == j) {
          continue;
        }

        if (adjacencyMatrix[i][j] != -1) {
          distance[i][j] = adjacencyMatrix[i][j];
          next[i][j] = j;
        }
      }
    }

    long lastReportTime = System.currentTimeMillis();
    for (int k = 0; k < adjacencyMatrix.length; k++) {
      if (System.currentTimeMillis() > lastReportTime + REPORT_INTERVAL) {
        System.out.printf("Running Floyd-Warshall: %d/%d%n", k, adjacencyMatrix.length);
        lastReportTime = System.currentTimeMillis();
      }

      for (int i = 0; i < adjacencyMatrix.length; i++) {
        for (int j = 0; j < adjacencyMatrix.length; j++) {
          if (distance[i][j] > distance[i][k] + distance[k][j] && distance[i][k] + distance[k][j] > 0) {
            distance[i][j] = distance[i][k] + distance[k][j];
            next[i][j] = next[i][k];
          }
        }
      }
    }
  }
}
