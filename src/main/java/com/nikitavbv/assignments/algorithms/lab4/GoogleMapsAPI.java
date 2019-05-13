package com.nikitavbv.assignments.algorithms.lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleMapsAPI {

  private static final String CITY_LOCATION_CACHE_DIRECTORY = "data/city_location";
  private static final String ROUTES_CACHE_DIRECTORY = "data/routes";

  private final String apiKey;
  private final String region;

  public GoogleMapsAPI(String apiKey, String region) {
    this.apiKey = apiKey;
    this.region = region;
  }

  public Location getCityLocation(String cityName) throws IOException {
    if (getCityLocationCacheFile(cityName).exists()) {
      return getCityLocationFromCacheFile(cityName);
    }

    try {
      Location location = requestCityLocation(cityName);
      saveCityLocationToCacheFile(cityName, location);
      return location;
    } catch(URISyntaxException | IOException | JSONException e) {
      throw new RuntimeException("Failed to get city location info", e);
    }
  }

  public List<Route> getRoutes(String from, String to) throws IOException {
    if (getRoutesCacheFile(from, to).exists()) {
      return getRoutesFromCacheFile(from, to);
    }

    try {
      List<Route> routes = requestRoutes(from, to);
      saveRoutesToCacheFile(from, to, routes);
      return routes;
    } catch(URISyntaxException | IOException | JSONException e) {
      throw new RuntimeException("Failed to get route location info", e);
    }
  }

  private List<Route> requestRoutes(String from, String to) throws URISyntaxException, IOException, JSONException {
    System.out.println("Requesting routes from " + from + " to " + to + "...");

    HttpClient client = HttpClientBuilder.create().build();
    URIBuilder builder = new URIBuilder("https://maps.googleapis.com/maps/api/directions/json");
    builder.addParameter("origin", from);
    builder.addParameter("destination", to);
    builder.setParameter("alternatives", "true");
    builder.setParameter("region", region);
    builder.addParameter("key", apiKey);
    HttpGet request = new HttpGet(builder.build());
    JSONObject response = new JSONObject(EntityUtils.toString(client.execute(request).getEntity()));
    JSONArray routes = response.getJSONArray("routes");
    List<Route> result = new ArrayList<>();
    for (int i = 0; i < routes.length(); i++) {
      JSONObject routeObj = routes.getJSONObject(i);
      double distance = 0;
      List<Location> points = new ArrayList<>();
      JSONArray legs = routeObj.getJSONArray("legs");

      for (int k = 0; k < legs.length(); k++) {
        distance += legs.getJSONObject(k).getJSONObject("distance").getDouble("value");
        JSONArray steps = legs.getJSONObject(k).getJSONArray("steps");
        for (int j = 0; j < steps.length(); j++) {
          Location startLocation = Location.fromJson(steps.getJSONObject(i).getJSONObject("start_location"));
          Location endLocation = Location.fromJson(steps.getJSONObject(i).getJSONObject("end_location"));
          points.add(startLocation);
          points.add(endLocation);
        }
      }
      result.add(new Route(distance, points));
    }
    return result;
  }

  private List<Route> getRoutesFromCacheFile(String from, String to) throws IOException {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(getRoutesCacheFile(from, to)));
      StringBuilder lines = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        lines.append(line);
        line = reader.readLine();
      }
      reader.close();

      JSONArray resultArr = new JSONArray(lines.toString());
      List<Route> result = new ArrayList<>();
      for (int i = 0; i < resultArr.length(); i++) {
        result.add(Route.fromJson(resultArr.getJSONObject(i)));
      }

      return result;
    } catch(JSONException e) {
      throw new RuntimeException("Failed to load routes from file", e);
    }
  }

  private void saveRoutesToCacheFile(String from, String to, List<Route> routes) throws IOException {
    JSONArray arr = new JSONArray();
    routes.stream().map(Route::toJson).forEach(arr::put);
    PrintWriter pw = new PrintWriter(new FileWriter(getRoutesCacheFile(from, to)));
    pw.write(arr.toString());
    pw.close();
  }

  private File getRoutesCacheFile(String from, String to) {
    return new File(getRoutesDirectory(), String.format("%s_%s", from, to));
  }

  private File getRoutesDirectory() {
    File routesDirectory = new File(ROUTES_CACHE_DIRECTORY);
    if (!routesDirectory.exists()) {
      if (!routesDirectory.mkdirs()) {
        throw new RuntimeException("Failed to create routes cache directory");
      }
    }
    return routesDirectory;
  }

  private void saveCityLocationToCacheFile(String cityName, Location location) throws IOException {
    PrintWriter pw = new PrintWriter(new FileWriter(getCityLocationCacheFile(cityName)));
    pw.write(location.lat + ";" + location.lng);
    pw.close();
  }

  private Location requestCityLocation(String cityName) throws URISyntaxException, IOException, JSONException {
    HttpClient client = HttpClientBuilder.create().build();
    URIBuilder builder = new URIBuilder("https://maps.googleapis.com/maps/api/geocode/json");
    builder.addParameter("address", cityName);
    builder.addParameter("key", apiKey);
    HttpGet request = new HttpGet(builder.build());
    JSONObject result = new JSONObject(EntityUtils.toString(client.execute(request).getEntity()));
    JSONObject location = result.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
            .getJSONObject("location");
    return new Location(location.getDouble("lat"), location.getDouble("lng"));
  }

  private Location getCityLocationFromCacheFile(String cityName) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(getCityLocationCacheFile(cityName)));
    String[] line = reader.readLine().replace("\n", "").split(";");
    reader.close();
    return new Location(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
  }

  private File getCityLocationCacheFile(String cityName) {
    return new File(getCityLocationDirectory(), cityName);
  }

  private File getCityLocationDirectory() {
    File file = new File(CITY_LOCATION_CACHE_DIRECTORY);
    if (!file.exists()) {
      if(!file.mkdirs()) {
        throw new RuntimeException("Failed to create city location cache directory");
      }
    }
    return file;
  }

}
