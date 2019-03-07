package com.nikitavbv.assignments.discretestructures.lab2.queue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Queue implementation with fixed capacity.
 *
 * @param <T> the type of elements in this queue
 *
 * @author Nikita Volobuev
 * @see MyQueueInterface
 */
public class MyFixedQueue<T> implements MyQueueInterface<T> {

  private static final int DEFAULT_CAPACITY = 100;

  private T[] arr;
  private int front;
  private int back;
  private int size = 0;

  /** Creates queue with default capacity */
  public MyFixedQueue() {
    this(DEFAULT_CAPACITY);
  }

  /**
   * Creates queue with specified capacity.
   *
   * @param capacity maximum size of this queue.
   * @throws IllegalArgumentException if queue capacity is < 0
   */
  public MyFixedQueue(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Capacity should be >= 0");
    }

    // noinspection unchecked
    this.arr = (T[]) new Object[capacity];
    front = capacity - 1;
    back = 0;
    back = capacity - 1;
  }

  @Override
  public void pushBack(T element) {
    if (getSize() >= getCapacity()) {
      throw new QueueFullException();
    }

    this.arr[this.back] = element;
    this.back = (back + 1) % getCapacity();
    this.size++;
  }

  @Override
  public void pushFront(T element) {
    if (getSize() >= getCapacity()) {
      throw new QueueFullException();
    }

    this.front = (this.front + getCapacity() - 1) % getCapacity();
    this.arr[this.front] = element;
    this.size++;
  }

  @Override
  public T popBack() {
    if (getSize() == 0) {
      throw new NoSuchElementException();
    }

    back = (back + getCapacity() - 1) % getCapacity();
    size--;
    return arr[back];
  }

  @Override
  public T popFront() {
    if (getSize() == 0) {
      throw new NoSuchElementException();
    }

    final T result = arr[front];
    front = (front + 1) % getCapacity();
    size--;
    return result;
  }

  @Override
  public void print(OutputStream out) throws IOException {
    out.write("[".getBytes());
    for (int i = front; i < front + getSize(); i++) {
      if (i != front) {
        out.write(",".getBytes());
      }
      out.write(arr[i % getCapacity()].toString().getBytes());
    }
    out.write("]\n".getBytes());
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public void clear() {
    this.size = 0;
    this.front = getCapacity() - 1;
    this.back = getCapacity() - 1;
    Arrays.fill(arr, null);
  }

  @Override
  public boolean isEmpty() {
    return getSize() == 0;
  }

  /** Returns maximum possible size of this queue */
  public int getCapacity() {
    return this.arr.length;
  }

}
