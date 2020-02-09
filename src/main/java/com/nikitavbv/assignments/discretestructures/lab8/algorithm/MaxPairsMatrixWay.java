package com.nikitavbv.assignments.discretestructures.lab8.algorithm;

import com.nikitavbv.assignments.discretestructures.lab8.graph.Graph;
import com.nikitavbv.assignments.discretestructures.lab8.graph.GraphEdge;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class MaxPairsMatrixWay {

  private boolean debug;

  public MaxPairsMatrixWay(boolean debug) {
    this.debug = debug;
  }

  public Map<Integer, Integer> findMaxPairs(Graph graph) {
    int halfSize = graph.totalNodes() / 2;
    int[][] matrix = new int[halfSize][halfSize];

    for (int i = 0; i < halfSize; i++) {
      List<Integer> connected = graph.streamEdges(i).map(GraphEdge::to).collect(Collectors.toList());
      for (int j : connected) {
        matrix[i][j-halfSize] = 1;
      }
    }

    // System.out.println(Arrays.deepToString(matrix));

    Queue<Integer> toCheck = new LinkedList<>();
    for (int i = 0; i < halfSize; i++) {
      toCheck.add(i);
    }

    while (!toCheck.isEmpty()) {
      int i = toCheck.remove();
      int free = -1;
      for (int j = 0; j < halfSize; j++) {
        if (matrix[i][j] == 1) {
          free = j;
          break;
        }
      }

      if (free == -1) {
        int best = Integer.MAX_VALUE;
        int found = -1;
        for (int k = 0; k < halfSize; k++) {
          if (matrix[i][k] != 0) {
            int takenBy = -1;
            for (int j = 0; j < halfSize; j++) {
              if(matrix[j][k] == 2) {
                takenBy = j;
                break;
              }
            }
            if (takenBy != -1) {
              if (takenBy < best) {
                best = takenBy;
                found = k;
              }
            }
          }
        }
        if (debug) continue;
        toCheck.add(best);
        matrix[i][found] = 2;
        matrix[best][found] = 1;
      } else {
        matrix[i][free] = 2;
      }
    }

    Map<Integer, Integer> result = new HashMap<>();

    for (int i = 0; i < halfSize; i++) {
      for (int j = 0; j < halfSize; j++) {
        if (i == j) {
          continue;
        }
        if (matrix[i][j] == 2) {
          result.put(i, j + halfSize);
        }
      }
    }

    return result;
  }
}
