package com.nikitavbv.assignments.algorithms.lab5;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GreedyGraphColorer implements GraphColorer {

  private Random random;

  @Override
  public ColoredGraph color(Graph graph) {
    ColoredGraph colored = new ColoredGraph(graph);

    List<Integer> nodesToVisit = IntStream.range(0, graph.nodeCount()).boxed().collect(Collectors.toList());
    Collections.shuffle(nodesToVisit);
    while (nodesToVisit.size() > 0) {
      int node = nodesToVisit.remove(0);
      Set<Integer> takenColors = graph.connectedNodes(node)
              .stream()
              .mapToInt(colored::getColor)
              .filter(i -> i != 0)
              .boxed()
              .collect(Collectors.toSet());
      int colorForThisNode = 1;
      while (takenColors.contains(colorForThisNode)) {
        colorForThisNode++;
      }
      colored.setColor(node, colorForThisNode);
    }

    return colored;
  }

}
