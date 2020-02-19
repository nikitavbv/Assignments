package com.nikitavbv.assignments.oop.lab1.validation;

public class NumberInBoundsValidator<T extends Number> implements Validator<T> {

  private final String message;
  private final T minBound;
  private final T maxBound;

  public NumberInBoundsValidator(String message, T minBound, T maxBound) {
    this.message = message;
    this.minBound = minBound;
    this.maxBound = maxBound;
  }

  @Override
  public Verdict validate(Number input) {
    if (input.doubleValue() <= minBound.doubleValue() || input.doubleValue() >= maxBound.doubleValue()) {
      return Verdict.negative(message);
    }

    return Verdict.positive();
  }
}
