package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

@SuppressWarnings("Duplicates")
public class DijkstraAlgorithm implements GraphAlgorithm {

  private int[] edgeTo;

  @Override
  public String name() {
    return "Dijkstra algorithm";
  }

  @Override
  public void findAllPaths(Graph graph) {
    for (int i = 0; i < graph.totalNodes(); i++) {
      calculateDistances(graph, i);
    }
  }

  @Override
  public List<Integer> findShortestPath(Graph graph, int from, int to, boolean traceRoute) {
    calculateDistances(graph, from);

    List<Integer> route = new ArrayList<>();
    int cursor = to;
    while (cursor != from) {
      route.add(0, cursor);
      cursor = edgeTo[cursor];
    }

    route.add(0, from);

    return route;
  }

  private void calculateDistances(Graph graph, int from) {
    int[][] adjacencyMatrix = graph.adjacencyMatrix();
    int[] distanceTo = new int[adjacencyMatrix.length];
    edgeTo = new int[adjacencyMatrix.length];

    Arrays.fill(distanceTo, Integer.MAX_VALUE);
    distanceTo[from] = 0;

    PriorityQueue<NodeEntry> pq = new PriorityQueue<>();
    pq.add(new NodeEntry(from, 0));

    while (!pq.isEmpty()) {
      int minDistanceNode = pq.remove().node;

      for (GraphEdge edge : graph.getEdgesByNode(minDistanceNode)) {
        int w = edge.to();
        if (distanceTo[w] > distanceTo[minDistanceNode] + edge.weight()) {
          distanceTo[w] = distanceTo[minDistanceNode] + edge.weight();
          edgeTo[w] = minDistanceNode;
          pq.remove(new NodeEntry(w, 0));
          pq.add(new NodeEntry(w, distanceTo[w]));
        }
      }

      /*for (int otherNode = 0; otherNode < adjacencyMatrix.length; otherNode++) {
        if (otherNode == minDistanceNode) {
          continue;
        }

        if (adjacencyMatrix[minDistanceNode][otherNode] != - 1) {
          if (distanceTo[otherNode] > distanceTo[minDistanceNode] + adjacencyMatrix[minDistanceNode][otherNode] && !visited.contains(otherNode)) {
            distanceTo[otherNode] = distanceTo[minDistanceNode] + adjacencyMatrix[minDistanceNode][otherNode];
            edgeTo[otherNode] = minDistanceNode;
          }
        }
      }*/
    }
  }

  class NodeEntry implements Comparable {
    int node;
    int dist;

    NodeEntry(int node, int dist) {
      this.node = node;
      this.dist = dist;
    }

    @Override
    public int compareTo(Object o) {
      return Integer.compare(dist, ((NodeEntry) o).dist);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof NodeEntry)) return false;
      NodeEntry nodeEntry = (NodeEntry) o;
      return node == nodeEntry.node;
    }

    @Override
    public int hashCode() {
      return Objects.hash(node);
    }
  }
}
