package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.io.IOException;
import java.io.OutputStream;

public interface GraphPrinter {

  String name();
  void print(Graph graph, OutputStream out) throws IOException;
}
