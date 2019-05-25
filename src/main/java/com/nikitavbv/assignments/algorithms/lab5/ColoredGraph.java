package com.nikitavbv.assignments.algorithms.lab5;

public class ColoredGraph extends Graph {

  private int[][] colors;

  public ColoredGraph(int[][] adjacencyMatrix) {
    super(adjacencyMatrix);
    colors = new int[adjacencyMatrix.length][adjacencyMatrix[0].length];
  }

  public int getColor(int x, int y) {
    return colors[x][y];
  }

  public void setColor(int x, int y, int color) {
    colors[x][y] = color;
  }
}
