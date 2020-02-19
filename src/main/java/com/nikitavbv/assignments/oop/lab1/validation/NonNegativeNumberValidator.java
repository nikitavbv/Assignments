package com.nikitavbv.assignments.oop.lab1.validation;

public class NonNegativeNumberValidator<T extends Number> implements Validator<T> {

  private final String message;

  public NonNegativeNumberValidator(String message) {
    this.message = message;
  }

  @Override
  public Verdict validate(Number input) {
    if (input.doubleValue() <= 0) {
      return Verdict.negative(message);
    }

    return Verdict.positive();
  }
}
