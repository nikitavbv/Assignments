package com.nikitavbv.assignments.discretestructures.lab3;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import com.nikitavbv.assignments.discretestructures.lab3.priorityQueue.MyPriorityQueue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MyPriorityQueueTest {

  @Test
  public void testPushAndPop() {
    MyPriorityQueue<Integer> queue = new MyPriorityQueue<>();

    assertEquals(0, queue.getSize());

    queue.push(3, 40);
    assertEquals(1, queue.getSize());
    queue.push(1, 0);
    assertEquals(2, queue.getSize());
    queue.push(2, 20);
    assertEquals(3, queue.getSize());
    queue.push(0, -10);
    assertEquals(4, queue.getSize());
    queue.push(4, 80);
    assertEquals(5, queue.getSize());

    assertEquals((Integer) 0, queue.pop());
    assertEquals((Integer) 1, queue.pop());
    assertEquals((Integer) 2, queue.pop());
    assertEquals((Integer) 3, queue.pop());
    assertEquals((Integer) 4, queue.pop());
  }

  @Test(expected = NoSuchElementException.class)
  public void testPopFromEmptyQueue() {
    MyPriorityQueue<Integer> queue = new MyPriorityQueue<>();

    assertEquals(0, queue.getSize());

    queue.pop();
  }

  @Test
  public void testPrint() throws IOException {
    MyPriorityQueue<Integer> queue = new MyPriorityQueue<>();

    assertEquals(0, queue.getSize());

    queue.push(3, 40);
    queue.push(1, 0);
    queue.push(2, 20);
    queue.push(0, -10);
    queue.push(4, 80);
    assertEquals(5, queue.getSize());

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    queue.print(out);

    assertEquals("[0 (-10), 1 (0), 2 (20), 3 (40), 4 (80)]", out.toString());
  }

  @Test
  public void testClear() {
    MyPriorityQueue<Integer> queue = new MyPriorityQueue<>();

    assertEquals(0, queue.getSize());
    assertTrue(queue.isEmpty());

    queue.push(3, 40);
    queue.push(1, 0);
    queue.push(2, 20);
    queue.push(0, -10);
    queue.push(4, 80);
    assertEquals(5, queue.getSize());
    assertFalse(queue.isEmpty());

    queue.clear();

    assertEquals(0, queue.getSize());
    assertTrue(queue.isEmpty());
  }

  @Test
  public void testGetFrontElementPriority() {
    MyPriorityQueue<Integer> queue = new MyPriorityQueue<>();

    assertEquals(0, queue.getSize());
    assertTrue(queue.isEmpty());

    queue.push(3, 40);
    queue.push(1, 0);
    queue.push(2, 20);
    queue.push(0, -10);
    queue.push(4, 80);

    assertEquals(-10, queue.getFrontPriority());
  }

}
