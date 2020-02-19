package com.nikitavbv.assignments.oop.lab1.input;

import com.nikitavbv.assignments.oop.lab1.models.MenuOption;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

public class MenuInput {

  private PrintStream outputWriter;
  private PrintStream errorWriter;
  private Scanner scanner;

  public MenuInput(OutputStream outputStream, OutputStream errorStream, InputStream inputStream) {
    this.outputWriter = new PrintStream(outputStream);
    this.errorWriter = new PrintStream(errorStream);
    this.scanner = new Scanner(System.in);
  }

  public MenuOption requestMenuOption() {
    while (true) {
      try {
        outputWriter.print("Please select option: ");

        String command = scanner.nextLine().replaceAll("\n", "").trim();
        Optional<MenuOption> selectedOption = MenuOption.byCommand(command);

        if (selectedOption.isPresent()) {
          return selectedOption.get();
        }

        delayBeforeError();
        errorWriter.printf("Option \"%s\" is not found.%n", command);
      } catch(NumberFormatException e) {
        errorWriter.println("Failed to parse");
      }
    }
  }

  private void delayBeforeError() {
    try {
      Thread.sleep(500);
    } catch(InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
