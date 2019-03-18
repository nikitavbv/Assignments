package com.nikitavbv.assignments.algorithms.lab1;

import static junit.framework.TestCase.assertTrue;

import java.util.Arrays;
import org.junit.Test;

public class ShellSortTest {

  @Test
  public void testSedgewickSequence() {
    System.out.println(ShellSort.getSedgewickSequenceForBound(13000).toArray().length);
    assertTrue(Arrays.equals(ShellSort.getSedgewickSequenceForBound(1000).toArray(), new Object[]{209, 109, 41, 19, 5, 1}));
  }

}
