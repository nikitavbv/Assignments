package com.nikitavbv.assignments.discretestructures.lab5.huffmanTree;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.io.IOException;
import org.junit.Test;

public class HuffmanTreeTest {

  private static final String DUMMY_TEXT = "Your time is limited, so don't waste it living someone else's life. " +
          "Don't be trapped by dogma – which is living with the results of other people's thinking. Don't " +
          "let the noise of others' opinions drown out your own inner voice. And, most important, have the " +
          "courage to follow your heart and intuition. They somehow already know what you truly want to " +
          "become. Everything else is secondary.\n";

  @Test
  public void testBuildingFrequencyTable() {
    HuffmanTree tree = HuffmanTree.withFrequenciesFromText(DUMMY_TEXT);
    assertTrue(tree.getCharacterFrequency('a') > tree.getCharacterFrequency('v'));
    assertTrue(tree.getCharacterFrequency(' ') > tree.getCharacterFrequency('a'));
  }

  @Test
  public void testRunHuffmanAlgorithm() throws IOException {
    HuffmanTree tree = HuffmanTree.withFrequenciesFromText(DUMMY_TEXT);
    tree.runHuffmanAlgorithm();
    assertTrue(tree.encode('a').length() < tree.encode('v').length());
    assertTrue(tree.encode(' ').length() <= tree.encode('a').length());

    assertEquals(' ', tree.decode(tree.encode(' ')));
    assertEquals('a', tree.decode(tree.encode('a')));
    assertEquals('v', tree.decode(tree.encode('v')));

    assertTrue(tree.getCodeWeight() < DUMMY_TEXT.length() * 8);
  }

}
