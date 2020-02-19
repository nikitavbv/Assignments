package com.nikitavbv.assignments.oop.lab1.models;

public enum BuildingType {
  DETACHED,
  SEMI_DETACHED,
  COTTAGE,
  TERRACE,
  FLAT_BLOCK;

  public int maxNumberOfFlats() {
    switch (this) {
      case DETACHED:
      case COTTAGE:
        return 1;
      case SEMI_DETACHED:
        return 2;
      case TERRACE:
        return 50;
      case FLAT_BLOCK:
        return 400;
      default:
        throw new AssertionError("Max number of flats not specified for: " + this);
    }
  }

  public double maxArea() {
    switch (this) {
      case DETACHED:
      case SEMI_DETACHED:
        return 300;
      case COTTAGE:
        return 250;
      case TERRACE:
        return 200;
      case FLAT_BLOCK:
        return 150;
      default:
        throw new AssertionError("Max area is not specified for: " + this);
    }
  }

  public int maxFloor() {
    switch (this) {
      case DETACHED:
      case SEMI_DETACHED:
      case TERRACE:
      case COTTAGE:
        return 1;
      case FLAT_BLOCK:
        return 35;
      default:
        throw new AssertionError("Max floors is not specified for: " + this);
    }
  }

  public int maxTotalRooms() {
    switch (this) {
      case DETACHED:
        return 12;
      case SEMI_DETACHED:
        return 10;
      case TERRACE:
      case COTTAGE:
        return 8;
      case FLAT_BLOCK:
        return 6;
      default:
        throw new AssertionError("Max rooms is not set for: " + this);
    }
  }

  public int maxLifetime() {
    return 20; // does not depend on building time
  }
}
