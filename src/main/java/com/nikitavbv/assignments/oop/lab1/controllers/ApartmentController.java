package com.nikitavbv.assignments.oop.lab1.controllers;

import com.nikitavbv.assignments.oop.lab1.models.Apartment;
import com.nikitavbv.assignments.oop.lab1.util.ApartmentProvider;
import com.nikitavbv.assignments.oop.lab1.views.ApartmentsView;
import com.nikitavbv.assignments.oop.lab1.input.ApartmentSearchUserInput;
import java.util.Arrays;
import java.util.function.Predicate;

@SuppressWarnings("FieldCanBeLocal")
public class ApartmentController {

  private ApartmentProvider apartmentProvider;
  private ApartmentsView apartmentsView;
  private ApartmentSearchUserInput apartmentSearchUserInput;

  private Apartment[] apartments;

  public ApartmentController(ApartmentProvider apartmentGenerator,
                             ApartmentsView apartmentsView,
                             ApartmentSearchUserInput apartmentSearchUserInput) {
    this.apartmentProvider = apartmentGenerator;
    this.apartmentsView = apartmentsView;
    this.apartmentSearchUserInput = apartmentSearchUserInput;

    this.apartments = apartmentProvider.allApartments();
  }

  public void runShowAll() {
    apartmentsView.showApartments(apartments);
  }

  public void runSearchByRooms() {
    int numberOfRoomsFilter = apartmentSearchUserInput.requestNumberOfRooms();
    apartmentsView.showApartments(
            "Search results (by number of rooms)",
            "Nothing found with rooms = " + numberOfRoomsFilter,
            apartmentsByCriteria(apartments, numberOfRoomsCriteria(numberOfRoomsFilter))
    );
  }

  public void runSearchByAreaAndFloor() {
    double minArea = apartmentSearchUserInput.requestArea();
    int minFloor = apartmentSearchUserInput.requestFloor();
    apartmentsView.showApartments(
            "Search results (by area and floor)",
            String.format("Nothing found with area>=%f and floor>=%d", minArea, minFloor),
            apartmentsByCriteria(apartments, minAreaCriteria(minArea).and(minFloorCriteria(minFloor)))
    );
  }

  private Apartment[] apartmentsByCriteria(Apartment[] apartments, Predicate<Apartment> criteria) {
    return Arrays.stream(apartments).filter(criteria).toArray(Apartment[]::new);
  }

  private Predicate<Apartment> numberOfRoomsCriteria(int numberOfRooms) {
    return apartment -> apartment.getTotalRooms() == numberOfRooms;
  }

  private Predicate<Apartment> minAreaCriteria(double minArea) {
    return apartment -> apartment.getArea() >= minArea;
  }

  private Predicate<Apartment> minFloorCriteria(int minFloor) {
    return apartment -> apartment.getFloor() >= minFloor;
  }
}
