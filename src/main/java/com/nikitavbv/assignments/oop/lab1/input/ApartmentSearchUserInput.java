package com.nikitavbv.assignments.oop.lab1.input;

import com.nikitavbv.assignments.oop.lab1.validation.NonNegativeNumberValidator;
import com.nikitavbv.assignments.oop.lab1.validation.NumberInBoundsValidator;
import com.nikitavbv.assignments.oop.lab1.validation.Validator;
import com.nikitavbv.assignments.oop.lab1.validation.Verdict;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class ApartmentSearchUserInput {
  private static final long INVALID_INPUT_DELAY = 100; // ms

  private static final Validator<Integer> NUMBER_OF_ROOMS_VALIDATIOR =
          new NonNegativeNumberValidator<>("Number of rooms should be a positive number");
  private static final Validator<Double> AREA_VALIDATOR =
          new NonNegativeNumberValidator<>("Area should be a positive number");
  private static final Validator<Integer> FLOOR_VALIDATOR =
          new NumberInBoundsValidator<>("Floor should be in (0..100)", 0, 100);

  private final PrintStream outputWriter;
  private final PrintStream errorWriter;
  private final Scanner scanner;

  public ApartmentSearchUserInput(OutputStream outputStream, OutputStream errorStream, InputStream in) {
    outputWriter = new PrintStream(outputStream);
    errorWriter = new PrintStream(errorStream);
    scanner = new Scanner(in);
  }

  public int requestNumberOfRooms() {
    return requestUserInput(
            "Please enter filter criteria (number of rooms): ",
            NUMBER_OF_ROOMS_VALIDATIOR,
            Integer::parseInt
    );
  }

  public double requestArea() {
    return requestUserInput(
            "Please enter filter criteria (area): ",
            AREA_VALIDATOR,
            Double::parseDouble
    );
  }

  public int requestFloor() {
    return requestUserInput(
            "Please enter filter criteria (floor): ",
            FLOOR_VALIDATOR,
            Integer::parseInt
    );
  }

  private <T> T requestUserInput(String prompt, Validator<T> validator, Function<String, T> inputParser) {
    while (true) {
      outputWriter.print(prompt);

      try {
        T value = inputParser.apply(scanner.nextLine());

        Verdict verdict = validator.validate(value);
        if (verdict.isNegative()) {
          verdict.explanation().ifPresent(errorWriter::println);
          delayAfterInvalidInput();
          continue;
        }

        return value;
      } catch (NumberFormatException | InputMismatchException e) {
        errorWriter.println("Parse error");
        delayAfterInvalidInput();
      }
    }
  }

  private void delayAfterInvalidInput() {
    try {
      Thread.sleep(INVALID_INPUT_DELAY);
    } catch (InterruptedException ex) {
      // Ignore :/
    }
  }
}
