package com.nikitavbv.assignments.oop.lab1;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Lab1 {

  private static final RandomSubscriberGenerator subscriberGenerator = new RandomSubscriberGenerator(new Random());
  private static final Subscriber[] SUBSCRIBERS = IntStream.range(0, 25)
          .mapToObj(v -> subscriberGenerator.nextSubscriber())
          .toArray(Subscriber[]::new);

  private static final Scanner input = new Scanner(System.in);
  private static final SubscriberView view = new SubscriberView();

  public static void main(String[] args) {
    System.out.print("Specify city call time threshold: ");
    view.displaySubscribers(System.out, subscribersWithCityTimeThreshold(SUBSCRIBERS, input.nextInt()));

    System.out.println("Subscribers, who used intercity calls:");
    view.displaySubscribers(System.out, subscribersWithIntercityCalls(SUBSCRIBERS));
  }

  private static Subscriber[] subscribersWithCityTimeThreshold(Subscriber[] subscribers, int threshold) {
    return Arrays.stream(subscribers).filter(s -> s.getCityCallTime() >= threshold).toArray(Subscriber[]::new);
  }

  private static Subscriber[] subscribersWithIntercityCalls(Subscriber[] subscribers) {
    return Arrays.stream(subscribers).filter(Subscriber::performedIntercityCalls).toArray(Subscriber[]::new);
  }
}
