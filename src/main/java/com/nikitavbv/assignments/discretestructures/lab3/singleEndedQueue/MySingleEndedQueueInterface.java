package com.nikitavbv.assignments.discretestructures.lab3.singleEndedQueue;

import java.io.IOException;
import java.io.OutputStream;

public interface MySingleEndedQueueInterface<T> {

  public void push(T element);
  public T pop();
  public void print(OutputStream out) throws IOException;
  public int getSize();
  public void clear();
  public boolean isEmpty();

}
