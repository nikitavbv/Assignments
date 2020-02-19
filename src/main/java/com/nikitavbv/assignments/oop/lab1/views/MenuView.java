package com.nikitavbv.assignments.oop.lab1.views;

import com.nikitavbv.assignments.oop.lab1.models.MenuOption;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MenuView {

  private PrintStream outputWriter;
  private PrintStream errorStream;

  public MenuView(OutputStream outputStream, OutputStream errorStream) {
      this.outputWriter = new PrintStream(outputStream);
      this.errorStream = new PrintStream(errorStream);
  }

  public void showMenu() {
    outputWriter.println(
            "Menu\n" +
            "Possible actions:\n" +
             possibleActionsAsString()
    );
  }

  public String possibleActionsAsString() {
    return Arrays.stream(MenuOption.values()).map(MenuOption::description)
            .collect(Collectors.joining(System.lineSeparator()));
  }

  public void showWelcome() {
    System.out.println(
            "  _          _       _ \n" +
                    " | |    __ _| |__   / |\n" +
                    " | |   / _` | '_ \\  | |\n" +
                    " | |__| (_| | |_) | | |\n" +
                    " |_____\\__,_|_.__/  |_|\n" +
                    "                       "
    );
  }
}
