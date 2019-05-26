package com.nikitavbv.assignments.algorithms.lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ColoredGraph extends Graph implements Comparable<ColoredGraph> {

  private int[] colors;

  public ColoredGraph(Graph graph) {
    this(graph.getAdjacencyMatrix());
  }

  ColoredGraph(int[][] adjMatrix, int[] colors) {
    this(adjMatrix);
    this.colors = colors;
  }

  public ColoredGraph(int[][] adjacencyMatrix) {
    super(adjacencyMatrix);
    colors = new int[adjacencyMatrix.length];
  }

  Optional<ColoredGraph> generateFromNeighbourhood(Random random) {
    int node = random.nextInt(nodeCount());
    List<Integer> connectedNodes = new ArrayList<>(connectedNodes(node));

    if (connectedNodes.size() == 0) {
      if (this.colors[node] == 1) {
        return Optional.empty();
      } else {
        int[] nColors = new int[colors.length];
        System.arraycopy(colors, 0, nColors, 0, nColors.length);
        nColors[node] = 1;
        return Optional.of(new ColoredGraph(getAdjacencyMatrix(), nColors));
      }
    }

    int otherNode = connectedNodes.get(random.nextInt(connectedNodes.size()));
    int[] nColors = new int[colors.length];
    System.arraycopy(colors, 0, nColors, 0, nColors.length);
    nColors[node] = colors[otherNode];
    nColors[otherNode] = colors[node];

    if (!isValid(nColors, node, connectedNodes) || !isValid(nColors, otherNode, connectedNodes)) {
      return Optional.empty();
    }

    reduce(nColors, node, connectedNodes);
    reduce(nColors, otherNode, connectedNodes);

    return Optional.of(new ColoredGraph(getAdjacencyMatrix(), nColors));
  }

  private void reduce(int[] colors, int node, List<Integer> connectedNodes) {
    Set<Integer> colorsTaken = new HashSet<>();
    for (int connectedNode : connectedNodes) {
      colorsTaken.add(colors[connectedNode]);
    }

    int thisNodeColor = 1;
    while (colorsTaken.contains(thisNodeColor)) {
      thisNodeColor++;
    }

    colors[node] = thisNodeColor;
  }

  private boolean isValid(int[] colors, int node, List<Integer> connectedNodes) {
    int thisNodeColor = colors[node];
    for (int otherNode : connectedNodes) {
      if (colors[otherNode] == thisNodeColor) {
        return false;
      }
    }
    return true;
  }

  public String colorSeqToString() {
    return Arrays.stream(colors).mapToObj(Integer::toString).collect(Collectors.joining(":"));
  }

  public int getColor(int node) {
    return colors[node];
  }

  public void setColor(int node, int color) {
    colors[node] = color;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ColoredGraph)) return false;
    ColoredGraph that = (ColoredGraph) o;
    return Arrays.equals(colors, that.colors);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(colors);
  }

  int totalColors() {
    int score = 0;
    for (int color : colors) {
      if (color > score) {
        score = color;
      }
    }
    return score;
  }

  int getScore() {
    return totalColors();
  }

  @Override
  public int compareTo(ColoredGraph o) {
    return Integer.compare(o.getScore(), getScore());
  }
}
