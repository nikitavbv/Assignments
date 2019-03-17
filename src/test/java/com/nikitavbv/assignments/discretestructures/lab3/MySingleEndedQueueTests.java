package com.nikitavbv.assignments.discretestructures.lab3;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import com.nikitavbv.assignments.discretestructures.lab3.singleEndedQueue.MySingleEndedQueue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MySingleEndedQueueTests {

  @Test
  public void testPushAndPop() {
    MySingleEndedQueue<Integer> queue = new MySingleEndedQueue<>();

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

  @Test(expected = NoSuchElementException.class)
  public void testPopFromEmptyQueue() {
    MySingleEndedQueue<Integer> queue = new MySingleEndedQueue<>();

    assertEquals(0, queue.getSize());
    queue.pop();
  }

  @Test
  public void testQueuePrint() throws IOException {
    MySingleEndedQueue<Integer> queue = new MySingleEndedQueue<>();

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
    MySingleEndedQueue<Integer> queue = new MySingleEndedQueue<>();

    assertEquals(0, queue.getSize());
    queue.push(1);
    queue.push(2);
    assertEquals(2, queue.getSize());

    queue.clear();

    assertEquals(0, queue.getSize());
  }

  @Test
  public void testIsEmpty() {
    MySingleEndedQueue<Integer> queue = new MySingleEndedQueue<>();

    assertEquals(0, queue.getSize());
    assertTrue(queue.isEmpty());

    queue.push(1);

    assertEquals(1, queue.getSize());
  }

}
