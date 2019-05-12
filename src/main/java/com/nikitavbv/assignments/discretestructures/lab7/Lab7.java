package com.nikitavbv.assignments.discretestructures.lab7;

import com.nikitavbv.assignments.discretestructures.lab7.graph.BellmanFordAlgorithm;
import com.nikitavbv.assignments.discretestructures.lab7.graph.BreadthFirstPrinter;
import com.nikitavbv.assignments.discretestructures.lab7.graph.DepthFirstPrinter;
import com.nikitavbv.assignments.discretestructures.lab7.graph.DijkstraAlgorithm;
import com.nikitavbv.assignments.discretestructures.lab7.graph.FloydWarshallAlgorithm;
import com.nikitavbv.assignments.discretestructures.lab7.graph.Graph;
import com.nikitavbv.assignments.discretestructures.lab7.graph.GraphAlgorithm;
import com.nikitavbv.assignments.discretestructures.lab7.graph.GraphPrinter;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

public class Lab7 {

  // total 15 nodes
  private static final int[][] ADJACENCY_MATRIX = new int[][] {
  //         0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16
          {  0,  1,  3, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 0
          {  1,  0, -1, -1,  5, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1 }, // 1
          {  3, -1,  0, -1,  8, -1, -1, -1, -1, -1, -1,  7, -1, -1, -1, -1, -1 }, // 2
          { 10, -1, -1,  0, -1, -1, -1, -1, -1, -1,  5, -1, -1, -1, -1, -1, -1 }, // 3
          { -1,  5,  8, -1,  0,  2,  5, -1, -1, -1,  9, -1, -1, -1, -1,  3, -1 }, // 4
          { -1, -1, -1, -1,  2,  0, -1,  7, -1, -1, -1, -1,  8,  8, -1, -1,  2 }, // 5
          { -1, -1, -1, -1,  5, -1,  0, -1,  4, -1,  1, -1, -1, -1, -1, -1, -1 }, // 6
          { -1, -1, -1, -1, -1,  7, -1,  0, -1,  8, -1,  8, -1, -1, -1, -1, -1 }, // 7
          { -1, -1, -1, -1, -1, -1,  4, -1,  0, -1,  9, -1,  4, -1, -1,  5,  3 }, // 8
          { -1, 11, -1, -1, -1, -1, -1,  8,  9,  0, -1, -1,  8, -1,  2, -1, -1 }, // 9
          { -1, -1, -1,  5,  9, -1,  1, -1, -1, -1,  0,  4, -1, -1, -1, -1, -1 }, // 10
          { -1, -1,  7, -1, -1, -1, -1,  8, -1, -1,  4,  0, -1, -1, -1, -1, -1 }, // 11
          { -1, -1, -1, -1, -1,  8, -1, -1,  4,  8, -1, -1,  0, -1, -1, -1,  9 }, // 12
          { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1,  1, -1 }, // 13
          { -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1, -1, -1, -1,  0, -1,  1 }, // 14
          { -1, -1, -1, -1,  3, -1, -1, -1,  5, -1, -1, -1, -1,  1, -1,  0, -1 }, // 15
          { -1, -1, -1, -1, -1,  2, -1, -1,  3, -1, -1, -1,  9, -1,  1, -1,  0 }, // 16
  };

  private static final int RANDOM_WEIGHT_BOUNDS = 100;
  private static final String LAB_OUTPUT_DIRECTORY = "output/discrete_structures/lab7/";
  private static final String PRINTER_OUTPUT_DIRECTORY = LAB_OUTPUT_DIRECTORY + "print/";
  private static final String ALGORITHM_OUTPUT_DIRECTORY = LAB_OUTPUT_DIRECTORY + "algorithm/";
  private static final String BENCHMARK_DIRECTORY = LAB_OUTPUT_DIRECTORY + "benchmark/";
  private static final String CHART_IMAGE_PATH = "./reports/discreteStructures/lab7/images/";

  private static final int BENCHMARK_FROM = 1000;
  private static final int BENCHMARK_TO = 20000;
  private static final int BENCHMARK_STEP = 1000;
  private static final int CHART_WIDTH = 800;
  private static final int CHART_HEIGHT = 600;

  private static Random random = new Random();
  private static Graph graph = new Graph(ADJACENCY_MATRIX);
  private static List<GraphPrinter> printers = Arrays.asList(
          new DepthFirstPrinter(),
          new BreadthFirstPrinter()
  );
  private static List<GraphAlgorithm> searchAlgorithms = Arrays.asList(
          new DijkstraAlgorithm(),
          new FloydWarshallAlgorithm(),
          new BellmanFordAlgorithm()
  );
  private static List<Class> FIRST_CHART = Arrays.asList(
          DijkstraAlgorithm.class,
          FloydWarshallAlgorithm.class,
          BellmanFordAlgorithm.class
  );
  private static List<Class> SECOND_CHART = Arrays.asList(
        DijkstraAlgorithm.class,
        BellmanFordAlgorithm.class
  );

  public static void main(String[] args) throws IOException {
    Lab7 lab = new Lab7();
    lab.runDemo(graph);
    lab.runBenchmark(BENCHMARK_FROM, BENCHMARK_TO, BENCHMARK_STEP);
    lab.makeChart(FIRST_CHART, "1");
    lab.makeChart(SECOND_CHART, "2");
  }

  private void makeChart(List<Class> algos, String chartName) throws IOException {
    final XYChart chart = new XYChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("Graph algorithms performance")
            .xAxisTitle("Nodes in graph")
            .yAxisTitle("Time, s")
            .theme(Styler.ChartTheme.GGPlot2)
            .build();

    chart.getStyler().setChartBackgroundColor(Color.WHITE);
    chart.getStyler().setPlotBorderVisible(false);
    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

    int[] graphSizes = IntStream.range(1000, 20000+1).filter(i -> i % 1000 == 0).toArray();

    searchAlgorithms.forEach(algo -> {
      if (!algos.contains(algo.getClass())) {
        return;
      }

      int[] arr = Arrays.stream(graphSizes).map(size -> {
        if (isAlreadyBenchmarked(algo, size)) {
          return (int) getBenchmarkResult(algo, size);
        } else {
          return 0;
        }
      }).toArray();
      chart.addSeries(algo.name(), graphSizes, arr);
    });

    BitmapEncoder.saveBitmap(chart, CHART_IMAGE_PATH + chartName, BitmapEncoder.BitmapFormat.PNG);

    new SwingWrapper<>(chart).displayChart();
  }

  private void runBenchmark(int from, int to, int step) {
    IntStream.range(from, to + 1)
            .filter(i -> i % step == 0)
            .forEach(this::runBenchmarkStep);
  }

  private void runBenchmarkStep(int step) {
    int[][] matrix = generateRandomAdjacencyMatrix(step);
    Graph graph = new Graph(matrix);
    searchAlgorithms.parallelStream().forEach(algo -> benchmarkAlgo(algo, graph));
  }

  private void benchmarkAlgo(GraphAlgorithm algo, Graph graph) {
    if (isAlreadyBenchmarked(algo, graph.totalNodes())) {
      System.out.printf("Already benchmarked: %s - %d nodes (%d ms)%n", algo.name(), graph.totalNodes(),
              getBenchmarkResult(algo, graph.totalNodes()));
      return;
    }

    System.out.printf("Starting benchmark for: %s (%d nodes)%n", algo.name(), graph.totalNodes());
    long startedAt = System.currentTimeMillis();
    algo.findAllPaths(graph);
    long benchmarkResult = System.currentTimeMillis() - startedAt;

    System.out.printf("Benchmark finished for: %s (%d nodes) - %d ms%n", algo.name(), graph.totalNodes(),
            benchmarkResult);
    saveBenchmarkResult(algo, graph.totalNodes(), benchmarkResult);
  }

  private long getBenchmarkResult(GraphAlgorithm algo, int matrixSize) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(getFileForBenchmarkResult(algo, matrixSize)));
      long result = Long.parseLong(reader.readLine());
      reader.close();
      return result;
    } catch (IOException e) {
      throw new RuntimeException("Failed to load benchmark result", e);
    }
  }

  private boolean isAlreadyBenchmarked(GraphAlgorithm algo, int matrixSize) {
    return getFileForBenchmarkResult(algo, matrixSize).exists();
  }

  private void saveBenchmarkResult(GraphAlgorithm algo, int matrixSize, long result) {
    try {
      PrintWriter pw = new PrintWriter(new FileWriter(getFileForBenchmarkResult(algo, matrixSize)));
      pw.write(Long.toString(result));
      pw.close();
    } catch (IOException e) {
      throw new RuntimeException("Failed to write benchmark result", e);
    }
  }

  private File getFileForBenchmarkResult(GraphAlgorithm algo, int matrixSize) {
    String algoName = algo.name().toLowerCase().replaceAll(" ", "_");
    return getOutputFile(String.format("%s%s", BENCHMARK_DIRECTORY, algoName), Integer.toString(matrixSize));
  }

  private int[][] generateRandomAdjacencyMatrix(int totalNodes) {
    int[][] matrix = new int[totalNodes][totalNodes];
    for (int i = 0; i < matrix.length; i++) {
      Arrays.fill(matrix[i], -1);
      matrix[i][i] = 0;
    }

    int totalEdges = random.nextInt(totalNodes);
    for (int i = 0; i < totalEdges; i++) {
      int from = random.nextInt(totalNodes);
      int to = random.nextInt(totalNodes);
      int weight = random.nextInt(RANDOM_WEIGHT_BOUNDS);
      matrix[from][to] = weight;
      matrix[to][from] = weight;
    }

    return matrix;
  }

  private void runDemo(Graph graph) {
    printers.forEach(this::runPrinter);
    searchAlgorithms.forEach(algo -> runSearchAlgorithm(graph, algo));
  }

  private void runSearchAlgorithm(Graph graph, GraphAlgorithm algo) {
    int totalNodes = graph.totalNodes();
    PrintWriter out = new PrintWriter(outputStreamForAlgorithmDemo(algo));

    for (int from = 0; from < totalNodes; from++) {
      for (int to = 0; to < totalNodes; to++) {
        if (from == to) {
          continue;
        }

        List<Integer> route = algo.findShortestPath(graph, from, to, true);
        out.printf("%d->%d: %d %s%n", from, to, graph.calcPathLength(route), Arrays.toString(route.toArray()));
        out.flush();
      }
    }
    out.close();
  }

  private void runPrinter(GraphPrinter printer) {
    try {
      printer.print(graph, outputStreamForPrinter(printer));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private File getOutputFile(String directory, String fileName) {
    File file = new File(directory, fileName.toLowerCase().replace(" ", "_") + ".txt");
    if (!file.getParentFile().exists()) {
      if (!file.getParentFile().mkdirs()) {
        throw new RuntimeException("Failed to create output directory");
      }
    }
    return file;
  }

  private OutputStream outputStreamForAlgorithmDemo(GraphAlgorithm algorithm) {
    try {
      return new FileOutputStream(getOutputFile(ALGORITHM_OUTPUT_DIRECTORY, algorithm.name()));
    } catch(FileNotFoundException e) {
      throw new RuntimeException("Output file does not exist", e);
    }
  }

  private OutputStream outputStreamForPrinter(GraphPrinter printer) {
    try {
      return new FileOutputStream(getOutputFile(PRINTER_OUTPUT_DIRECTORY, printer.name()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Output file does not exist", e);
    }
  }
}
