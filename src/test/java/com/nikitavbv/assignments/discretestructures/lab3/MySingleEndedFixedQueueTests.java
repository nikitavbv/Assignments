package com.nikitavbv.assignments.discretestructures.lab3;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import com.nikitavbv.assignments.discretestructures.lab3.singleEndedQueue.MySingleEndedFixedQueue;
import com.nikitavbv.assignments.discretestructures.lab3.singleEndedQueue.QueueFullException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MySingleEndedFixedQueueTests {

  @Test
  public void testPushAndPop() {
    MySingleEndedFixedQueue<Integer> queue = new MySingleEndedFixedQueue<>(10);

    assertEquals(10, queue.getCapacity());
    assertEquals(0, queue.getSize());

    queue.push(1);
    assertEquals(1, queue.getSize());
    queue.push(2);
    assertEquals(2, queue.getSize());
    queue.push(3);
    assertEquals(3, queue.getSize());
    queue.push(4);
    assertEquals(4, queue.getSize());

    assertEquals(Integer.valueOf(1), queue.pop());
    assertEquals(3, queue.getSize());
    assertEquals(Integer.valueOf(2), queue.pop());
    assertEquals(2, queue.getSize());
    assertEquals(Integer.valueOf(3), queue.pop());
    assertEquals(1, queue.getSize());
    assertEquals(Integer.valueOf(4), queue.pop());
    assertEquals(0, queue.getSize());
  }

  @Test
  public void testDefaultCapacity() {
    MySingleEndedFixedQueue<Integer> queue = new MySingleEndedFixedQueue<>();
    assertTrue(queue.getCapacity() > 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCapacity() {
    new MySingleEndedFixedQueue<>(-10);
  }

  @Test(expected = QueueFullException.class)
  public void testPushToFullQueue() {
    MySingleEndedFixedQueue<Integer> queue = new MySingleEndedFixedQueue<>(3);

    assertEquals(3, queue.getCapacity());
    assertEquals(0, queue.getSize());

    queue.push(1);
    assertEquals(1, queue.getSize());
    queue.push(2);
    assertEquals(2, queue.getSize());
    queue.push(3);
    assertEquals(3, queue.getSize());

    queue.push(4);
  }

  @Test(expected = NoSuchElementException.class)
  public void testPopFromEmptyQueue() {
    MySingleEndedFixedQueue<Integer> queue = new MySingleEndedFixedQueue<>();

    assertEquals(0, queue.getSize());
    queue.pop();
  }

  @Test
  public void testQueuePrint() throws IOException {
    MySingleEndedFixedQueue<Integer> queue = new MySingleEndedFixedQueue<>(10);

    queue.push(1);
    queue.push(2);
    queue.push(3);
    queue.push(4);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    queue.print(out);
    assertEquals("[1,2,3,4]\n", out.toString());
  }

  @Test
  public void testClear() {
    MySingleEndedFixedQueue<Integer> queue = new MySingleEndedFixedQueue<>();

    assertTrue(queue.getCapacity() > 0);

    assertEquals(0, queue.getSize());
    queue.push(1);
    queue.push(2);
    assertEquals(2, queue.getSize());

    queue.clear();

    assertEquals(0, queue.getSize());
    assertTrue(queue.getCapacity() > 0);
  }

  @Test
  public void testIsEmpty() {
    MySingleEndedFixedQueue<Integer> queue = new MySingleEndedFixedQueue<>();

    assertEquals(0, queue.getSize());
    assertTrue(queue.isEmpty());

    queue.push(1);

    assertEquals(1, queue.getSize());
  }

}
