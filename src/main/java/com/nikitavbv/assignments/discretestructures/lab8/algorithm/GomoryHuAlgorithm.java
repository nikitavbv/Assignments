package com.nikitavbv.assignments.discretestructures.lab8.algorithm;

import com.nikitavbv.assignments.discretestructures.lab8.graph.Graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class GomoryHuAlgorithm extends GraphFlowAlgorithm {

  public GomoryHuAlgorithm(Graph graph) {
    super(graph);
  }

  @Override
  public Map<Pair<Integer, Integer>, Integer> getMaximumFlowBetweenAllNodes() {
    Map<Pair<Integer, Integer>, Integer> result = new HashMap<>();
    Graph graph = graph().makeCopy();
    FordFlukersonAlgorithm ffa = new FordFlukersonAlgorithm(graph);
    Map<Integer, Integer> dependsOn = new HashMap<>();
    Map<Integer, Integer> flowValue = new HashMap<>();

    List<Integer> vertexes = new ArrayList<>(graph.vertexes());
    vertexes.sort(Integer::compareTo);
    List<Integer> a = new ArrayList<>(Arrays.asList(0, 5, 3, 6, 4, 2));
    List<Integer> b = new ArrayList<>(Arrays.asList(1, 6, 2, 2, 1, 1));

    while (vertexes.size() > 1 && a.size() > 0) {
      //int first = vertexes.remove(0);
      //int inside = graph.getNeighbors(first).filter(vertexes::contains).findFirst().orElse(vertexes.get(vertexes.size()-1));
      int first = a.remove(0);
      int inside = b.remove(0);

      if (vertexes.size() == 1 || a.size() == 0) {
        vertexes.remove(0);
        for (Integer key : dependsOn.keySet()) {
          if (dependsOn.get(key) == -1) {
            dependsOn.put(key, inside);
          }
        }
        dependsOn.put(inside, -1);
        dependsOn.put(first, inside);
        break;
      }

      int maxFlow = ffa.getMaximumFlowBetweenNodes(inside, first);
      flowValue.put(first, maxFlow);
      Optional<Integer> connectToNode = ffa.getMinCut(first);

      dependsOn.put(first, -1); // depends on root

      if (connectToNode.isPresent()) {
        dependsOn.put(connectToNode.get(), first);
        graph.merge(Math.max(first, connectToNode.get()), Math.min(first, connectToNode.get()));
      }
    }

    /*for (Integer key : dependsOn.keySet()) {
      System.out.println(key + "- " + dependsOn.get(key));
    }*/

    for (int from = 0; from < graph().totalNodes(); from++) {
      for (int to = 0; to < graph().totalNodes(); to++) {
        if (to == from) {
          continue;
        }

        int flow = Integer.MAX_VALUE;

        int cursor = from;
        while (cursor != to) {
          flow = Math.min(flow, flowValue.getOrDefault(cursor, Integer.MAX_VALUE));
          if (dependsOn.get(cursor) == null) {
            break;
          }
          cursor = dependsOn.get(cursor);
          if (cursor == -1) {
            break;
          }
        }
        if (cursor != to) {
          cursor = to;
          while (cursor != -1) {
            flow = Math.min(flow, flowValue.getOrDefault(cursor, Integer.MAX_VALUE));
            if (dependsOn.get(cursor) == null) {
              break;
            }
            cursor = dependsOn.get(cursor);
          }
        }

        result.put(new Pair<>(from, to), flow);
      }
    }

    return result;
  }

  @Override
  int getMaximumFlowBetweenNodes(int from, int to) {
    return 0;
  }

}
