package com.nikitavbv.assignments.oop.lab1;

import java.io.PrintStream;
import java.util.Arrays;

public class SubscriberView {

  public void displaySubscribers(PrintStream out, Subscriber[] subscribers) {
    if (subscribers.length == 0) {
      System.err.println("No subscribers matching search query found");
      return;
    }

    System.out.printf(
      "%20s | %20s | %20s | %10s | %10s | %10s%n",
      "first name",
      "middle name",
      "last name",
      "address",
      "city",
      "intercity"
    );

    Arrays.stream(subscribers).map(Subscriber::toString).forEach(out::println);
  }
}
