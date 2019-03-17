package com.nikitavbv.assignments.discretestructures.lab3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Test;

public class Lab3Tests {

  @Test
  public void testLab() throws IOException {
    System.setIn(new ByteArrayInputStream("20".getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Lab3.main(new String[]{});

    String[] lines = outContent.toString().split("\n");
    String initial = lines[2];
    String result = lines[4];

    assertEquals(20, initial.split(",").length);
    assertNotEquals(initial, result);

    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test(expected = RuntimeException.class)
  public void testNegativeQueueLength() throws IOException {
    System.setIn(new ByteArrayInputStream("-20".getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Lab3.main(new String[]{});

    System.setIn(System.in);
    System.setOut(System.out);
  }

}
