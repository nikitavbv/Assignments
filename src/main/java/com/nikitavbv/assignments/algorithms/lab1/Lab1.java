package com.nikitavbv.assignments.algorithms.lab1;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

public class Lab1 {

  private static final int RANDOM_ELEMENT_BOUND = 10000;
  private static final int[] ARRAY_SIZES = new int[] { 10, 100, 1000, 5000, 10000, 20000, 50000 };

  private static final String LAB_IMAGES_DIR = "./reports/algorithms/lab1/images/";
  private static final String COMPARISONS_CHART_IMAGE_OUTPUT_PATH = LAB_IMAGES_DIR + "1";
  private static final String SWAPS_CHART_IMAGE_OUTPUT_PATH = LAB_IMAGES_DIR + "2";

  private static final Color COLOR_GREEN = new Color(46, 204, 113);
  private static final Color COLOR_RED = new Color(231, 76, 60);
  private static final Color COLOR_BLUE = new Color(52, 152, 219);
  private static final Color COLOR_VIOLET = new Color(142, 68, 173);
  private static final Color COLOR_YELLOW = new Color(241, 196, 15);

  public static void main(String[] args) throws IOException {
    sortRandomArray(100);
    sortRandomArray(1000);

    System.out.printf("%6s %10s %10s%n", "n", "compar.", "swaps");
    System.out.println("Pre-sorted arrays");
    Map<Integer, SortingMetrics> preSortedArrays = runSortForDifferentArraySizes((size) -> IntStream.range(0, size).toArray());
    System.out.println("Reversed arrays");
    Map<Integer, SortingMetrics> reversedArrays = runSortForDifferentArraySizes((size) ->
            IntStream.range(0, size).map(i -> size - i).toArray());
    System.out.println("Random arrays");
    Map<Integer, SortingMetrics> randomArrays = runSortForDifferentArraySizes((size) -> {
      int[] arr = IntStream.range(0, size).toArray();
      shuffle(arr);
      return arr;
    });

    makeNumberOfComparisonsChart(preSortedArrays, reversedArrays, randomArrays);
    makeNumberOfSwapsChart(preSortedArrays, reversedArrays, randomArrays);
  }

  private static void makeNumberOfComparisonsChart(Map<Integer, SortingMetrics> preSortedArrays,
                                                   Map<Integer, SortingMetrics> reversedArrays,
                                                   Map<Integer, SortingMetrics> randomArrays) throws IOException {
    final XYChart chart = new XYChartBuilder()
            .width(800)
            .height(600)
            .title("Time complexity")
            .xAxisTitle("Array size")
            .yAxisTitle("Total comparisons")
            .theme(Styler.ChartTheme.GGPlot2)
            .build();

    chart.getStyler().setChartBackgroundColor(Color.WHITE);
    chart.getStyler().setPlotBorderVisible(false);
    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

    chart.addSeries("pre-sorted", ARRAY_SIZES, Arrays.stream(ARRAY_SIZES)
            .map(k -> preSortedArrays.get(k).getTotalComparisons()).toArray())
            .setMarkerColor(COLOR_GREEN)
            .setLineColor(COLOR_GREEN);
    chart.addSeries("reversed", ARRAY_SIZES, Arrays.stream(ARRAY_SIZES)
            .map(k -> reversedArrays.get(k).getTotalComparisons()).toArray())
            .setMarkerColor(COLOR_RED)
            .setLineColor(COLOR_RED);
    chart.addSeries("random", ARRAY_SIZES, Arrays.stream(ARRAY_SIZES)
            .map(k -> randomArrays.get(k).getTotalComparisons()).toArray())
            .setMarkerColor(COLOR_BLUE)
            .setLineColor(COLOR_BLUE);
    addBestAndWorstCaseSeriesToChart(chart);

    BitmapEncoder.saveBitmap(chart, COMPARISONS_CHART_IMAGE_OUTPUT_PATH, BitmapEncoder.BitmapFormat.PNG);

    new SwingWrapper<>(chart).displayChart();
  }

