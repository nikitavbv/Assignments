package com.nikitavbv.assignments.oop.lab1;

public class Subscriber {

  private final String firstName;
  private final String middleName;
  private final String lastName;

  private final String address;

  private final int cityCallTime;
  private final int intercityCallTime;

  public Subscriber(String firstName, String middleName, String lastName, String address, int cityCallTime,
                    int intercityCallTime) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.address = address;
    this.cityCallTime = cityCallTime;
    this.intercityCallTime = intercityCallTime;
  }

  public boolean performedIntercityCalls() {
    return getIntercityCallTime() > 0;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }

  public int getCityCallTime() {
    return cityCallTime;
  }

  public int getIntercityCallTime() {
    return intercityCallTime;
  }

  @Override
  public String toString() {
    return String.format(
      "%20s | %20s | %20s | %10s | %10s | %10s",
      firstName,
      middleName,
      lastName,
      address,
      cityCallTime,
      intercityCallTime
    );
  }
}
