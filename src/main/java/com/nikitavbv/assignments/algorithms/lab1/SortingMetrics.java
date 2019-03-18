package com.nikitavbv.assignments.algorithms.lab1;

public class SortingMetrics {

  private int totalComparisons;
  private int totalSwaps;

  SortingMetrics(int totalComparisons, int totalSwaps) {
    this.totalComparisons = totalComparisons;
    this.totalSwaps = totalSwaps;
  }

  public int getTotalComparisons() {
    return totalComparisons;
  }

  public int getTotalSwaps() {
    return totalSwaps;
  }
}
