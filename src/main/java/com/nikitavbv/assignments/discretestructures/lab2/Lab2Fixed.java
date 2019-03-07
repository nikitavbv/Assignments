package com.nikitavbv.assignments.discretestructures.lab2;

import com.nikitavbv.assignments.discretestructures.lab2.queue.MyFixedQueue;
import com.nikitavbv.assignments.discretestructures.lab2.queue.MyQueueInterface;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Lab2Fixed {

  private static final int RANDOM_NUMBER_BOUND = 10;

  public static void main(String[] args) throws IOException {
    int queueSize = requestQueueSize();
    if (queueSize < 0) {
      throw new RuntimeException("Queue size should be >= 0");
    }

    MyFixedQueue<Integer> queue = new MyFixedQueue<>(queueSize);
    fillQueueWithRandomElements(queue, queueSize);

    System.out.println("Queue:");
    queue.print(System.out);

    int average = findQueueAverage(queue);
    System.out.printf("Average: %d%n", average);

    removeQueueElements(queue, average);

    System.out.println("Result:");
    queue.print(System.out);
  }

  private static int requestQueueSize() {
    Scanner in = new Scanner(System.in);
    System.out.println("Queue size: ");
    int queueSize = in.nextInt();
    in.close();
    return queueSize;
  }

  private static void fillQueueWithRandomElements(MyQueueInterface<Integer> queue, int totalElements) {
    new Random()
            .ints(totalElements)
            .map(n -> n % RANDOM_NUMBER_BOUND)
            .forEach(queue::pushBack);
  }

  private static int findQueueAverage(MyQueueInterface<Integer> queue) {
    int sum = 0;
    for (int i = 0; i < queue.getSize(); i++) {
      int n = queue.popFront();
      sum += n;
      queue.pushBack(n);
    }
    return sum / queue.getSize();
  }

  private static void removeQueueElements(MyQueueInterface<Integer> queue, int elementToRemove) {
    final int queueSize = queue.getSize();
    for (int i = 0; i < queueSize; i++) {
      int n = queue.popFront();
      if (n == elementToRemove) {
        continue;
      }
      queue.pushBack(n);
    }
  }

}
