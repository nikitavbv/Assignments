package com.nikitavbv.assignments.algorithms.lab3.btree;

import static com.nikitavbv.assignments.algorithms.lab3.btree.BTree.KEY_LENGTH;
import static com.nikitavbv.assignments.algorithms.lab3.btree.BTree.POINTER_LENGTH;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BTreeNode {

  private final BTree tree;
  private final int nodeID;
  private final int t;
  private final int dataEntryLength;

  byte[] row;
  private int totalKeys = 0;
  private boolean isLeaf = true;

  static int a = 0;
  static int b = 0;

  //public int comparisons = 0;

  public BTreeNode(BTree tree, int nodeID, byte[] data, int totalKeys) {
    if (data.length != tree.getRowLength()) {
      throw new IllegalArgumentException("Data length is not valid for this tree");
    }
    this.tree = tree;
    this.t = tree.getParameter();
    this.dataEntryLength = tree.getDataEntryLength();
    this.nodeID = nodeID;
    this.row = data;
    this.totalKeys = totalKeys;
  }

  public BTreeNode(BTree tree, int nodeID) {
    this(tree, nodeID, new byte[tree.getRowLength()], 0);
  }

  void load(InputStream inputStream) {
    try {
      byte[] totalKeysBytes = new byte[4];
      if (inputStream.read(totalKeysBytes) != totalKeysBytes.length) {
        throw new AssertionError("Failed to read total keys number");
      }
      ByteBuffer buffer = ByteBuffer.allocate(totalKeysBytes.length);
      buffer.put(totalKeysBytes);
      buffer.position(0);
      totalKeys = buffer.getInt();

      int bytesRead = inputStream.read(row);
      if (bytesRead < row.length) {
        throw new AssertionError("Node bytes read less than expected");
      }
      inputStream.close();

      isLeaf = true;
      for (int i = 0; i < row.length; i += KEY_LENGTH + dataEntryLength + POINTER_LENGTH) {
        if (getInt(i) != 0) {
          isLeaf = false;
          System.out.print("Node is not a leaf: " + nodeID + " because of link to " + getInt(i));
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void save() {
    try {
      ByteBuffer output = ByteBuffer.allocate(4 + row.length);
      output.putInt(totalKeys);
      output.put(row);

      OutputStream out = tree.getOutputStreamForNode(nodeID);
      out.write(output.array());
      out.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void insertData(int key, byte[] data) {
    int pos = findInsertPosition(key);

    if (!isLeaf()) {
      int childNodeID = getChildNodeIDAtPos(pos);
      BTreeNode childNode = tree.getNodeByID(childNodeID);
      childNode.insertData(key, data);

      if (childNode.totalKeys == 2 * t - 1) {
        BTreeNode first = childNode.cloneLeftPart();
        BTreeNode second = childNode.cloneRightPart();
        int medianKey = childNode.getKeyAtPosition(t - 1);
        byte[] medianKeyData = childNode.getDataAtPosition(t - 1);
        first.save();
        second.save();

        this.shiftRowAtPos(pos);
        writeKeyAtPosition(pos, medianKey);
        writeDataAtPosition(pos, medianKeyData);
        setChildNodeIDAtPos(pos, first.nodeID);
        setChildNodeIDAtPos(pos + 1, second.nodeID);

        this.totalKeys++;
        isLeaf = false;
        this.save();
        tree.deleteNode(childNode.nodeID);
      }
      return;
    }

    this.shiftRowAtPos(pos);
    writeKeyAtPosition(pos, key);
    writeDataAtPosition(pos, data);
    this.totalKeys++;
    this.save();
  }

  BTreeNode cloneLeftPart() {
    int partsLen = getPosStartByte(t - 1);
    byte[] firstRow = new byte[row.length];
    System.arraycopy(row, 0, firstRow, 0, partsLen);
    return tree.createNewNode(firstRow, Math.min(totalKeys, t - 1));
  }

  BTreeNode cloneRightPart() {
    int partsLen = getPosStartByte(t - 1);
    byte[] secondRow = new byte[row.length];
    System.arraycopy(row, getPosStartByte(t), secondRow, POINTER_LENGTH, partsLen - POINTER_LENGTH);
    return tree.createNewNode(secondRow, Math.max(totalKeys - t, 0));
  }

  private void shiftRowAtPos(int pos) {
    if (totalKeys == 0) {
      return;
    }
    byte[] newRow = new byte[row.length];
    System.arraycopy(row, 0, newRow, 0, getPosStartByte(pos));
    System.arraycopy(row, getPosStartByte(pos), newRow, getPosStartByte(pos + 1), getPosStartByte(totalKeys) - getPosStartByte(pos));
    this.row = newRow;
  }

  int findInsertPosition(int key) {
    if (totalKeys == 0) {
      return 0;
    } else if (totalKeys == 1) {
      if (getKeyAtPosition(0) < key) {
        return 1;
      } else if (getKeyAtPosition(0) > key) {
        return 0;
      } else {
        throw new KeyAlreadyExistsException(key);
      }
    }

    int begin = 0;
    int last = totalKeys;
    int cursor;

    while (begin <= last) {
      cursor = (begin + last) / 2;
      int cursorKey = getKeyAtPosition(cursor);

      if (cursorKey < key && (cursor + 1 == totalKeys || getKeyAtPosition(cursor + 1) > key)) {
        return cursor + 1;
      } else if (cursorKey < key) {
        begin = cursor + 1;
      } else if (cursorKey > key) {
        last = cursor - 1;
      } else if (cursorKey == key) {
        throw new KeyAlreadyExistsException(key);
      }
    }

    return 0;
  }

  private int findKeyInThisNode(int key) {
    int begin = 0;
    int last = totalKeys;
    int cursor;

    while (begin <= last) {
      cursor = (begin + last) / 2;
      int cursorKey = getKeyAtPosition(cursor);

      if (cursorKey < key) {
        begin = cursor + 1;
      } else if (cursorKey > key) {
        last = cursor - 1;
      } else if (cursorKey == key) {
        return  cursor;
      }
    }

    return -1;
  }

  void findAndRemoveKey(int key) {
    int pos = findInsertPosition(key);
    int childNodeID = getChildNodeIDAtPos(pos);
    BTreeNode childNode = tree.getNodeByID(childNodeID);
    if (childNode.isLeaf()) {
      childNode.removeKey(key);
    } else if (childNode.containsKey(key)) {
      int posInNode = childNode.findKeyInThisNode(key);
      BTreeNode prevNode = tree.getNodeByID(childNode.getChildNodeIDAtPos(posInNode));
      if (prevNode.totalKeys >= t) {
        int prevValue = prevNode.findClosestSmallerTo(key);
        childNode.writeKeyAtPosition(posInNode, prevValue);
        childNode.writeDataAtPosition(posInNode, childNode.findDataForKey(prevValue));
        prevNode.findAndRemoveKey(prevValue);
      } else {
        BTreeNode nextNode = tree.getNodeByID(childNode.getChildNodeIDAtPos(posInNode+1));
        if (nextNode.totalKeys >= t) {
          int nextValue = prevNode.findClosestBiggerTo(key);
          childNode.writeKeyAtPosition(posInNode, nextValue);
          childNode.writeDataAtPosition(posInNode, childNode.findDataForKey(nextValue));
          nextNode.findAndRemoveKey(nextValue);
        } else {
          byte[] data = childNode.findDataForKey(key);
          childNode.removeKey(key);
          prevNode.mergeFrom(nextNode);
          prevNode.insertData(key, data);
          prevNode.findAndRemoveKey(key);
        }
      }
    } else {
      int insPos = childNode.findInsertPosition(key);
      BTreeNode subTreeRoot = tree.getNodeByID(childNode.getChildNodeIDAtPos(childNode.findInsertPosition(insPos)));

      if (subTreeRoot.totalKeys < t) {
        BTreeNode leftNeighbour = tree.getNodeByID(childNode.getChildNodeIDAtPos(insPos - 1));
        BTreeNode rightNeighbour = tree.getNodeByID(childNode.getChildNodeIDAtPos(insPos + 1));
        if (leftNeighbour.totalKeys >= t) {
          subTreeRoot.insertData(childNode.getKeyAtPosition(insPos-1), childNode.getDataAtPosition(insPos-1));
          int leftNeighbourBiggestKey = leftNeighbour.getKeyAtPosition(leftNeighbour.totalKeys - 1);
          byte[] lnData = leftNeighbour.getDataAtPosition(leftNeighbour.totalKeys - 1);
          subTreeRoot.writeKeyAtPosition(insPos - 1, leftNeighbourBiggestKey);
          subTreeRoot.writeDataAtPosition(insPos - 1, lnData);
          subTreeRoot.writeInt(childNode.getPosStartByte(insPos - 1) + KEY_LENGTH + dataEntryLength,
                  leftNeighbour.getPosStartByte(leftNeighbour.totalKeys - 1) - (KEY_LENGTH + dataEntryLength));
          leftNeighbour.removeKey(leftNeighbourBiggestKey);
        } else if (rightNeighbour.totalKeys >= t) {
          subTreeRoot.insertData(childNode.getKeyAtPosition(insPos+1), childNode.getDataAtPosition(insPos+1));
          int rightNeighbourSmallestKey = rightNeighbour.getKeyAtPosition(rightNeighbour.totalKeys - 1);
          byte[] rnData = rightNeighbour.getDataAtPosition(0);
          subTreeRoot.writeKeyAtPosition(insPos - 1, rightNeighbourSmallestKey);
          subTreeRoot.writeDataAtPosition(insPos - 1, rnData);
          subTreeRoot.writeInt(childNode.getPosStartByte(insPos - 1) + KEY_LENGTH + dataEntryLength,
                  leftNeighbour.getPosStartByte(0));
          leftNeighbour.removeKey(rightNeighbourSmallestKey);
        } else {
          subTreeRoot.mergeFrom(rightNeighbour);
        }
      }

      childNode.findAndRemoveKey(key);
    }
  }

  byte[] findDataForKey(int key) {
    int begin = 0;
    int last = totalKeys;
    int cursor;

    while (begin <= last) {
      cursor = (begin + last) / 2;
      int cursorKey = getKeyAtPosition(cursor);
      //comparisons++;
      if (cursorKey < key && (cursor + 1 == totalKeys || getKeyAtPosition(cursor + 1) > key)) {
        BTreeNode node = tree.getNodeByID(getChildNodeIDAtPos(cursor + 1));
        byte[] r = node.findDataForKey(key);
        //this.comparisons+=node.comparisons;
        return r;
      } else if (cursorKey < key) {
        begin = cursor + 1;
      } else if (cursorKey > key) {
        last = cursor - 1;
      } else if (cursorKey == key) {
        return getDataAtPosition(cursor);
      }
    }

    return null;
  }

  private int findClosestBiggerTo(int key) {
    int biggest = key;
    for (int i = totalKeys - 1; i >= 0; i--) {
      int subBiggest = tree.getNodeByID(getChildNodeIDAtPos(i)).findClosestBiggerTo(key);
      if (subBiggest > biggest) {
        biggest = subBiggest;
      }
      if (getKeyAtPosition(i) > biggest) {
        biggest = getKeyAtPosition(i);
      }
      if (getKeyAtPosition(i) < key && (i == 0 || getKeyAtPosition(i-1) < key)) {
        break;
      }
    }
    return biggest;
  }

  private int findClosestSmallerTo(int key) {
    int smallest = key;
    for (int i = 0; i < totalKeys; i++) {
      int subSmallest = tree.getNodeByID(getChildNodeIDAtPos(i)).findClosestSmallerTo(key);
      if (subSmallest < smallest) {
        smallest = subSmallest;
      }
      if (getKeyAtPosition(i) < smallest) {
        smallest = getKeyAtPosition(i);
      }
      if (getKeyAtPosition(i) > smallest && getKeyAtPosition(i - 1) > smallest) {
        break;
      }
    }
    return smallest;
  }

  private void mergeFrom(BTreeNode origin) {
    List<BTreeNode> nodesToMove = new ArrayList<>();
    nodesToMove.add(origin);
    while (nodesToMove.size() > 0) {
      BTreeNode nodeToMove = nodesToMove.remove(0);
      for (int i = 0; i < nodeToMove.totalKeys; i++) {
        int keyToMove = nodeToMove.getKeyAtPosition(i);
        byte[] dataToMove = nodeToMove.getDataAtPosition(i);
        insertData(keyToMove, dataToMove);
        if (nodeToMove.getChildNodeIDAtPos(i) != 0) {
          nodesToMove.add(tree.getNodeByID(nodeToMove.getChildNodeIDAtPos(i)));
        }
      }
    }
  }

  private boolean containsKey(int key) {
    return findKeyInThisNode(key) != -1;
  }

  void updateData(int key, byte[] data) {
    int pos = findKeyInThisNode(key);
    if (pos == -1) {
      if (isLeaf()) {
        throw new KeyNotFoundException(key);
      }
      pos = findInsertPosition(key);
      tree.getNodeByID(getChildNodeIDAtPos(pos)).updateData(key, data);
      return;
    }
    writeDataAtPosition(pos, data);
  }

  void removeKey(int key) {
    int pos = findKeyInThisNode(key);
    if (pos == -1) {
      throw new KeyNotFoundException(key);
    }

    byte[] newRow = new byte[row.length];
    System.arraycopy(row, 0, newRow, 0, getPosStartByte(pos));
    System.arraycopy(row, getPosStartByte(pos+1), newRow, getPosStartByte(pos), getPosStartByte(totalKeys) - getPosStartByte(pos+1));
    this.row = newRow;

    this.totalKeys--;

    this.save();
  }

  private int getChildNodeIDAtPos(int pos) {
    return getInt((POINTER_LENGTH + KEY_LENGTH + dataEntryLength) * pos);
  }

  private void setChildNodeIDAtPos(int pos, int nodeID) {
    writeInt((POINTER_LENGTH + KEY_LENGTH + dataEntryLength) * pos, nodeID);
  }

  int getKeyAtPosition(int pos) {
    return getInt(getPosStartByte(pos));
  }

  byte[] getDataAtPosition(int pos) {
    byte[] data = new byte[KEY_LENGTH + dataEntryLength];
    System.arraycopy(row, getPosStartByte(pos) + KEY_LENGTH, data, 0, data.length);
    return data;
  }

  void writeKeyAtPosition(int pos, int key) {
    writeInt(getPosStartByte(pos), key);
  }

  void writeDataAtPosition(int pos, byte[] data) {
    System.arraycopy(data, 0, row, getPosStartByte(pos) + KEY_LENGTH, data.length);
  }

  private int getInt(int offset) {
    ByteBuffer buffer = ByteBuffer.allocate(4);
    for (int i = 0; i < 4; i++) {
      buffer.put(row[offset + i]);
    }
    buffer.position(0);
    return buffer.getInt();
  }

  void writeInt(int offset, int value) {
    ByteBuffer buffer = ByteBuffer.allocate(4);
    buffer.putInt(value);
    for (int i = 0; i < 4; i++) {
      row[offset + i] = buffer.get(i);
    }
  }

  private int getPosStartByte(int pos) {
    return POINTER_LENGTH + pos * (KEY_LENGTH + dataEntryLength + POINTER_LENGTH);
  }

  public List<Pair<Integer, byte[]>> getKeysAndDataRecursive() {
    List<Pair<Integer, byte[]>> result = new ArrayList<>();
    for (int i = 0; i < totalKeys; i++) {
      result.add(new Pair<>(getKeyAtPosition(i), getDataAtPosition(i)));
      if (getChildNodeIDAtPos(i) != 0) {
        result.addAll(tree.getNodeByID(getChildNodeIDAtPos(i)).getKeysAndDataRecursive());
      }
    }
    if (getChildNodeIDAtPos(totalKeys) != 0) {
      result.addAll(tree.getNodeByID(getChildNodeIDAtPos(totalKeys)).getKeysAndDataRecursive());
    }
    return result;
  }

  public String renderToHtml(boolean root) {
    if (totalKeys == 0) {
      return "<div class=\"btree-node\">empty node</div>";
    }

    StringBuilder result = new StringBuilder("<div class=\"btree-node\"><div class=\"btree-node-keys\">");
    if (root) {
      result = new StringBuilder("<div class=\"btree-node-root\"><div class=\"btree-node-keys\">");
    }

    List<Integer> children = new ArrayList<>();

    if (totalKeys == 1) {
      result.append("<div class=\"btree-node-key btree-node-only-key\">").append(getKeyAtPosition(0)).append("</div>");
      children.add(getChildNodeIDAtPos(0));
    } else {
      for (int i = 0; i < totalKeys; i++) {
        if (i == 0) {
          result.append("<div class=\"btree-node-key btree-node-first-key\">");
        } else if (i == totalKeys - 1) {
          result.append("<div class=\"btree-node-key btree-node-last-key\">");
        } else {
          result.append("<div class=\"btree-node-key\">");
        }
        result.append(getKeyAtPosition(i)).append("</div>");
        children.add(getChildNodeIDAtPos(i));
      }
    }
    children.add(getChildNodeIDAtPos(totalKeys));
    result.append("</div><div class=\"btree-node-children\">");

    for (Integer child : children) {
      if (child == 0) {
        result.append("<div class=\"btree-node-empty\"></div>");
      } else {
        result.append(tree.getNodeByID(child).renderToHtml(false));
      }
    }

    result.append("</div></div>");
    return result.toString();
  }

  boolean isLeaf() {
    return isLeaf;
  }

  void setLeaf(boolean isLeaf) {
    this.isLeaf = isLeaf;
  }

  public int getTotalKeys() {
    return totalKeys;
  }

  void setTotalKeys(int totalKeys) {
    this.totalKeys = totalKeys;
  }

  int getNodeID() {
    return nodeID;
  }
}
