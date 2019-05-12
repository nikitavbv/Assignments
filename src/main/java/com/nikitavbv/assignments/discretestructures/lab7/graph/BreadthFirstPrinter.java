package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstPrinter implements GraphPrinter {

  @Override
  public String name() {
    return "Breath first traversal";
  }

  @Override
  public void print(Graph graph, OutputStream out) throws IOException {
    final int from = 0;
    int[][] adjacencyMatrix = graph.adjacencyMatrix();
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    queue.add(from);
    visited.add(from);

    out.write('[');
    while (!queue.isEmpty()) {
      int currentNode = queue.remove();

      out.write((currentNode + " ").getBytes());

      for (int otherNode = 0; otherNode < adjacencyMatrix[currentNode].length; otherNode++) {
        if (currentNode == otherNode) {
          continue;
        }

        if (adjacencyMatrix[currentNode][otherNode] != -1 && !visited.contains(otherNode)) {
          visited.add(otherNode);
          queue.add(otherNode);
        }
      }
    }

    out.write(']');
    out.write('\n');
  }
}
