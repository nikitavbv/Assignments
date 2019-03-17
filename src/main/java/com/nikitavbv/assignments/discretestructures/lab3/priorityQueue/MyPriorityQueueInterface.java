package com.nikitavbv.assignments.discretestructures.lab3.priorityQueue;

import java.io.IOException;
import java.io.OutputStream;

public interface MyPriorityQueueInterface<T> {

  void push(T element, int priority);
  T pop();
  void print(OutputStream out) throws IOException;
  int getSize();
  void clear();
  boolean isEmpty();

}
