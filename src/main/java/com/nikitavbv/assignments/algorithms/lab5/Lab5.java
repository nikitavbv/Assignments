package com.nikitavbv.assignments.algorithms.lab5;

public class Lab5 {

  private static final int TOTAL_GRAPH_NODES = 100;
  private static final int GRAPH_MAX_NODES_CONNECTIONS = 20;
  private static final int GRAPH_MIN_NODES_CONNECTIONS = 1;
  private static final int TOTAL_BEES = 30;
  private static final int TOTAL_SCOUT_BEES = 2;

  private GraphFactory graphFactory;

  public static void main(String[] args) {
    Lab5 lab = new Lab5();
    lab.setGraphFactory(new RandomGraphFactory()
            .withMaxConnections(GRAPH_MAX_NODES_CONNECTIONS)
            .withMinConnections(GRAPH_MIN_NODES_CONNECTIONS)
            .withTotalNodes(TOTAL_GRAPH_NODES)
    );
    lab.run();
  }

  private void setGraphFactory(GraphFactory factory) {
    this.graphFactory = factory;
  }

  private void run() {
    Graph graph = graphFactory.makeGraph();
  }
}
