package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch implements GraphAlgorithm {

  @Override
  public String name() {
    return "Breadth first search";
  }

  @Override
  public void findAllPaths(Graph graph) {
    throw new RuntimeException("Not implemented!");
  }

  @Override
  public List<Integer> findShortestPath(Graph graph, int from, int to, boolean traceRoute) {
    int[][] adjacencyMatrix = graph.adjacencyMatrix();
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    List<Integer> route = new ArrayList<>();
    Map<Integer, Integer> discoveredBy = new HashMap<>();

    queue.add(from);
    visited.add(from);
    while (!queue.isEmpty()) {
      int currentNode = queue.remove();

      if (currentNode == to) {
        // System.out.println("Found!");
        break;
      }

      // System.out.println("Visiting " + currentNode);

      for (int otherNode = 0; otherNode < adjacencyMatrix[currentNode].length; otherNode++) {
        if (currentNode == otherNode) {
          continue;
        }

        if (adjacencyMatrix[currentNode][otherNode] != -1 && !visited.contains(otherNode)) {
          visited.add(otherNode);
          queue.add(otherNode);
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
