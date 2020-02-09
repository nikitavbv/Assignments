package com.nikitavbv.assignments.discretestructures.lab8.algorithm;

import com.nikitavbv.assignments.discretestructures.lab8.graph.DirectedGraphEdge;
import com.nikitavbv.assignments.discretestructures.lab8.graph.Graph;
import com.nikitavbv.assignments.discretestructures.lab8.graph.GraphEdge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FordFlukersonAlgorithm extends GraphFlowAlgorithm {

  private Map<GraphEdge, Integer> flow = new HashMap<>();

  public FordFlukersonAlgorithm(Graph graph) {
    super(graph);
  }

  @Override
  public int getMaximumFlowBetweenNodes(int from, int to) {
    int maximumFlow = 0;

    flow.clear();

    // perform depth first traversal
    Optional<List<Integer>> pathOptional = depthFirstSearch(from, to);
    int iter = 0;
    while (pathOptional.isPresent()) {
      List<Integer> path = pathOptional.get();
      int maximumFlowViaPath = getPossibleFlowViaPath(path);
      updateFlowOnPath(path, maximumFlowViaPath);
      maximumFlow += maximumFlowViaPath;
      pathOptional = depthFirstSearch(from, to);
      iter++;
      if (iter == graph().totalNodes() * 10) {
        break;
      }
    }

    return maximumFlow;
  }

  private void updateFlowOnPath(List<Integer> path, int flowDelta) {
    for (int i = 1; i < path.size(); i++) {
      final int from = path.get(i - 1);
      final int to = path.get(i);
      final GraphEdge edge = graph().edgeBetween(from, to).orElseThrow(noEdgeException(from, to));
      if (edge.from() == from) {
        flow.put(edge, flow.getOrDefault(edge,0) + flowDelta);
      } else if (edge instanceof DirectedGraphEdge) {
        flow.put(edge, flow.get(edge) - flowDelta);
      } else {
        flow.put(edge, flow.getOrDefault(edge,0) + flowDelta);
      }
    }
  }

  private int getPossibleFlowViaPath(List<Integer> path) {
    int maximumFlow = -1;

    for (int i = 1; i < path.size(); i++) {
      int flow = getPossibleFlowThroughEdge(path.get(i-1), path.get(i));
      if (maximumFlow == -1) {
        maximumFlow = flow;
        continue;
      }
      maximumFlow = Math.min(maximumFlow, flow);
    }

    return maximumFlow;
  }

  @SuppressWarnings("Duplicates")
  private Optional<List<Integer>> depthFirstSearch(int from, int to) {
    Stack<Integer> stack = new Stack<>();
    Set<Integer> beenTo = new HashSet<>();
    Map<Integer, Integer> prev = new HashMap<>();

    stack.add(from);

    while (!stack.isEmpty()) {
      Integer currentNode = stack.pop();
      beenTo.add(currentNode);
      if (currentNode == to) {
        List<Integer> path = new ArrayList<>();
        int cursor = to;
        while (cursor != from) {
          path.add(0, cursor);
          cursor = prev.get(cursor);
        }
        path.add(0, from);
        return Optional.of(path);
      }
      graph().streamEdges(currentNode)
              .filter(edge -> edge.from() == currentNode ? !beenTo.contains(edge.to()) : !beenTo.contains(edge.from()))
              .filter(edge -> getPossibleFlowThroughEdge(edge, currentNode) != 0)
              .map(edge -> edge.from() == currentNode ? edge.to() : edge.from())
              .forEach(el -> {
                stack.push(el);
                prev.put(el, currentNode);
              });
    }

    return Optional.empty();
  }

  @SuppressWarnings("Duplicates")
  public Optional<Integer> getMinCut(int from) {
    boolean[] reachable = new boolean[graph().totalNodes()];
    Stack<Integer> stack = new Stack<>();
    Set<Integer> beenTo = new HashSet<>();

    stack.add(from);

    while (!stack.isEmpty()) {
      Integer currentNode = stack.pop();
      if (currentNode >= reachable.length) continue;
      reachable[currentNode] = true;
      beenTo.add(currentNode);
      graph().streamEdges(currentNode)
              .filter(edge -> edge.from() == currentNode ? !beenTo.contains(edge.to()) : !beenTo.contains(edge.from()))
              .filter(edge -> getPossibleFlowThroughEdge(edge, currentNode) != 0)
              .map(edge -> edge.from() == currentNode ? edge.to() : edge.from())
              .forEach(stack::push);
    }

    // System.out.println(Arrays.toString(reachable));

    /*return graph().streamEdges()
            .filter(edge -> reachable[edge.to()] != reachable[edge.from()])
            .collect(Collectors.toList());*/

    for (int i = 0; i < reachable.length; i++) {
      if (i == from) {
        continue;
      }

      if (!reachable[i]) {
        continue;
      }

      return Optional.of(i);
    }
    return Optional.empty();
  }

  @SuppressWarnings("Duplicates")
  public List<GraphEdge> getMinCutEdges(int from) {
    boolean[] reachable = new boolean[graph().totalNodes()];
    Stack<Integer> stack = new Stack<>();
    Set<Integer> beenTo = new HashSet<>();

    stack.add(from);

    while (!stack.isEmpty()) {
      Integer currentNode = stack.pop();
      reachable[currentNode] = true;
      beenTo.add(currentNode);
      graph().streamEdges(currentNode)
              .filter(edge -> edge.from() == currentNode ? !beenTo.contains(edge.to()) : !beenTo.contains(edge.from()))
              .filter(edge -> getPossibleFlowThroughEdge(edge, currentNode) != 0)
              .map(edge -> edge.from() == currentNode ? edge.to() : edge.from())
              .forEach(stack::push);
    }

    // System.out.println(Arrays.toString(reachable));

    return graph().streamEdges()
            .filter(edge -> reachable[edge.to()] != reachable[edge.from()])
            .collect(Collectors.toList());
  }

  private int getPossibleFlowThroughEdge(int from, int to) {
    return getPossibleFlowThroughEdge(graph().edgeBetween(from, to).orElseThrow(noEdgeException(from, to)), from);
  }

  private int getPossibleFlowThroughEdge(GraphEdge edge, int from) {
    if (edge.from() == from) {
      return edge.capacity() - flow.getOrDefault(edge, 0);
    }

    if (edge instanceof DirectedGraphEdge) {
      return flow.getOrDefault(edge, 0);
    }

    return edge.capacity() - flow.getOrDefault(edge, 0);
  }

  private Supplier<AssertionError> noEdgeException(int from, int to) {
    return () -> {
      throw new AssertionError(String.format("No edge between %d and %d", from, to));
    };
  }
}