  private static void makeNumberOfSwapsChart(Map<Integer, SortingMetrics> preSortedArrays,
                                                   Map<Integer, SortingMetrics> reversedArrays,
                                                   Map<Integer, SortingMetrics> randomArrays) throws IOException {
    final XYChart chart = new XYChartBuilder()
            .width(800)
            .height(600)
            .title("Time complexity")
            .xAxisTitle("Array size")
            .yAxisTitle("Total swaps")
            .theme(Styler.ChartTheme.GGPlot2)
            .build();

    chart.getStyler().setChartBackgroundColor(Color.WHITE);
    chart.getStyler().setPlotBorderVisible(false);
    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

    chart.addSeries("pre-sorted", ARRAY_SIZES, Arrays.stream(ARRAY_SIZES)
            .map(k -> preSortedArrays.get(k).getTotalSwaps()).toArray())
            .setMarkerColor(COLOR_GREEN)
            .setLineColor(COLOR_GREEN);
    chart.addSeries("reversed", ARRAY_SIZES, Arrays.stream(ARRAY_SIZES)
            .map(k -> reversedArrays.get(k).getTotalSwaps()).toArray())
            .setMarkerColor(COLOR_RED)
            .setLineColor(COLOR_RED);
    chart.addSeries("random", ARRAY_SIZES, Arrays.stream(ARRAY_SIZES)
            .map(k -> randomArrays.get(k).getTotalSwaps()).toArray())
            .setMarkerColor(COLOR_BLUE)
            .setLineColor(COLOR_BLUE);

    BitmapEncoder.saveBitmap(chart, SWAPS_CHART_IMAGE_OUTPUT_PATH, BitmapEncoder.BitmapFormat.PNG);

    new SwingWrapper<>(chart).displayChart();
  }

  private static void addBestAndWorstCaseSeriesToChart(XYChart chart) {
    chart.addSeries(
            "best case",
            Arrays.stream(ARRAY_SIZES).mapToDouble(n -> n).toArray(),
            Arrays.stream(ARRAY_SIZES).mapToDouble(n -> n * Math.log(n)).toArray()
    ).setMarkerColor(COLOR_VIOLET).setLineColor(COLOR_VIOLET);
    chart.addSeries(
            "worst case",
            Arrays.stream(ARRAY_SIZES).mapToDouble(n -> n).toArray(),
            Arrays.stream(ARRAY_SIZES).mapToDouble(n -> Math.pow(n, 4f/3)).toArray()
    ).setMarkerColor(COLOR_YELLOW).setLineColor(COLOR_YELLOW);
  }

  private static Map<Integer, SortingMetrics> runSortForDifferentArraySizes(ArrayProvider arrayProvider) {
    Map<Integer, SortingMetrics> results = new HashMap<>();
    Arrays.stream(ARRAY_SIZES).forEach(size -> {
      int[] arr = arrayProvider.provideArray(size);
      SortingMetrics metrics = ShellSort.sort(arr);
      System.out.printf("%6d %10d %10d%n", size, metrics.getTotalComparisons(), metrics.getTotalSwaps());
      results.put(size, metrics);
    });
    printDelimiter();
    return results;
  }

  private static void shuffle(int[] array) {
    Random random = new Random();
    int count = array.length;
    for (int i = count; i > 1; i--) {
      swap(array, i - 1, random.nextInt(i));
    }
  }

  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  private static void sortRandomArray(int arrayLength) {
    int[] arr = new Random().ints(arrayLength).map(i -> Math.abs(i) % RANDOM_ELEMENT_BOUND).toArray();
    System.out.println("Initial array of " + arr.length + " elements");
    System.out.println(Arrays.toString(arr));

    ShellSort.sort(arr);

    System.out.println("Sorted array:");
    System.out.println(Arrays.toString(arr));
    printDelimiter();
  }

  private static void printDelimiter() {
    System.out.println("--------------------");
  }

  private interface ArrayProvider {
    int[] provideArray(int size);
  }

}
