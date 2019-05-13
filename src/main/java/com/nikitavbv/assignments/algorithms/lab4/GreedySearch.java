package com.nikitavbv.assignments.algorithms.lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@SuppressWarnings("Duplicates")
class GreedySearch {

  List<Integer> findRoute(Graph graph, int from, int to, Location[] locations) {
    double[][] adjacencyMatrix = graph.adjacencyMatrix();
    Stack<Integer> stack = new Stack<>();
    Set<Integer> beenTo = new HashSet<>();
    List<Integer> route = new ArrayList<>();
    Map<Integer, Integer> discoveredBy = new HashMap<>();
    stack.push(from);
    beenTo.add(from);
    boolean routeFound = false;
    while (!stack.isEmpty()) {
      int currentNode = stack.pop();

      if (currentNode == to) {
        routeFound = true;
        break;
      }

      IntStream.range(0, graph.adjacencyMatrix().length)
              .filter(n -> !beenTo.contains(n) && adjacencyMatrix[currentNode][n] != -1 && n != currentNode)
              .boxed()
              .sorted(Comparator.comparingDouble(n -> locations[to].distanceTo(locations[n])))
              .forEach(n -> {
                stack.push(n);
                beenTo.add(n);
                discoveredBy.put(n, currentNode);
              });
    }

    if (!routeFound) {
      throw new AssertionError("Failed to find route from " + from + " to " + to);
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
