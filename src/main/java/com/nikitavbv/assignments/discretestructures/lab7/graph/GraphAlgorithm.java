package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.util.List;

public interface GraphAlgorithm {

  String name();
  void findAllPaths(Graph graph);
  List<Integer> findShortestPath(Graph graph, int from, int to, boolean traceRoute);

}
