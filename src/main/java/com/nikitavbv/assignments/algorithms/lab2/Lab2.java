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

  private static final int TEST_ARRAY_SIZE = 100;
  private static final int TEST_LINKED_LIST_SIZE = 1000;
  private static final int RANDOM_BOUND = 100000;
  private static final boolean REQUEST_INPUT = true;
  private static final String LAB_IMAGES_DIR = "./reports/algorithms/lab2/images/";
  private static final String COMPARISONS_CHART_IMAGE_OUTPUT_PATH = LAB_IMAGES_DIR + "1";
  private static final String ACCESS_CHART_IMAGE_OUTPUT_PATH = LAB_IMAGES_DIR + "2";
  private static final int RANDOM_SEED = 43;
  private static final int CHART_WIDTH = 800;
  private static final int CHART_HEIGHT = 600;

  private static Random random = new Random();
  private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  private static class SearchMetrics {
    int comparisons;
    int access;

    private SearchMetrics(int comparisons, int access) {
      this.comparisons = comparisons;
      this.access = access;
    }

    public String toString() {
      return String.format("comparisons: %d, access: %d", comparisons, access);
    }
  }

  public static void main(String[] args) throws IOException {
    random.setSeed(RANDOM_SEED);
    testArraySearch();
    testLinkedListSearch();
    buildCharts();
  }

  private static void testLinkedListSearch() throws IOException {
    MyHashTable<Integer, Integer> hashTable = new MyHashTable<>(TEST_LINKED_LIST_SIZE, false);

    LinkedListNode<Integer, Integer> linkedList = new LinkedListNode<>();
    LinkedListNode<Integer, Integer> front = linkedList;
    for (int i = 0; i < TEST_LINKED_LIST_SIZE; i++) {
      linkedList.key = random.nextInt(RANDOM_BOUND);
      linkedList.next = new LinkedListNode<>();
      linkedList = linkedList.next;
    }

    LinkedListNode<Integer, Integer> cursor = front;
    for (int i = 0; i < TEST_LINKED_LIST_SIZE; i++) {
      hashTable.put(cursor.key, i);
      cursor = cursor.next;
    }
    hashTable.printTo(System.out);
    final Integer keyToFind;
    if (REQUEST_INPUT) {
      System.out.print("Element to find: ");
      keyToFind = Integer.parseInt(reader.readLine());
    } else {
      int randomIndex = random.nextInt(TEST_LINKED_LIST_SIZE);
      cursor = front;
      for (int i = 0; i < randomIndex; i++) {
        cursor = cursor.next;
      }
      keyToFind = cursor.key;
    }

    Integer result = hashTable.get(keyToFind)
            .orElseThrow(() -> new RuntimeException("key not found"));
    System.out.println("key: " + keyToFind + " value: " + result);
  }

  private static void makeChart(String yAxis, String path, Map<Integer, Integer> arrImpl,
                                Map<Integer, Integer> llImpl) throws IOException {
    final XYChart chart = new XYChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("Time complexity")
            .xAxisTitle("Number of elements")
            .yAxisTitle(yAxis)
            .theme(Styler.ChartTheme.GGPlot2)
            .build();

    chart.getStyler().setChartBackgroundColor(Color.WHITE);
    chart.getStyler().setPlotBorderVisible(false);
    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

    chart.addSeries("Array", HASH_TABLE_SIZES, Arrays.stream(HASH_TABLE_SIZES)
            .map(arrImpl::get).toArray());
    chart.addSeries("Linked list", HASH_TABLE_SIZES, Arrays.stream(HASH_TABLE_SIZES)
            .map(llImpl::get).toArray());

    BitmapEncoder.saveBitmap(chart, path, BitmapEncoder.BitmapFormat.PNG);

    new SwingWrapper<>(chart).displayChart();
  }

  private static void testArraySearch() throws IOException {
    MyHashTable<Integer, Integer> hashtable = new MyHashTable<>(TEST_ARRAY_SIZE, false);
    Integer[] arr = randomUniqueIntegers(TEST_ARRAY_SIZE);
    for (int i = 0; i < 100; i++) {
      hashtable.put(arr[i], i);
    }
    hashtable.printTo(System.out);
    final Integer keyToFind;
    if (REQUEST_INPUT) {
      System.out.print("element to find: ");
      keyToFind = Integer.parseInt(reader.readLine());
    } else {
      keyToFind = arr[random.nextInt(arr.length)];
    }

    int result = hashtable.get(keyToFind)
            .orElseThrow(() -> new RuntimeException("key not found"));
    System.out.println("key: " + keyToFind + ", value: " + result);
  }

  private static void buildCharts() throws IOException {
    Map<Integer, Integer> arrayCompares = new HashMap<>();
    Map<Integer, Integer> linkedListCompares = new HashMap<>();
    Map<Integer, Integer> arrayAccess = new HashMap<>();
    Map<Integer, Integer> linkedListAccess = new HashMap<>();

    Arrays.stream(HASH_TABLE_SIZES).forEach(size -> {
      Integer[] arr = randomUniqueIntegers(size);
      SearchMetrics arrayMetrics = getArraySearchMetrics(arr);
      SearchMetrics linkedListMetrics = getLinkedListSearchMetrics(arr);

      System.out.println("------------");
      System.out.printf("Size: %d%n", size);
      System.out.println("Array:");
      System.out.println(arrayMetrics);
      System.out.println("Linked List:");
      System.out.println(linkedListMetrics);

      arrayCompares.put(size, arrayMetrics.comparisons);
      linkedListCompares.put(size, linkedListMetrics.comparisons);
      arrayAccess.put(size, arrayMetrics.access);
      linkedListAccess.put(size, linkedListMetrics.access);
    });

    makeChart("Comparisons", COMPARISONS_CHART_IMAGE_OUTPUT_PATH, arrayCompares, linkedListCompares);
    makeChart("Access", ACCESS_CHART_IMAGE_OUTPUT_PATH, arrayAccess, linkedListAccess);
  }

  private static SearchMetrics getArraySearchMetrics(Integer[] arr) {
    int totalArrAccess = 0;
    MyHashTable<Integer, Integer> hashTable = new MyHashTable<>(arr.length, false);
    for (int i = 0; i < arr.length; i++) {
      hashTable.put(arr[i], i);
    }
    for (Integer element : arr) {
      int index = hashTable.get(element).orElseThrow(RuntimeException::new);
      if (!element.equals(arr[index])) {
        throw new AssertionError();
      }
      totalArrAccess++;
    }
    return new SearchMetrics(hashTable.getTotalCompares() / arr.length, totalArrAccess);
  }

  private static SearchMetrics getLinkedListSearchMetrics(Integer[] arr) {
    int totalLLAccess = 0;
    MyHashTable<Integer, Integer> hashTable = new MyHashTable<>(arr.length, false);
    LinkedListNode<Integer, Integer> linkedList = new LinkedListNode<>();
    LinkedListNode<Integer, Integer> front = linkedList;
    for (int value : arr) {
      linkedList.key = value;
      linkedList.next = new LinkedListNode<>();
      linkedList = linkedList.next;
    }

    LinkedListNode<Integer, Integer> cursor = front;
    for (int i = 0; i < arr.length; i++) {
      hashTable.put(cursor.key, i);
      cursor = cursor.next;
      totalLLAccess++;
    }
    totalLLAccess *= arr.length;

    cursor = front;
    for (int i = 0; i < arr.length; i++) {
      int index = hashTable.get(cursor.key).orElseThrow(RuntimeException::new);
      LinkedListNode<Integer, Integer> searchCursor = front;
      for (int j = 0; j < index; j++) {
        totalLLAccess++;
        searchCursor = searchCursor.next;
      }
      cursor = cursor.next;
    }

    return new SearchMetrics(hashTable.getTotalCompares() / arr.length, totalLLAccess / arr.length);
  }

  private static Integer[] randomUniqueIntegers(int total) {
    Set<Integer> result = new HashSet<>();
    while (result.size() < total) {
      result.add(random.nextInt(100000));
    }
    return result.toArray(new Integer[]{});
  }

}
