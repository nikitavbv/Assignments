package com.nikitavbv.assignments.algorithms.lab4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class AStarSearch {

  List<Integer> findRoute(Graph graph, int from, int to, Location[] locations) {
    Set<Integer> closedSet = new HashSet<>();
    Set<Integer> openSet = new HashSet<>();
    openSet.add(from);
    Map<Integer, Integer> cameFrom = new HashMap<>();

    while(!openSet.isEmpty()) {
      int current = openSet.stream()
              .sorted(Comparator.comparingDouble(n -> locations[to].distanceTo(locations[n])))
              .collect(Collectors.toList()).get(0);
      if (current == to) {
        break;
      }
      openSet.remove(current);
      closedSet.add(current);

      for (int i = 0; i < graph.adjacencyMatrix()[current].length; i++) {
        if (graph.adjacencyMatrix()[current][i] == -1 || current == i) {
          continue;
        }

        if (closedSet.contains(i)) {
          continue;
        }

        double ts = locations[to].distanceTo(locations[current]) + graph.adjacencyMatrix()[current][i];
        if (!openSet.contains(i)) {
          openSet.add(i);
        } else if (ts >= locations[to].distanceTo(locations[i])) {
          continue;
        }

        cameFrom.put(i, current);
      }
    }

    List<Integer> route = new ArrayList<>();
    int cursor = to;
    while (cursor != from) {
      route.add(0, cursor);
      cursor = cameFrom.get(cursor);
    }
    route.add(0, from);

    return route;
  }

}
