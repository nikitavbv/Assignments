package com.nikitavbv.assignments.algorithms.lab5;

import static com.nikitavbv.assignments.algorithms.lab5.Utils.repeat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

class BeeAlgorithm {

  private static final int DEFAULT_SIMULTANEOUS_SITES = 1;
  private static final int DEFAULT_MIN_QUEUE_SIZE = 1;
  private static final int DEFAULT_MAX_QUEUE_SIZE = 1;

  private final Graph graph;
  private final GraphColorer colorer;
  private final Random random;

  private final int workerBees;
  private final int scoutBees;
  private int simultaneousSites = DEFAULT_SIMULTANEOUS_SITES;
  private int minQueueSize = DEFAULT_MIN_QUEUE_SIZE; // should be >= simultaneousSites
  private int maxQueueSize = DEFAULT_MAX_QUEUE_SIZE; // should be >= minQueueSize

  private ColoredGraph best;
  private Set<ColoredGraph> localExtremums = new HashSet<>();
  private PriorityQueue<ColoredGraph> sitesQueue = new PriorityQueue<>();

  BeeAlgorithm(Graph graph, GraphColorer colorer, int totalBees, int scoutBees, Random random) {
    this.graph = graph;
    this.colorer = colorer;
    this.workerBees = totalBees - scoutBees;
    this.scoutBees = scoutBees;
    this.random = random;
  }

  void runIteration() {
    repeat(scoutBees, () -> enqueueSite(globalSolutionSearch()));

    List<ColoredGraph> sites = new ArrayList<>();
    for (int i = 0; i < simultaneousSites; i++) {
      sites.add(sitesQueue.poll());
    }
    int scoreSum = getScoreSum(sites);
    List<Integer> numBees = sites.stream()
            .mapToInt(s -> (int) Math.round((double) (s.getScore() * workerBees) / scoreSum))
            .boxed()
            .collect(Collectors.toList());

    while (sites.size() > 0) {
      ColoredGraph site = sites.remove(0);
      int beesAtSite = numBees.remove(0);

      for (int i = 0; i < beesAtSite; i++) {
        runLocalSearch(site);
      }

      enqueueSite(site);
    }
  }

  private void runLocalSearch(ColoredGraph site) {
    Optional<ColoredGraph> otherSite = site.generateFromNeighbourhood(random);
    if (!otherSite.isPresent()) {
      return;
    }
    enqueueSite(otherSite.get());
  }

  private int getScoreSum(List<ColoredGraph> sites) {
    int sum = 0;
    for (ColoredGraph site : sites) {
      sum += site.getScore();
    }
    return sum;
  }

  private ColoredGraph globalSolutionSearch() {
    return colorer.color(graph);
  }

  private void enqueueSite(ColoredGraph solution) {
    if (best == null || solution.getScore() < best.getScore()) {
      best = solution;
    }

    sitesQueue.add(solution);
    if (sitesQueue.size() > maxQueueSize) {
      cleanQueue();
    }
  }

  private void cleanQueue() {
    PriorityQueue<ColoredGraph> newQueue = new PriorityQueue<>();
    while (newQueue.size() < minQueueSize && sitesQueue.size() > 0) {
      newQueue.add(sitesQueue.poll());
    }
    sitesQueue = newQueue;
  }

  ColoredGraph best() {
    return best;
  }
}
