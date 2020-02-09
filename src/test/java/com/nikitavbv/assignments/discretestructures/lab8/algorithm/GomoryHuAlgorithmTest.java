package com.nikitavbv.assignments.discretestructures.lab8.algorithm;

import com.nikitavbv.assignments.discretestructures.lab8.graph.Graph;
import com.nikitavbv.assignments.discretestructures.lab8.graph.GraphEdge;
import java.util.Arrays;
import org.junit.Test;

public class GomoryHuAlgorithmTest {

  @Test
  public void exampleFromLecture() {
    Graph graph = new Graph(7, Arrays.asList(
            new GraphEdge(0, 1, 3),
            new GraphEdge(0, 3, 5),
            new GraphEdge(1, 2, 2),
            new GraphEdge(2, 3, 4),
            new GraphEdge(2, 6, 6),
            new GraphEdge(1, 4, 7),
            new GraphEdge(4, 6, 3),
            new GraphEdge(3, 5, 1),
            new GraphEdge(5, 6, 4)
    ));
    GomoryHuAlgorithm algo = new GomoryHuAlgorithm(graph);
    algo.getMaximumFlowBetweenAllNodes();
  }

}
