package com.nikitavbv.assignments.discretestructures.lab3.priorityQueue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

/**
 * PriorityQueue implementation based on list.
 * Thus capacity of this queue is not limited.
 *
 * @param <T> the type of elements in this queue
 * @author Nikita Volobuev
 * @see MyPriorityQueueInterface
 */
public class MyPriorityQueue<T> implements MyPriorityQueueInterface<T> {

  private class MyPriorityQueueNode<T> {
    T element;
    int priority;

    MyPriorityQueueNode<T> prev;

    MyPriorityQueueNode(T element, int priority, MyPriorityQueueNode<T> prev) {
      this.element = element;
      this.priority = priority;
      this.prev = prev;
    }
  }

  private MyPriorityQueueNode<T> front;

  /**
   * Inserts specified element with specified priority to the queue.
   *
   * @param element element to be inserted
   * @param priority priority of this element, determines the position element
   *                 gets inserted to
   */
  @Override
  public void push(T element, int priority) {
    MyPriorityQueueNode<T> node = new MyPriorityQueueNode<>(element, priority, null);

    if (front == null) {
      front = node;
      return;
    }

    MyPriorityQueueNode<T> cursor = this.front;
    MyPriorityQueueNode<T> prev = null;
    while (cursor != null && cursor.priority <= node.priority) {
      prev = cursor;
      cursor = cursor.prev;
    }

    if (cursor != null) {
      node.prev = cursor;
      if (cursor == front) {
        front = node;
      }
    } else {
      node.prev = prev.prev;
    }

    if (prev != null) {
      prev.prev = node;
    }
  }

  /**
   * Removes and returns element from this queue
   *
   * @return element with highest priority
   */
  @Override
  public T pop() {
    if (front == null) {
      throw new NoSuchElementException();
    }

    MyPriorityQueueNode<T> node = this.front;
    this.front = node.prev;
    return node.element;
  }

  /**
   * Returns priority of the element with highest priority.
   *
   * @return highest priority
   */
  public int getFrontPriority() {
    return front.priority;
  }

  /**
   * Print queue contents.
   *
   * @param out OutputStream to print contents to
   * @throws IOException if failed to print
   */
  @Override
  public void print(OutputStream out) throws IOException {
    out.write("[".getBytes());
    MyPriorityQueueNode<T> cursor = front;
    boolean firstElement = true;
    while (cursor != null) {
      if (!firstElement) {
        out.write(", ".getBytes());
      }

      out.write(cursor.element.toString().getBytes());
      out.write((" (" + cursor.priority + ")").getBytes());

      firstElement = false;
      cursor = cursor.prev;
    }
    out.write("]".getBytes());
  }

  /** Returns the number of elements in this queue. */
  @Override
  public int getSize() {
    MyPriorityQueueNode<T> cursor = front;
    int size = 0;
    while (cursor != null) {
      size++;
      cursor = cursor.prev;
    }
    return size;
  }

  /** Removes all the elements from this queue. */
  @Override
  public void clear() {
    front = null;
  }

  /** Returns <tt>true</tt> if queue contains no elements. */
  @Override
  public boolean isEmpty() {
    return getSize() == 0;
  }
}
