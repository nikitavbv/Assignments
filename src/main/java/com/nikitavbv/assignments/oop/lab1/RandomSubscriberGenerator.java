package com.nikitavbv.assignments.oop.lab1;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomSubscriberGenerator {

  private static final List<String> FIRST_NAMES = Arrays.asList(
      "Nikita", "Kyrill", "Valeriy", "Vasiliy", "Denis", "Mark", "Albert", "Yaroslav", "Alexander", "Igor", "Timur"
  );

  private static final List<String> MIDDLE_NAMES = Arrays.asList(
      "Alexandrovich", "Serheevysh", "Svyatoslavovich", "Valeriyevich", "Fedorovich", "Danilovich", "Ruslanovich",
      "Ivanovich", "Kyrillovich", "Vasilievich", "Denisovich"
  );

  private static final List<String> LAST_NAMES = Arrays.asList(
      "Safonov", "Grishin", "Novikov", "Mikheev", "Ustinov", "Avdeev", "Samoylov", "Lobanov", "Bespalov", "Kuznetsov",
      "Silin"
  );

  private static final List<String> CITIES = Arrays.asList(
      "Kyiv", "Lviv", "Kharkiv"
  );

  private static final double ODDS_OF_HAVING_ZERO_MINUTES = 0.2;
  private static final int CALL_TIME_UPPER_BOUND = 500;

  private final Random random;

  public RandomSubscriberGenerator(Random random) {
      this.random = random;
  }

  public Subscriber nextSubscriber() {
    return new Subscriber(
            randomFirstName(),
            randomMiddleName(),
            randomLastName(),
            randomCity(),
            randomCallTime(),
            randomCallTime()
    );
  }

  private String randomFirstName() {
    return FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size()));
  }

  private String randomMiddleName() {
    return MIDDLE_NAMES.get(random.nextInt(MIDDLE_NAMES.size()));
  }

  private String randomLastName() {
    return LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));
  }

  private String randomCity() {
    return CITIES.get(random.nextInt(CITIES.size()));
  }

  private int randomCallTime() {
    if (random.nextDouble() < ODDS_OF_HAVING_ZERO_MINUTES) {
      return 0;
    }

    return random.nextInt(CALL_TIME_UPPER_BOUND);
  }
}
