package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch implements GraphAlgorithm {

  @Override
  public String name() {
    return "Depth first search";
  }

  @Override
  public void findAllPaths(Graph graph) {
    throw new RuntimeException("Not implemented!");
  }

  @SuppressWarnings("Duplicates")
  @Override
  public List<Integer> findShortestPath(Graph graph, int from, int to, boolean traceRoute) {
    int[][] adjacencyMatrix = graph.adjacencyMatrix();
    Stack<Integer> stack = new Stack<>();
    Set<Integer> beenTo = new HashSet<>();
    List<Integer> route = new ArrayList<>();
    Map<Integer, Integer> discoveredBy = new HashMap<>();
    stack.push(from);
    beenTo.add(from);
    while (!stack.isEmpty()) {
      int currentNode = stack.pop();

      if (currentNode == to) {
        // System.out.println("Found!");
        break;
      }

      // System.out.println("Visiting " + currentNode);

      for (int otherNode = 0; otherNode < adjacencyMatrix[currentNode].length; otherNode++) {
        if (otherNode == currentNode) {
          continue;
        }

        if (adjacencyMatrix[currentNode][otherNode] != -1 && !beenTo.contains(otherNode)) {
          stack.push(otherNode);
          beenTo.add(otherNode);
          discoveredBy.put(otherNode, currentNode);
        }
      }
    }

    if (!traceRoute) {
      return Collections.emptyList();
    }

    int cursor = to;
    while (cursor != from) {
      route.add(0, cursor);
      cursor = discoveredBy.get(cursor);
    }
    route.add(0, from);

    return route;
  }
}
