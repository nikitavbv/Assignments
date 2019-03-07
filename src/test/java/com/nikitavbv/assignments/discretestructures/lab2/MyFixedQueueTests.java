package com.nikitavbv.assignments.discretestructures.lab2;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import com.nikitavbv.assignments.discretestructures.lab2.queue.MyFixedQueue;
import com.nikitavbv.assignments.discretestructures.lab2.queue.QueueFullException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MyFixedQueueTests {

  @Test
  public void testPushAndPop() {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>(10);

    assertEquals(10, queue.getCapacity());
    assertEquals(0, queue.getSize());

    queue.pushBack(3);
    assertEquals(1, queue.getSize());
    queue.pushFront(2);
    assertEquals(2, queue.getSize());
    queue.pushBack(4);
    assertEquals(3, queue.getSize());
    queue.pushFront(1);
    assertEquals(4, queue.getSize());

    assertEquals(Integer.valueOf(1), queue.popFront());
    assertEquals(3, queue.getSize());
    assertEquals(Integer.valueOf(2), queue.popFront());
    assertEquals(2, queue.getSize());
    assertEquals(Integer.valueOf(3), queue.popFront());
    assertEquals(1, queue.getSize());
    assertEquals(Integer.valueOf(4), queue.popBack());
    assertEquals(0, queue.getSize());
  }

  @Test
  public void testDefaultCapacity() {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>();
    assertTrue(queue.getCapacity() > 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCapacity() {
    new MyFixedQueue<>(-10);
  }

  @Test(expected = QueueFullException.class)
  public void testPushBackToFullQueue() {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>(3);

    assertEquals(3, queue.getCapacity());
    assertEquals(0, queue.getSize());

    queue.pushBack(1);
    assertEquals(1, queue.getSize());
    queue.pushBack(2);
    assertEquals(2, queue.getSize());
    queue.pushBack(3);
    assertEquals(3, queue.getSize());

    queue.pushBack(4);
  }

  @Test(expected = QueueFullException.class)
  public void testPushFrontToFullQueue() {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>(3);

    assertEquals(3, queue.getCapacity());
    assertEquals(0, queue.getSize());

    queue.pushFront(1);
    assertEquals(1, queue.getSize());
    queue.pushFront(2);
    assertEquals(2, queue.getSize());
    queue.pushFront(3);
    assertEquals(3, queue.getSize());

    queue.pushFront(4);
  }

  @Test(expected = NoSuchElementException.class)
  public void testPopFrontFromEmptyQueue() {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>();

    assertEquals(0, queue.getSize());
    queue.popFront();
  }

  @Test(expected = NoSuchElementException.class)
  public void testPopBackFromEmptyQueue() {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>();

    assertEquals(0, queue.getSize());
    queue.popBack();
  }

  @Test
  public void testQueuePrint() throws IOException {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>(10);

    queue.pushBack(3);
    queue.pushFront(2);
    queue.pushBack(4);
    queue.pushFront(1);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    queue.print(out);
    assertEquals("[1,2,3,4]\n", out.toString());
  }

  @Test
  public void testClear() {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>();

    assertTrue(queue.getCapacity() > 0);

    assertEquals(0, queue.getSize());
    queue.pushBack(1);
    queue.pushFront(2);
    assertEquals(2, queue.getSize());

    queue.clear();

    assertEquals(0, queue.getSize());
    assertTrue(queue.getCapacity() > 0);
  }

  @Test
  public void testIsEmpty() {
    MyFixedQueue<Integer> queue = new MyFixedQueue<>();

    assertEquals(0, queue.getSize());
    assertTrue(queue.isEmpty());

    queue.pushBack(1);

    assertEquals(1, queue.getSize());
  }

}
