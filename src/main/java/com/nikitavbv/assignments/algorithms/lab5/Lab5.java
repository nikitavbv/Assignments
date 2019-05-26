package com.nikitavbv.assignments.algorithms.lab5;

import java.util.Random;

public class Lab5 {

  private static final int TOTAL_GRAPH_NODES = 100;
  private static final int GRAPH_MAX_NODES_CONNECTIONS = 20;
  private static final int GRAPH_MIN_NODES_CONNECTIONS = 1;
  private static final int REPORTING_STEP = 100;
  private static final int ITERATIONS_STEP = 2000;
  private static final int TOTAL_BEES = 300;
  private static final int TOTAL_SCOUT_BEES = 20;

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
    Random random = new Random();
    Graph graph = graphFactory.makeGraph();
    BeeAlgorithm algo = new BeeAlgorithm(graph, new GreedyGraphColorer(), TOTAL_BEES, TOTAL_SCOUT_BEES, random);
    algo.runIteration();
    int iter = 1;
    double prevScore = algo.best().getScore();
    while (true) {
      if (iter % ITERATIONS_STEP == 0) {
        if (algo.best().getScore() >= prevScore) {
          break;
        }
        prevScore = algo.best().getScore();
      }
      if (iter % REPORTING_STEP == 0) {
        System.out.println("Iteration: " + iter + " Colors: " + algo.best().totalColors() + " Score: " + algo.best().getScore());
      }
      algo.runIteration();
      iter++;
    }

    System.out.println("Result: " + algo.best().totalColors() + " colors in " + iter + " iterations");
  }
}
