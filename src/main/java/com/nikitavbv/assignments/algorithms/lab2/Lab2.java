package com.nikitavbv.assignments.algorithms.lab2;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

@SuppressWarnings("Duplicates")
public class Lab2 {

  private static final int[] HASH_TABLE_SIZES = new int[] {100, 1000, 5000, 10000, 20000};

  private static final boolean REQUEST_INPUT = true;
  private static final String LAB_IMAGES_DIR = "./reports/algorithms/lab2/images/";
  private static final String COMPARISONS_CHART_IMAGE_OUTPUT_PATH = LAB_IMAGES_DIR + "1";
  private static final String ACCESS_CHART_IMAGE_OUTPUT_PATH = LAB_IMAGES_DIR + "2";

  private static Random random = new Random();

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    random.setSeed(43);

    MyHashTable<Integer, Integer> testArr100 = new MyHashTable<>(100, false);
    Set<Integer> keys = randomUniqueIntegers(100);
    keys.forEach(v -> testArr100.put(v, v * 2));
    System.out.println("Hash table - array implementation, " + keys.size() + " elements:");
    testArr100.printTo(System.out);
    final Integer keyToFind;
    if (REQUEST_INPUT) {
      System.out.print("Element to find: ");
      keyToFind = Integer.parseInt(reader.readLine());
    } else {
      keyToFind = (Integer) keys.toArray()[random.nextInt(keys.size())];
    }
    Integer foundValue = testArr100.get(keyToFind)
            .orElseThrow(() -> new RuntimeException("Requested key " + keyToFind + " is not found"));
    System.out.println("Value for key " + keyToFind + " is " + foundValue);

    System.out.println("---------------------------------------------------------------------");

    MyLinkedListHashTable<Integer, Integer> testLL1000 = new MyLinkedListHashTable<>(1000, false);
    keys = randomUniqueIntegers(1000);
    keys.forEach(v -> testLL1000.put(v, v * -3));
    System.out.println("Hash table - linked list implementation, " + keys.size() + " elements:");
    testLL1000.printTo(System.out);
    final Integer secondKeyToFind;
    if (REQUEST_INPUT) {
      System.out.print("Element to find: ");
      secondKeyToFind = Integer.parseInt(reader.readLine());
    } else {
      secondKeyToFind = (Integer) keys.toArray()[random.nextInt(keys.size())];
    }
    foundValue = testLL1000.get(secondKeyToFind)
            .orElseThrow(() -> new RuntimeException("Requested key " + secondKeyToFind + " is not found"));
    System.out.println("Value for key " + keyToFind + " is " + foundValue);

    System.out.println("---------------------------------------------------------------------");
    Map<Integer, Integer> arrCompares = new HashMap<>();
    Map<Integer, Integer> arrAccess = new HashMap<>();
    System.out.println("Array implementation, compares and access:");
    Arrays.stream(HASH_TABLE_SIZES).forEach(size -> {
      MyHashTable<Integer, Integer> myHashTable = new MyHashTable<>(size, false);
      testComplexity(size, myHashTable);
      arrCompares.put(size, myHashTable.getTotalCompares() / size);
      arrAccess.put(size, myHashTable.getTotalAccess() / size);
    });

    System.out.println("---------------------------------------------------------------------");
    System.out.println("Linked list implementation, compares and access:");
    Map<Integer, Integer> llCompares = new HashMap<>();
    Map<Integer, Integer> llAccess = new HashMap<>();
    Arrays.stream(HASH_TABLE_SIZES).forEach(size -> {
      MyLinkedListHashTable<Integer, Integer> hashTable = new MyLinkedListHashTable<>(size, false);
      testComplexity(size, hashTable);
      llCompares.put(size, hashTable.getTotalCompares() / size);
      llAccess.put(size, hashTable.getTotalAccess() / size);
    });

    makeChart("Total comparisons", COMPARISONS_CHART_IMAGE_OUTPUT_PATH, arrCompares, llCompares);
    makeChart("Total access", ACCESS_CHART_IMAGE_OUTPUT_PATH, arrAccess, llAccess);
  }

  private static void makeChart(String yAxis, String path, Map<Integer, Integer> arrImpl,
                                Map<Integer, Integer> llImpl) throws IOException {
    final XYChart chart = new XYChartBuilder()
            .width(800)
            .height(600)
            .title("Time complexity")
            .xAxisTitle("Elements in hash table")
            .yAxisTitle(yAxis)
            .theme(Styler.ChartTheme.GGPlot2)
            .build();

    chart.getStyler().setChartBackgroundColor(Color.WHITE);
    chart.getStyler().setPlotBorderVisible(false);
    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

    chart.addSeries("Array-based implementation", HASH_TABLE_SIZES, Arrays.stream(HASH_TABLE_SIZES)
            .map(arrImpl::get).toArray());
    chart.addSeries("Linked list-based implementation", HASH_TABLE_SIZES, Arrays.stream(HASH_TABLE_SIZES)
            .map(llImpl::get).toArray());

    BitmapEncoder.saveBitmap(chart, path, BitmapEncoder.BitmapFormat.PNG);

    new SwingWrapper<>(chart).displayChart();
  }

  private static void testComplexity(int size, MyHashTableInterface<Integer, Integer> hashTable) {
    Set<Integer> testKeys = randomUniqueIntegers(size);
    testKeys.forEach(v -> hashTable.put(v, v * 4));
    for (int i = 0; i < size; i++) {
      Integer testKeyToFind = (Integer) testKeys.toArray()[random.nextInt(testKeys.size())];
      hashTable.get(testKeyToFind).orElseThrow(() -> new RuntimeException("Value for key not found"));
    }
    System.out.printf("size %d: %d compares, %d access%n", size, hashTable.getTotalCompares() / size, hashTable.getTotalAccess() / size);
  }

  private static Set<Integer> randomUniqueIntegers(int total) {
    Set<Integer> result = new HashSet<>();
    while (result.size() < total) {
      result.add(random.nextInt(100000));
    }
    return result;
  }

}
