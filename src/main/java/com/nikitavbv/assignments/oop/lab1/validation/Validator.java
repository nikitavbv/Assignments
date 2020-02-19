package com.nikitavbv.assignments.oop.lab1.validation;

public interface Validator<T> {

  public Verdict validate(T input);
}
