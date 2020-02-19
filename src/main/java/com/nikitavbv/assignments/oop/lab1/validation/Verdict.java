package com.nikitavbv.assignments.oop.lab1.validation;

import java.util.Optional;

public class Verdict {

  private final boolean isPositive;
  private final Optional<String> explanation;

  private Verdict(boolean isPositive, Optional<String> explanation) {
    this.isPositive = isPositive;
    this.explanation = explanation;
  }

  public static Verdict positive() {
    return new Verdict(true, Optional.empty());
  }

  public static Verdict negative(String explanation) {
    return new Verdict(false, Optional.of(explanation));
  }

  public boolean isPositive() {
    return isPositive;
  }

  public boolean isNegative() {
    return !isPositive();
  }

  public Optional<String> explanation() {
    return explanation;
  }
}
