package com.nikitavbv.assignments.discretestructures.lab8.algorithm;

import com.nikitavbv.assignments.discretestructures.lab8.graph.Graph;
import java.util.HashMap;
import java.util.Map;

public abstract class GraphFlowAlgorithm {

  private Graph graph;

  public GraphFlowAlgorithm(Graph graph) {
    this.graph = graph;
  }

  public Map<Pair<Integer, Integer>, Integer> getMaximumFlowBetweenAllNodes() {
    Map<Pair<Integer, Integer>, Integer> result = new HashMap<>();
    for (int from = 0; from < graph.totalNodes(); from++) {
      for (int to = 0; to < graph.totalNodes(); to++) {
        if (to == from) {
          continue;
        }

        result.put(new Pair<>(from, to), getMaximumFlowBetweenNodes(from, to));
      }
    }
    return result;
  }

  abstract int getMaximumFlowBetweenNodes(int from, int to);

  protected Graph graph() {
    return graph;
  }
}
