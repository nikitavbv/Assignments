package com.nikitavbv.assignments.algorithms.lab1;

import java.util.ArrayList;
import java.util.List;

public class ShellSort {

  public static SortingMetrics sort(int[] arr) {
    int totalComparisons = 0;
    int totalSwaps = 0;

    List<Integer> seq = getSedgewickSequenceForBound(arr.length);
    for (Integer gap : seq) {
      for (int i = gap; i < arr.length; i++) {
        // Do insertion sort for sub array
        int element = arr[i];
        int j = i; // position to insert to
        while (j - gap >= 0) {
          totalComparisons++;
          if (arr[j - gap] <= element) {
            break;
          }
          arr[j] = arr[j - gap];
          totalSwaps++;
          j -= gap;
        }
        arr[j] = element;
      }
    }

    return new SortingMetrics(totalComparisons, totalSwaps);
  }

  static List<Integer> getSedgewickSequenceForBound(int bound) {
    if (bound <= 0) {
      throw new IllegalArgumentException("Bound should be > 0");
    }

    List<Integer> sequence = new ArrayList<>();
    int d = 0;
    int i = 0;
    while (d * 3 <= bound) {
      if (i != 0) {
        sequence.add(0, d);
      }

      if (i % 2 == 0) {
        d = 9 * (int) (Math.pow(2, i) - Math.pow(2, i/2)) + 1;
      } else {
        d = 8 * (int) Math.pow(2, i) - 6 * (int) Math.pow(2, (i+1)/2) + 1;
      }
      i++;
    }

    return sequence;
  }

}
