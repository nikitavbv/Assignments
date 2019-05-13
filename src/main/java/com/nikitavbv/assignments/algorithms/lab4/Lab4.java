package com.nikitavbv.assignments.algorithms.lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class Lab4 {

  // Albania
  private static final String REGION = "AL";
  private static String[] CITIES = new String[] {
    "Tirana", "Durrës", "Vlorë", "Elbasan", "Berat",
    "Korçë", "Pogradec", "Sarandë", "Kukës", "Leskovik",
    "Krasta", "Shëngjin", "Krujë District", "Vorë", "Cërrik"
  };
  private static final int MIN_CITY_DISTANCE = 10000; // meters
  private static double[][] distanceMatrix = new double[CITIES.length][CITIES.length];
  private static Map<String, Double> distances = new HashMap<>();

  private static final String GOOGLE_MAPS_API_KEY_FILE = ".google_maps_api_key";

  public static void main(String[] args) throws IOException {
    GoogleMapsAPI api = new GoogleMapsAPI(loadGoogleMapsAPIKey(), REGION);

    makeDistanceMatrix(api);
    printDistanceMatrix();
    Graph graph = new Graph(distanceMatrix);
    runGreedySearch(api, graph);
    runAStarSearch(api, graph);
  }

  private static void runGreedySearch(GoogleMapsAPI api, Graph graph) throws IOException {
    System.out.println("Greedy search");
    GreedySearch search = new GreedySearch();
    Location[] cityLocations = Arrays.stream(CITIES).map(city -> {
      try {
        return api.getCityLocation(city);
      } catch (IOException e) {
        throw new RuntimeException("Failed to get city location", e);
      }
    }).toArray(Location[]::new);
    for (int i = 0; i < CITIES.length; i++) {
      for (int j = 0; j < CITIES.length; j++) {
        if (i == j) {
          continue;
        }
        List<Integer> route = search.findRoute(graph, i, j, cityLocations);
        System.out.println(CITIES[i] + " -> " + CITIES[j] + " distance: " + String.format("%.3f", calculateDistance(api, route))
                + "km. Route: " + route.stream().map(n -> CITIES[n]).collect(Collectors.joining("->")));
      }
    }
  }

  private static void runAStarSearch(GoogleMapsAPI api, Graph graph) throws IOException {
    System.out.println("A* search");
    AStarSearch search = new AStarSearch();
    Location[] cityLocations = Arrays.stream(CITIES).map(city -> {
      try {
        return api.getCityLocation(city);
      } catch (IOException e) {
        throw new RuntimeException("Failed to get city location", e);
      }
    }).toArray(Location[]::new);
    for (int i = 0; i < CITIES.length; i++) {
      for (int j = 0; j < CITIES.length; j++) {
        if (i == j) {
          continue;
        }
        List<Integer> route = search.findRoute(graph, i, j, cityLocations);
        System.out.println(CITIES[i] + " -> " + CITIES[j] + " distance: " + String.format("%.3f", calculateDistance(api, route))
                + "km. Route: " + route.stream().map(n -> CITIES[n]).collect(Collectors.joining("->")));
      }
    }
  }

  private static double calculateDistance(GoogleMapsAPI api, List<Integer> route) throws IOException {
    double distance = 0;

    int city = route.get(0);
    for (int i = 1; i < route.size(); i++) {
      if (!distances.containsKey(city + "_" + i)) {
        distances.put(city + "_" + i, api.getCityLocation(CITIES[city]).distanceTo(api.getCityLocation(CITIES[i])));
      }
      distance += distances.get(city + "_" + i);
      city = route.get(i);
    }

    return distance / 1000;
  }

  private static void printDistanceMatrix() {
    System.out.println("\t" + String.join("\t", CITIES));
    for (int i = 0; i < CITIES.length; i++) {
      System.out.print(CITIES[i] + "\t");
      Stream<String> result = Arrays.stream(distanceMatrix[i]).mapToObj(d -> d == -1 ? "-" : (d/1000) + "km");
      System.out.println(result.collect(Collectors.joining("\t")));
    }
  }

  private static void makeDistanceMatrix(GoogleMapsAPI api) throws IOException {
    for (int fromIndex = 0; fromIndex < CITIES.length; fromIndex++) {
      for (int toIndex = 0; toIndex < CITIES.length; toIndex++) {
        List<Location> otherCities = new ArrayList<>();
        for (int i = 0; i < CITIES.length; i++) {
          if (i == fromIndex || i == toIndex) {
            continue;
          }
          Location loc = api.getCityLocation(CITIES[i]);
          loc.name = CITIES[i];
          otherCities.add(loc);
        }
        final int from = fromIndex;
        final int to = toIndex;
        final double lineDist = api.getCityLocation(CITIES[from]).distanceTo(api.getCityLocation(CITIES[to]));
        List<Route> routes = api.getRoutes(CITIES[fromIndex], CITIES[toIndex]).stream()
                .filter(route -> !isRouteCloseToAny(route, otherCities, lineDist))
                .collect(Collectors.toList());
        if (routes.size() == 0) {
          if (distanceMatrix[fromIndex][toIndex] == 0) {
            distanceMatrix[fromIndex][toIndex] = -1;
          }
          continue;
        }

        distanceMatrix[fromIndex][toIndex] = routes.stream()
                .reduce((r1, r2) -> (r1.distance < r2.distance ? r1 : r2)).get().distance;
        distanceMatrix[toIndex][fromIndex] = distanceMatrix[fromIndex][toIndex];
      }
    }
  }

  private static boolean isRouteCloseToAny(Route route, List<Location> points, double lineDist) {
    for (Location location : route.locations) {
      for (Location otherLocation : points) {
        if (location.distanceTo(otherLocation) < lineDist * 0.6) { // MAGIC!
          return true;
        }
      }
    }
    return false;
  }

  private static String loadGoogleMapsAPIKey() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File(GOOGLE_MAPS_API_KEY_FILE)));
    String key = reader.readLine().replaceAll("\n", "");
    reader.close();
    return key;
  }

}
