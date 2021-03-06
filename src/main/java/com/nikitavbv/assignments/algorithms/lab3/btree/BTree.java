package com.nikitavbv.assignments.algorithms.lab3.btree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class BTree {

  static final int KEY_LENGTH = 4; // bytes
  static final int POINTER_LENGTH = 4; // bytes

  private final int t;
  private final int dataEntryLength;
  private final File treeDirectoryFile;

  private int nodeIDCounter = 1;
  private int rootNodeID;
  private BTreeNode root;

  public BTree(int t, int dataEntryLength, File treeDirectoryFile) {
    this(t, dataEntryLength, treeDirectoryFile, 1);
  }

  public BTree(int t, int dataEntryLength, File treeDirectoryFile, int rootNodeID) {
    this.t = t;
    this.dataEntryLength = dataEntryLength;
    this.treeDirectoryFile = treeDirectoryFile;
    this.rootNodeID = rootNodeID;
    this.root = getNodeByID(rootNodeID);
    this.load();
  }

  BTreeNode createNewNode() {
    return createNewNode(new byte[getRowLength()], 0);
  }

  BTreeNode createNewNode(byte[] data, int totalKeys) {
    nodeIDCounter++;
    this.save();
    return new BTreeNode(this, nodeIDCounter, data, totalKeys);
  }

  private void load() {
    try {
      File meta = getMetaFile();
      if (!meta.exists()) {
        return;
      }
      BufferedReader reader = new BufferedReader(new FileReader(meta));
      this.nodeIDCounter = Integer.parseInt(reader.readLine());
      this.rootNodeID = Integer.parseInt(reader.readLine());
      this.root = getNodeByID(this.rootNodeID);
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void save() {
    try {
      File treeFile = getMetaFile();
      PrintWriter pw = new PrintWriter(new FileWriter(treeFile));
      pw.write(Integer.toString(nodeIDCounter));
      pw.write("\n");
      pw.write(Integer.toString(rootNodeID));
      pw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  BTreeNode getNodeByID(int nodeID) {
    BTreeNode node = new BTreeNode(this, nodeID);
    File nodeFile = getNodeFile(nodeID);
    if (nodeFile.exists()) {
      try {
        node.load(new FileInputStream(nodeFile));
      } catch(FileNotFoundException e) {
        throw new AssertionError("Node file not found");
      }
    }
    return node;
  }

  OutputStream getOutputStreamForNode(int nodeID) {
    try {
      return new FileOutputStream(getNodeFile(nodeID));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new AssertionError("Output file for node is not found");
    }
  }

  void deleteNode(int nodeID) {
    if (!getNodeFile(nodeID).delete()) {
      throw new AssertionError("Failed to delete node file: " + nodeID);
    }
  }

  private File getMetaFile() {
    return new File(getTreeDirectoryFile(), "meta");
  }

  private File getNodeFile(int nodeID) {
    return new File(getTreeDirectoryFile(), "node_" + nodeID);
  }

  private File getTreeDirectoryFile() {
    if (!treeDirectoryFile.exists()) {
      if (!treeDirectoryFile.mkdirs()) {
        throw new RuntimeException("Failed to create directory for this tree");
      }
    }
    return treeDirectoryFile;
  }

  public void addKeys(int... keys) {
    Arrays.stream(keys).forEach(this::insertData);
  }

  public byte[] getData(int key) {
    return root.findDataForKey(key);
  }

  public void updateData(int key, byte[] data) {
    root.updateData(key, data);
  }

  public void insertData(int key) {
    insertData(key, new byte[dataEntryLength]);
  }

  public void insertData(int key, byte[] data) {
    root.insertData(key, data);
    if (root.getTotalKeys() == 2 * t - 1) {
      // split root node
      BTreeNode first = root.cloneLeftPart();
      BTreeNode second = root.cloneRightPart();
      int medianKey = root.getKeyAtPosition(t - 1);
      byte[] medianKeyData = root.getDataAtPosition(t - 1);
      first.save();
      second.save();

      BTreeNode newRoot = createNewNode();
      newRoot.writeInt(0, first.getNodeID());
      newRoot.writeKeyAtPosition(0, medianKey);
      newRoot.writeDataAtPosition(0, medianKeyData);
      newRoot.writeInt(POINTER_LENGTH + KEY_LENGTH + dataEntryLength, second.getNodeID());
      newRoot.setLeaf(false);
      newRoot.setTotalKeys(1);
      newRoot.save();

      if(!getNodeFile(root.getNodeID()).delete()) {
        System.err.println("Failed to delete old node file");
      }

      root = newRoot;
    }
  }

  public void removeKey(int key) {
    if (root.isLeaf()) {
      root.removeKey(key);
    } else {
      root.findAndRemoveKey(key);
    }
  }

  int getParameter() {
    return t;
  }

  int getDataEntryLength() {
    return dataEntryLength;
  }

  int getRowLength() {
    return (KEY_LENGTH + dataEntryLength) * (2 * t - 1) + POINTER_LENGTH * 2 * t;
  }

  public BTreeNode getRootNode() {
    return root;
  }
}
