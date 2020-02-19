package com.nikitavbv.assignments.oop.lab1.models;

public class Apartment {

  private final int flatNumber;
  private final double area; // square meters
  private final int floor;
  private final int totalRooms;
  private final BuildingType buildingType;
  private final double lifetimeYears;

  public Apartment(int flatNumber, double area, int floor, int totalRooms, BuildingType buildingType, double lifetimeYears) {
    this.flatNumber = flatNumber;
    this.area = area;
    this.floor = floor;
    this.totalRooms = totalRooms;
    this.buildingType = buildingType;
    this.lifetimeYears = lifetimeYears;
  }

  public int getFlatNumber() {
    return flatNumber;
  }

  public double getArea() {
    return area;
  }

  public int getFloor() {
    return floor;
  }

  public int getTotalRooms() {
    return totalRooms;
  }

  public BuildingType getBuildingType() {
    return buildingType;
  }

  public double getLifetimeYears() {
    return lifetimeYears;
  }

  @Override
  public String toString() {
    return "Apartment{" +
            "flatNumber=" + flatNumber +
            ", area=" + area +
            ", floor=" + floor +
            ", buildingType=" + buildingType +
            ", lifetimeYears=" + lifetimeYears +
            '}';
  }
}
