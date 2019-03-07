package com.nikitavbv.assignments.discretestructures.lab2.queue;

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
 * @see MyQueueInterface
 */
public class MyQueue<T> implements MyQueueInterface<T> {

  private class MyQueueElement<T> {
    private T element;
    private MyQueueElement<T> prev;
    private MyQueueElement<T> next;

    private MyQueueElement(T element, MyQueueElement<T> prev, MyQueueElement<T> next) {
      this.element = element;
      this.prev = prev;
      this.next = next;
    }
  }

  private MyQueueElement<T> back = null;
  private MyQueueElement<T> front = null;
  private int size = 0;

  @Override
  public void pushBack(T element) {
    MyQueueElement<T> node = new MyQueueElement<>(element, null, null);

    if (back == null) {
      back = node;
      front = node;
    } else {
      node.prev = back;
      back.next = node;
      back = node;
    }

    size++;
  }

  @Override
  public void pushFront(T element) {
    MyQueueElement<T> node = new MyQueueElement<>(element, null, null);

    if (front == null) {
      front = node;
      back = node;
    } else {
      node.next = front;
      front.prev = node;
      front = node;
    }

    size++;
  }

  @Override
  public T popBack() {
    if (back == null) {
      throw new NoSuchElementException();
    }

    T result = back.element;
    size--;

    if (back.prev == null) {
      back = null;
      front = null;
      return result;
    }

    back = back.prev;
    back.next = null;
    return result;
  }

  @Override
  public T popFront() {
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
    front.prev = null;
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
