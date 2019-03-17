package com.nikitavbv.assignments.discretestructures.lab3;

import com.nikitavbv.assignments.discretestructures.lab3.priorityQueue.MyPriorityQueue;
import com.nikitavbv.assignments.discretestructures.lab3.priorityQueue.MyPriorityQueueInterface;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Lab3 {

  private static final int QUEUE_ELEMENT_BOUND = 100;
  private static final int QUEUE_PRIORITY_BOUND = 7;

  public static void main(String[] args) throws IOException {
    int queueLength = requestQueueLength();
    if (queueLength < 0) {
      throw new RuntimeException("Queue length should be >= 0");
    }

    MyPriorityQueue<Integer> queue = new MyPriorityQueue<>();
    fillQueueWithRandomElements(queue, queueLength);
    System.out.println("Initial queue:");
    queue.print(System.out);
    System.out.println();

    MyPriorityQueue<Integer> result = new MyPriorityQueue<>();
    int i = 0;
    while (!queue.isEmpty()) {
      int priority = queue.getFrontPriority();
      if (i % 2 == 1) {
        priority--;
      }
      result.push(queue.pop(), priority);
      i++;
    }
    System.out.println("Result:");
    result.print(System.out);
    System.out.println();
  }

  private static int requestQueueLength() {
    Scanner in = new Scanner(System.in);
    System.out.println("Queue size: ");
    int queueSize = in.nextInt();
    in.close();
    return queueSize;
  }

  private static void fillQueueWithRandomElements(MyPriorityQueueInterface<Integer> queue, int totalElements) {
    Random random = new Random();
    for (int i = 0; i < totalElements; i++) {
      queue.push(random.nextInt(QUEUE_ELEMENT_BOUND), random.nextInt(QUEUE_PRIORITY_BOUND));
    }
  }

}
