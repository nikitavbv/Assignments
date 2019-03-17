package com.nikitavbv.assignments.discretestructures.lab3.singleEndedQueue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

/**
 * Queue implementation based on linked list.
 * Thus capacity of this queue is not limited.
 *
 * @param <T> the type of elements in this queue
 *
 * @author Nikita Volobuev
 * @see MySingleEndedQueueInterface
 */
public class MySingleEndedQueue<T> implements MySingleEndedQueueInterface<T> {

  private class MyQueueElement<T> {
    private T element;
    private MyQueueElement<T> next;

    private MyQueueElement(T element, MyQueueElement<T> next) {
      this.element = element;
      this.next = next;
    }
  }

  private MyQueueElement<T> back = null;
  private MyQueueElement<T> front = null;
  private int size = 0;

  @Override
  public void push(T element) {
    MyQueueElement<T> node = new MyQueueElement<>(element, null);

    if (back == null) {
      back = node;
      front = node;
    } else {
      back.next = node;
      back = node;
    }

    size++;
  }

  @Override
  public T pop() {
    if (front == null) {
      throw new NoSuchElementException();
    }

    T result = front.element;
    size--;

    if (front.next == null) {
      front = null;
      back = null;
      return result;
    }

    front = front.next;
    return result;
  }

  @Override
  public void print(OutputStream out) throws IOException {
    MyQueueElement<T> cursor = front;
    boolean firstIteration = true;
    out.write("[".getBytes());
    while (cursor != null) {
      if (!firstIteration) {
        out.write(",".getBytes());
      }
      out.write(cursor.element.toString().getBytes());
      cursor = cursor.next;
      firstIteration = false;
    }
    out.write("]\n".getBytes());
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public void clear() {
    size = 0;
    back = null;
    front = null;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }
}
