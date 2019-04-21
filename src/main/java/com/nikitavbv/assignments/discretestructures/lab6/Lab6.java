package com.nikitavbv.assignments.discretestructures.lab6;

import com.nikitavbv.assignments.discretestructures.lab4.avltree.AVLTree;
import com.nikitavbv.assignments.discretestructures.lab6.rbtree.RBTree;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

public class Lab6 {

  private static class AVLTreeMetrics {
    private int totalLeftRotates = 0;
    private int totalRightRotates = 0;

    AVLTreeMetrics(int totalLeftRotates, int totalRightRotates) {
      this.totalLeftRotates = totalLeftRotates;
      this.totalRightRotates = totalRightRotates;
    }

    private int getTotalLeftRotates() {
      return totalLeftRotates;
    }

    private int getTotalRightRotates() {
      return totalRightRotates;
    }
  }

  private static class RBTreeMetrics {
    private int totalSingleTurns = 0;
    private int totalDoubleTurns = 0;

    RBTreeMetrics(int totalSingleTurns, int totalDoubleTurns) {
      this.totalSingleTurns = totalSingleTurns;
      this.totalDoubleTurns = totalDoubleTurns;
    }

    private int getTotalSingleTurns() {
      return totalSingleTurns;
    }

    private int getTotalDoubleTurns() {
      return totalDoubleTurns;
    }
  }

  private static final int CHART_WIDTH = 800;
  private static final int CHART_HEIGHT = 600;
  private static final String CHART_IMAGE_PATH = "./reports/discreteStructures/lab6/images/";
  private static final String CHART_AVL_TREE_IMAGE_PATH = CHART_IMAGE_PATH + "1";
  private static final String CHART_RB_TREE_IMAGE_PATH = CHART_IMAGE_PATH + "2";

  public static void main(String[] args) throws IOException {
    List<Integer> treeSizes = new ArrayList<>();
    Map<Integer, AVLTreeMetrics> avlTreeMetricsMap = new HashMap<>() ;
    Map<Integer, RBTreeMetrics> rbTreeMetricsMap = new HashMap<>();
    for (int k = 1000; k <= 10000; k+=1000) {
      avlTreeMetricsMap.put(k, runAVLTreeTest(k));
      rbTreeMetricsMap.put(k, runRBTreeTest(k));
      treeSizes.add(k);
    }
    printTable(treeSizes, avlTreeMetricsMap, rbTreeMetricsMap);
    makeChart(treeSizes, avlTreeMetricsMap, rbTreeMetricsMap);
  }

  private static void printTable(List<Integer> treeSizes, Map<Integer, AVLTreeMetrics> avlTreeMetricsMap,
                                 Map<Integer, RBTreeMetrics> rbTreeMetricsMap) {
    System.out.printf("%15s | %20s | %20s | %20s | %20s%n", "size", "AVL Tree left r.", "AVL Tree right. r.",
            "RB Tree single r.", "RB Tree double r.");
    treeSizes.forEach(size -> System.out.printf("%15d | %20d | %20d | %20d | %20d%n", size,
            avlTreeMetricsMap.get(size).getTotalLeftRotates(), avlTreeMetricsMap.get(size).getTotalRightRotates(),
            rbTreeMetricsMap.get(size).getTotalSingleTurns(), rbTreeMetricsMap.get(size).getTotalDoubleTurns()));
  }

  private static void makeChart(List<Integer> treeSizes, Map<Integer, AVLTreeMetrics> avlTreeMetricsMap,
                                Map<Integer, RBTreeMetrics> rbTreeMetricsMap) throws IOException {
    final XYChart avlTreeChart = new XYChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("AVL Tree")
            .xAxisTitle("Tree size")
            .yAxisTitle("Number of turns")
            .theme(Styler.ChartTheme.GGPlot2)
            .build();

    avlTreeChart.getStyler().setChartBackgroundColor(Color.WHITE);
    avlTreeChart.getStyler().setPlotBorderVisible(false);
    avlTreeChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

    int[] treeSizesArr = treeSizes.stream().mapToInt(i -> i).toArray();
    avlTreeChart.addSeries("AVL Tree left rotates", treeSizesArr, treeSizes.stream().map(avlTreeMetricsMap::get)
            .mapToInt(AVLTreeMetrics::getTotalLeftRotates).toArray());
    avlTreeChart.addSeries("AVL Tree right rotates", treeSizesArr, treeSizes.stream().map(avlTreeMetricsMap::get)
            .mapToInt(AVLTreeMetrics::getTotalRightRotates).toArray());

    final XYChart rbTreeChart = new XYChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("RB Tree")
            .xAxisTitle("Tree size")
            .yAxisTitle("Number of turns")
            .theme(Styler.ChartTheme.GGPlot2)
            .build();

    rbTreeChart.getStyler().setChartBackgroundColor(Color.WHITE);
    rbTreeChart.getStyler().setPlotBorderVisible(false);
    rbTreeChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

    rbTreeChart.addSeries("RB Tree single rotates", treeSizesArr, treeSizes.stream().map(rbTreeMetricsMap::get)
            .mapToInt(RBTreeMetrics::getTotalSingleTurns).toArray());
    rbTreeChart.addSeries("RB Tree double rotates", treeSizesArr, treeSizes.stream().map(rbTreeMetricsMap::get)
            .mapToInt(RBTreeMetrics::getTotalDoubleTurns).toArray());

    BitmapEncoder.saveBitmap(avlTreeChart, CHART_AVL_TREE_IMAGE_PATH, BitmapEncoder.BitmapFormat.PNG);
    BitmapEncoder.saveBitmap(rbTreeChart, CHART_RB_TREE_IMAGE_PATH, BitmapEncoder.BitmapFormat.PNG);

    new SwingWrapper<>(avlTreeChart).displayChart();
    new SwingWrapper<>(rbTreeChart).displayChart();
  }

  private static AVLTreeMetrics runAVLTreeTest(int k) {
    AVLTree<Integer> tree = new AVLTree<>();
    IntStream.range(1, k+1).forEach(tree::add);
    return new AVLTreeMetrics(tree.getTotalLeftRotates(), tree.getTotalRightRotates());
   }

  private static RBTreeMetrics runRBTreeTest(int k) {
    RBTree<Integer> tree = new RBTree<>();
    IntStream.range(1, k+1).forEach(tree::insert);
    return new RBTreeMetrics(tree.getTotalSingleTurns(), tree.getTotalDoubleTurns());
  }

}
