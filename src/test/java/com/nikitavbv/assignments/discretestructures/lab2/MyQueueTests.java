package com.nikitavbv.assignments.discretestructures.lab2;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import com.nikitavbv.assignments.discretestructures.lab2.queue.MyQueue;
import com.nikitavbv.assignments.discretestructures.lab2.queue.MyQueue;
import com.nikitavbv.assignments.discretestructures.lab2.queue.QueueFullException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MyQueueTests {

  @Test
  public void testPushAndPop() {
    MyQueue<Integer> queue = new MyQueue<>();

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
  public void testPushAndPopFrontFirst() {
    MyQueue<Integer> queue = new MyQueue<>();

    assertEquals(0, queue.getSize());

    queue.pushFront(2);
    assertEquals(1, queue.getSize());
    queue.pushBack(3);
    assertEquals(2, queue.getSize());
    queue.pushFront(1);
    assertEquals(3, queue.getSize());
    queue.pushBack(4);
    assertEquals(4, queue.getSize());

    assertEquals(Integer.valueOf(4), queue.popBack());
    assertEquals(3, queue.getSize());
    assertEquals(Integer.valueOf(3), queue.popBack());
    assertEquals(2, queue.getSize());
    assertEquals(Integer.valueOf(2), queue.popBack());
    assertEquals(1, queue.getSize());
    assertEquals(Integer.valueOf(1), queue.popFront());
    assertEquals(0, queue.getSize());
  }

  @Test(expected = NoSuchElementException.class)
  public void testPopFrontFromEmptyQueue() {
    MyQueue<Integer> queue = new MyQueue<>();

    assertEquals(0, queue.getSize());
    queue.popFront();
  }

  @Test(expected = NoSuchElementException.class)
  public void testPopBackFromEmptyQueue() {
    MyQueue<Integer> queue = new MyQueue<>();

    assertEquals(0, queue.getSize());
    queue.popBack();
  }

  @Test
  public void testQueuePrint() throws IOException {
    MyQueue<Integer> queue = new MyQueue<>();

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
    MyQueue<Integer> queue = new MyQueue<>();

    assertEquals(0, queue.getSize());
    queue.pushBack(1);
    queue.pushFront(2);
    assertEquals(2, queue.getSize());

    queue.clear();

    assertEquals(0, queue.getSize());
  }

  @Test
  public void testIsEmpty() {
    MyQueue<Integer> queue = new MyQueue<>();

    assertEquals(0, queue.getSize());
    assertTrue(queue.isEmpty());

    queue.pushBack(1);

    assertEquals(1, queue.getSize());
  }

}
