package com.nikitavbv.assignments.discretestructures.lab2;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class Lab2FixedTests {

  @Test
  public void testLab() throws IOException {
    System.setIn(new ByteArrayInputStream("20".getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Lab2Fixed.main(new String[]{});

    String[] lines = outContent.toString().split("\n");
    List<String> initialArr = Arrays.asList(lines[2].substring(1, lines[2].length()-1).split(","));
    List<String> resultingArr = Arrays.asList(lines[5].substring(1, lines[5].length()-1).split(","));

    assertEquals(20, initialArr.size());
    assertTrue(resultingArr.size() <= initialArr.size());

    String averageToRemove = lines[3].substring(lines[3].indexOf("Average: ") + 9);
    assertFalse(resultingArr.contains(averageToRemove));

    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test(expected = RuntimeException.class)
  public void testNegativeQueueSize() throws IOException {
    System.setIn(new ByteArrayInputStream("-20".getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Lab2Fixed.main(new String[]{});
  }

}
