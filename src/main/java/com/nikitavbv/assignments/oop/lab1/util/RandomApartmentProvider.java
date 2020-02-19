package com.nikitavbv.assignments.oop.lab1.util;

import com.nikitavbv.assignments.oop.lab1.models.Apartment;
import com.nikitavbv.assignments.oop.lab1.models.BuildingType;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomApartmentProvider implements ApartmentProvider {

  private final Random random;
  private final int totalApartments;

  public RandomApartmentProvider(Random random, int totalApartments) {
    this.random = random;
    this.totalApartments = totalApartments;
  }

  public Apartment nextApartment() {
    BuildingType type = randomBuildingType();
    return new Apartment(
            randomFlatNumber(type),
            randomArea(type),
            randomFloor(type),
            randomTotalRooms(type),
            type,
            randomLifetimeYears(type)
    );
  }

  public Apartment[] allApartments() {
    return IntStream.range(0, totalApartments)
            .mapToObj(i -> nextApartment())
            .toArray(Apartment[]::new);
  }

  private int randomFlatNumber(BuildingType type) {
    return random.nextInt(type.maxNumberOfFlats());
  }

  private double randomArea(BuildingType type) {
    return (double) Math.round(random.nextDouble() * type.maxArea() * 100) / 100;
  }

  private int randomFloor(BuildingType type) {
    return random.nextInt(type.maxFloor());
  }

  private int randomTotalRooms(BuildingType type) {
    return random.nextInt(type.maxTotalRooms() - 1) + 1;
  }

  private BuildingType randomBuildingType() {
    return BuildingType.values()[random.nextInt(BuildingType.values().length)];
  }

  private double randomLifetimeYears(BuildingType type) {
    return (double) Math.round(random.nextDouble() * type.maxLifetime() * 100) / 100;
  }
}
