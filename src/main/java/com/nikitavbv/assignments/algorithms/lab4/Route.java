package com.nikitavbv.assignments.algorithms.lab4;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Route {

  final double distance;
  final List<Location> locations;

  Route(double distance, List<Location> locations) {
    this.distance = distance;
    this.locations = locations;
  }

  static Route fromJson(JSONObject obj) {
    try {
      return new Route(obj.getDouble("distance"), locationsFromJson(obj.getJSONArray("points")));
    } catch (JSONException e) {
      throw new RuntimeException("Failed to load route from json", e);
    }
  }

  private static List<Location> locationsFromJson(JSONArray arr) {
    try {
      List<Location> result = new ArrayList<>();
      for (int i = 0; i < arr.length(); i++) {
          result.add(Location.fromJson(arr.getJSONObject(i)));
      }
      return result;
    } catch (JSONException e) {
      throw new RuntimeException("Failed to deserialize location list from json");
    }
  }

  JSONObject toJson() {
    try {
      JSONObject result = new JSONObject();
      result.put("distance", distance);
      result.put("points", locationsAsJsonArray());
      return result;
    } catch(JSONException e) {
      throw new RuntimeException("Failed to serialize route to json", e);
    }
  }

  private JSONArray locationsAsJsonArray() {
    JSONArray array = new JSONArray();
    locations.stream().map(Location::toJsonObject).forEach(array::put);
    return array;
  }

}
