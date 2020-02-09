package com.nikitavbv.assignments.discretestructures.lab8.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {

  private int totalNodes;
  private List<GraphEdge> edges;

  public Graph(int totalNodes, List<GraphEdge> edges) {
    this.totalNodes = totalNodes;
    this.edges = edges;
  }

  public Stream<Integer> getNeighbors(int node) {
    return edges.stream()
            .filter(edge -> edge.from() == node || edge.to() == node)
            .map(edge -> edge.to() == node ? edge.from() : edge.to());
  }

  public Set<Integer> vertexes() {
    Set<Integer> vertexes = new HashSet<>();
    edges.forEach(edge -> {
      vertexes.add(edge.from());
      vertexes.add(edge.to());
    });
    return vertexes;
  }

  public Graph makeCopy() {
    return new Graph(totalNodes, edges.stream().map(edge -> new GraphEdge(edge.from(), edge.to(), edge.capacity()))
                    .collect(Collectors.toList()));
  }

  public void merge(int what, int where) {
    edges = edges.stream()
            .filter(edge -> !((edge.from() == what && edge.to() == where) || (edge.to() == what && edge.from() == where)))
            .map(edge -> {
      if (edge.from() == what) {
        return new GraphEdge(where, edge.to(), edge.capacity());
      } else if (edge.to() == what) {
        return new GraphEdge(edge.from(), where, edge.capacity());
      } else {
        return edge;
      }
    }).collect(Collectors.toList());
  }

  public int totalNodes() {
    return totalNodes;
  }

  public Stream<GraphEdge> streamEdges() {
    return edges.stream();
  }

  public Stream<GraphEdge> streamEdges(int node) {
    return edges.stream().filter(edge -> edge.from() == node || edge.to() == node);
  }

  public Optional<GraphEdge> edgeBetween(int from, int to) {
    return edges.stream()
            .filter(edge -> (edge.from() == from && edge.to() == to) || (edge.from() == to && edge.to() == from))
            .findFirst();
  }
}
