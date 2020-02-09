package com.nikitavbv.assignments.discretestructures.lab8.algorithm;

import static org.junit.Assert.assertEquals;

import com.nikitavbv.assignments.discretestructures.lab8.graph.DirectedGraphEdge;
import com.nikitavbv.assignments.discretestructures.lab8.graph.Graph;
import java.util.Arrays;
import org.junit.Test;

public class FordFlukersonAlgorithmTest {

  @Test
  public void simpleTest() {
    // a b c d e f z
    // 0 1 2 3 4 5 6
    Graph graph = new Graph(
            7,
            Arrays.asList(
                    new DirectedGraphEdge(0, 1, 3),
                    new DirectedGraphEdge(0, 2, 5),
                    new DirectedGraphEdge(1, 4, 8),
                    new DirectedGraphEdge(2, 3, 3),
                    new DirectedGraphEdge(1, 3, 1),
                    new DirectedGraphEdge(2, 4, 2),
                    new DirectedGraphEdge(4, 6, 7),
                    new DirectedGraphEdge(5, 6, 4),
                    new DirectedGraphEdge(3, 5, 9),
                    new DirectedGraphEdge(5, 6, 4)
            )
    );
    FordFlukersonAlgorithm algo = new FordFlukersonAlgorithm(graph);
    assertEquals(8, algo.getMaximumFlowBetweenNodes(0, 6));
  }

}
