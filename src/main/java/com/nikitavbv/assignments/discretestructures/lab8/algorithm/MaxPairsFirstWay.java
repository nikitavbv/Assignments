package com.nikitavbv.assignments.discretestructures.lab8.algorithm;

import com.nikitavbv.assignments.discretestructures.lab8.graph.Graph;
import com.nikitavbv.assignments.discretestructures.lab8.graph.GraphEdge;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class MaxPairsFirstWay {

  private boolean debug;

  public MaxPairsFirstWay(boolean debug) {
    this.debug = debug;
  }

  public Map<Integer, Integer> findMaxPairs(Graph graph) {
    int halfSize = graph.totalNodes() / 2;
    Map<Integer, Integer> result = new HashMap<>();
    Map<Integer, Integer> taken = new HashMap<>();

    Stack<Integer> toProcess = new Stack<>();
    for (int i = halfSize - 1; i >= 0; i--) {
      toProcess.push(i);
    }

    while (!toProcess.isEmpty()) {
      int i = toProcess.pop();
      List<Integer> connected = graph.streamEdges(i).map(GraphEdge::to).filter(n -> !taken.containsKey(n))
              .collect(Collectors.toList());
      if (connected.size() > 0) {
        result.put(i, connected.get(0));
        taken.put(connected.get(0), i);
      } else {
        connected = graph.streamEdges(i).map(GraphEdge::to).collect(Collectors.toList());
        int best = Integer.MAX_VALUE;
        int found = -1;
        for (Integer k : connected) {
          if (taken.get(k) < best) {
            best = taken.get(k);
            found = k;
          }
        }
        if (debug) continue;
        toProcess.add(best);
        result.put(i, found);
        taken.put(found, i);
      }
    }

    return result;
  }

}
