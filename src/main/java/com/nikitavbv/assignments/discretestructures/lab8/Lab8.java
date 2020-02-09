package com.nikitavbv.assignments.discretestructures.lab8;

import com.nikitavbv.assignments.discretestructures.lab8.algorithm.FordFlukersonAlgorithm;
import com.nikitavbv.assignments.discretestructures.lab8.algorithm.GomoryHuAlgorithm;
import com.nikitavbv.assignments.discretestructures.lab8.algorithm.MaxPairsFirstWay;
import com.nikitavbv.assignments.discretestructures.lab8.algorithm.MaxPairsMatrixWay;
import com.nikitavbv.assignments.discretestructures.lab8.graph.Graph;
import com.nikitavbv.assignments.discretestructures.lab8.graph.GraphEdge;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Lab8 {

  private static final Graph GRAPH_FROM_TASK = new Graph(
          10,
          Arrays.asList(
                  new GraphEdge(6, 7, 17),
                  new GraphEdge(7, 2, 21),
                  new GraphEdge(2, 9, 22),
                  new GraphEdge(9, 1, 23),
                  new GraphEdge(1, 3, 15),
                  new GraphEdge(3, 8, 16),
                  new GraphEdge(8, 6, 14),
                  new GraphEdge(6, 5, 12),
                  new GraphEdge(5, 4, 5),
                  new GraphEdge(4, 0, 17),
                  new GraphEdge(0, 5, 23),
                  new GraphEdge(5, 7, 27),
                  new GraphEdge(4, 2, 16),
                  new GraphEdge(5, 9, 41),
                  new GraphEdge(1, 2, 7)
          )
  );

  private static final Graph TWO_HALVES_GRAPH_FROM_TASK = new Graph(
          12,
          Arrays.asList(
                  new GraphEdge(0, 9),
                  new GraphEdge(0, 11),
                  new GraphEdge(1, 11),
                  new GraphEdge(2, 6),
                  new GraphEdge(2, 11),
                  new GraphEdge(3, 8),
                  new GraphEdge(3, 9),
                  new GraphEdge(3, 10),
                  new GraphEdge(4, 7),
                  new GraphEdge(4, 11),
                  new GraphEdge(5, 6),
                  new GraphEdge(5, 9),
                  new GraphEdge(5, 10)
          )
  );

  private static Random random = new Random();

  public static void main(String[] args) throws IOException {
    fordFlukersonTest();
    pairsTest();
    //flowSearchSpeedTest();
    //pairsSearchSpeedTest();
  }

  private static void fordFlukersonTest() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("From: ");
    int from = Integer.parseInt(reader.readLine()) - 1;
    System.out.println("To: ");
    int to = Integer.parseInt(reader.readLine()) - 1;

    FordFlukersonAlgorithm ffa = new FordFlukersonAlgorithm(GRAPH_FROM_TASK);
    int flow = ffa.getMaximumFlowBetweenNodes(from, to);
    System.out.println("Maximum flow is: " + flow);
    System.out.println("Min cut: ");
    ffa.getMinCutEdges(from).forEach(System.out::println);
  }

  private static void pairsTest() {
    MaxPairsFirstWay firstWay = new MaxPairsFirstWay(false);
    Map<Integer, Integer> result = firstWay.findMaxPairs(TWO_HALVES_GRAPH_FROM_TASK);
    for (Integer key : result.keySet()) {
      System.out.println(key + " -> " + result.get(key));
    }
    System.out.println("---------------");
    MaxPairsMatrixWay matrixWay = new MaxPairsMatrixWay(false);
    result = firstWay.findMaxPairs(TWO_HALVES_GRAPH_FROM_TASK);
    for (Integer key : result.keySet()) {
      System.out.println(key + " -> " + result.get(key));
    }
  }

  private static void pairsSearchSpeedTest() {
    for (int i = 500; i <= 2000; i+=100) {
      Graph graph = randomHalvesGraph(i);
      MaxPairsFirstWay maxPairsFirstWay = new MaxPairsFirstWay(true);
      long start = System.currentTimeMillis();
      maxPairsFirstWay.findMaxPairs(graph);
      long first = System.currentTimeMillis() - start;
      MaxPairsMatrixWay matrixWay = new MaxPairsMatrixWay(true);
      start = System.currentTimeMillis();
      matrixWay.findMaxPairs(graph);
      long second = System.currentTimeMillis() - start;
      System.out.println(i + "\t" + first + "\t" + second);
    }
  }

  private static void flowSearchSpeedTest() {
    for (int i = 1; i <= 20; i ++) {
      Graph graph = randomGraph(i);
      FordFlukersonAlgorithm ffa = new FordFlukersonAlgorithm(graph);
      long start = System.currentTimeMillis();
      ffa.getMaximumFlowBetweenAllNodes();
      long ffaEnd = System.currentTimeMillis() - start;
      GomoryHuAlgorithm gh = new GomoryHuAlgorithm(graph);
      gh.getMaximumFlowBetweenAllNodes();
      long ghEnd = System.currentTimeMillis() - start;
      System.out.println(i + "\t" + ffaEnd + "\t" + ghEnd);
    }
  }

  private static Graph randomGraph(int nodes) {
    List<GraphEdge> edges = new ArrayList<>();
    int totalEdges = random.nextInt(nodes * nodes);
    for (int i = 0; i < totalEdges; i++) {
      edges.add(new GraphEdge(random.nextInt(nodes), random.nextInt(nodes), random.nextInt(100)));
    }
    return new Graph(nodes, edges);
  }

  private static Graph randomHalvesGraph(int nodes) {
    List<GraphEdge> edges = new ArrayList<>();
    int halfSize = nodes / 2;
    int totalEdges = random.nextInt(nodes * nodes);
    for (int i = 0; i < totalEdges; i++) {
      edges.add(new GraphEdge(random.nextInt(halfSize), random.nextInt(halfSize) + halfSize));
    }
    return new Graph(nodes, edges);
  }
}
