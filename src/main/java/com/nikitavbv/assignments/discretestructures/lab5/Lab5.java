package com.nikitavbv.assignments.discretestructures.lab5;

import com.nikitavbv.assignments.discretestructures.lab5.huffmanTree.HuffmanTree;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

public class Lab5 {

  private static final String TEST_TEXT_FILE = "data/victory_unintentional.txt";
  private static final String CHART_IMAGES_PATH = "./reports/discreteStructures/lab5/images/";

  public static void main(String[] args) throws IOException {
    String text = readInputText();

    Map<Double, Double> treeHeightSeries = new HashMap<>();
    Map<Double, Double> averageHeightSeries = new HashMap<>();

    for (int i = 10; i <= 3000; i+=10) {
      String subText = text.substring(0, i);
      HuffmanTree tree = HuffmanTree.withFrequenciesFromText(subText);
      tree.runHuffmanAlgorithm();
      String encodedSubText = tree.encodeText(subText);
      String decodedText = tree.decodeText(encodedSubText);
      if (!subText.equals(decodedText)) {
        throw new AssertionError("Decoded text does not match the source");
      }
      int treeHeight = tree.getTreeHeight();
      float averageHeight = (float) encodedSubText.length() / i;

      if (i == 500 || i == 1000 || i == 3000) {
        System.out.println("---------------------------------------------------------");
        System.out.printf("Frequencies table for i=%d%n", i);
        tree.printFrequenciesTableTo(System.out);
        System.out.printf("Code table for i=%d%n", i);
        tree.printInfix(System.out);
        System.out.println();
        System.out.printf("Tree height: %d, average height: %f%n", treeHeight, averageHeight);
      }

      treeHeightSeries.put((double) i, (double) treeHeight);
      averageHeightSeries.put((double) i, (double) averageHeight);
    }

    makeChart(treeHeightSeries, "Huffman tree height", CHART_IMAGES_PATH + "1.png");
    makeChart(averageHeightSeries, "Average height", CHART_IMAGES_PATH + "2.png");
  }

  private static String readInputText() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File(TEST_TEXT_FILE)));
    StringBuilder result = new StringBuilder();

    String line = reader.readLine();
    while (line != null) {
      if (result.length() > 0) {
        result.append('\n');
      }
      result.append(line);
      line = reader.readLine();
    }

    reader.close();
    return result.toString();
  }

  private static void makeChart(Map<Double, Double> treeHeightSeries, String yAxis, String imagePath)
          throws IOException {
    final XYChart chart = new XYChartBuilder()
            .width(400)
            .height(300)
            .title(yAxis + " vs. text length")
            .xAxisTitle("Text length")
            .yAxisTitle(yAxis)
            .theme(Styler.ChartTheme.GGPlot2)
            .build();

    chart.getStyler().setChartBackgroundColor(Color.WHITE);
    chart.getStyler().setPlotBorderVisible(false);
    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);

    double[] charactersNumber = treeHeightSeries.keySet().stream().sorted().mapToDouble(i -> i).toArray();

    chart.addSeries(yAxis, charactersNumber, Arrays.stream(charactersNumber)
            .map(treeHeightSeries::get).toArray());

    BitmapEncoder.saveBitmap(chart, imagePath, BitmapEncoder.BitmapFormat.PNG);

    new SwingWrapper<>(chart).displayChart();
  }


}
