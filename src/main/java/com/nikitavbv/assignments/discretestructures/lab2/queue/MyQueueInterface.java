package com.nikitavbv.assignments.discretestructures.lab2.queue;

import java.io.IOException;
import java.io.OutputStream;

public interface MyQueueInterface<T> {

  /**
   * Inserts the specified element at the back of the queue.
   *
   * @param element element to be inserted
   */
  public void pushBack(T element);

  /**
   * Inserts the specified element at the front of the queue.
   *
   * @param element element to be inserted
   */
  public void pushFront(T element);

  /**
   * Removes and returns element from the back of the queue.
   *
   * @return element from the back of the queue.
   */
  public T popBack();

  /**
   * Removes and returns element from the front of the queue.
   *
   * @return element from the front of the queue.
   */
  public T popFront();

  /**
   * Print queue contents.
   *
   * @param out OutputStream to print contents to
   * @throws IOException if failed to print
   */
  public void print(OutputStream out) throws IOException;

  /** Returns the number of elements in this queue. */
  public int getSize();

  /**
   * Removes all of the elements from this queue.
   * The queue will be empty after this call returns.
   */
  public void clear();

  /** Returns <tt>true</tt> if queue contains no elements. */
  public boolean isEmpty();

}
