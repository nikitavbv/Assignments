package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DepthFirstPrinter implements GraphPrinter {

  @Override
  public String name() {
    return "Depth first traversal";
  }

  @Override
  public void print(Graph graph, OutputStream out) throws IOException {
    int[][] adjacencyMatrix = graph.adjacencyMatrix();
    Stack<Integer> stack = new Stack<>();
    Set<Integer> beenTo = new HashSet<>();
    final int from = 0;
    stack.push(from);
    beenTo.add(from);

    out.write('[');
    while (!stack.isEmpty()) {
      int currentNode = stack.pop();
      out.write((currentNode + " ").getBytes());

      for (int otherNode = 0; otherNode < adjacencyMatrix[currentNode].length; otherNode++) {
        if (otherNode == currentNode) {
          continue;
        }

        if (adjacencyMatrix[currentNode][otherNode] != -1 && !beenTo.contains(otherNode)) {
          stack.push(otherNode);
          beenTo.add(otherNode);
        }
      }
    }
    out.write(']');
    out.write('\n');
  }
}
