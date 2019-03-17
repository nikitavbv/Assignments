package com.nikitavbv.assignments.discretestructures.lab3.singleEndedQueue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class MySingleEndedFixedQueue<T> implements MySingleEndedQueueInterface<T> {

  private static final int DEFAULT_CAPACITY = 100;

  private T[] arr;
  private int front;
  private int back;
  private int size = 0;

  /** Creates queue with default capacity */
  public MySingleEndedFixedQueue() {
    this(DEFAULT_CAPACITY);
  }

  /**
   * Creates queue with specified capacity.
   *
   * @param capacity maximum size of this queue.
   * @throws IllegalArgumentException if queue capacity is < 0
   */
  public MySingleEndedFixedQueue(int capacity) {
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
  public void push(T element) {
    if (getSize() >= getCapacity()) {
      throw new QueueFullException();
    }

    this.arr[this.back] = element;
    this.back = (back + 1) % getCapacity();
    this.size++;
  }

  @Override
  public T pop() {
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
