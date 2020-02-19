package com.nikitavbv.assignments.oop.lab1.views;

import com.nikitavbv.assignments.oop.lab1.models.Apartment;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class ApartmentsView {
  private static final long NOTHING_FOUND_DELAY = 100; // ms

  private static final String DEFAULT_APARTMENTS_TABLE_HEADER = "Apartments";
  private static final String DEFAULT_ON_NOTHING_FOUND_MESSAGE = "Oops, nothing here...";

  private PrintStream outputWriter;
  private PrintStream errorWriter;

  public ApartmentsView(OutputStream outputStream, OutputStream errorStream) {
      outputWriter = new PrintStream(outputStream);
      errorWriter = new PrintStream(errorStream);
  }

  public void showApartments(Apartment[] apartments) {
    showApartments(DEFAULT_APARTMENTS_TABLE_HEADER, DEFAULT_ON_NOTHING_FOUND_MESSAGE, apartments);
  }

  public void showApartments(String tableHeader, String onNothingMessage, Apartment[] apartments) {
    outputWriter.println(tableHeader);

    if (apartments.length == 0) {
      errorWriter.println(onNothingMessage);
      delayAfterNothingFound();
      return;
    }

    outputWriter.printf(
            "%10s | %10s | %10s | %10s | %20s | %10s%n",
            "number",
            "area",
            "floor",
            "rooms",
            "type",
            "lifetime"
    );

    Arrays.stream(apartments)
            .map(this::showApartment)
            .forEach(outputWriter::println);
  }

  private String showApartment(Apartment apartment) {
    return String.format(
            "%10d | %10f | %10d | %10s | %20s | %10f",
            apartment.getFlatNumber(),
            apartment.getArea(),
            apartment.getFloor(),
            apartment.getTotalRooms(),
            apartment.getBuildingType(),
            apartment.getLifetimeYears()
    );
  }

  private void delayAfterNothingFound() {
    try {
      Thread.sleep(NOTHING_FOUND_DELAY);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
