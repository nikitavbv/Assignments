package com.nikitavbv.assignments.algorithms.lab4;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {

  private static final double RADIUS_OF_EARH = 6371;

  String name = "";
  final double lat, lng;

  Location(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }

  public double distanceTo(Location other) {
    double latDistance = Math.toRadians(other.lat - lat);
    double lonDistance = Math.toRadians(other.lng - lng);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(other.lat))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return RADIUS_OF_EARH * c * 1000;
  }

  static Location fromJson(JSONObject obj) {
    try {
      return new Location(obj.getDouble("lat"), obj.getDouble("lng"));
    } catch (JSONException e) {
      throw new RuntimeException("Failed to deserialize location from json", e);
    }
  }

  JSONObject toJsonObject() {
    try {
      JSONObject result = new JSONObject();
      result.put("lat", lat);
      result.put("lng", lng);
      return result;
    } catch(JSONException e) {
      throw new RuntimeException("Failed to serialize location to json", e);
    }
  }

  @Override
  public String toString() {
    return "Location{" + name + " " +
            "lat=" + lat +
            ", lng=" + lng +
            '}';
  }
}
