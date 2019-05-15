package com.nikitavbv.assignments.algorithms.lab3;

import static spark.Spark.get;

import com.nikitavbv.assignments.algorithms.lab3.btree.BTree;
import com.nikitavbv.assignments.algorithms.lab3.btree.BTreeNode;
import com.nikitavbv.assignments.algorithms.lab3.btree.KeyAlreadyExistsException;
import com.nikitavbv.assignments.algorithms.lab3.btree.KeyNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class Lab3 {

  public static void main(String[] args) {
    bootstrapWebUI();

    BTree bTree = new BTree(250, 0, new File("data/btree/first"));

    for (int i = 0; i < 10000; i++) {
      bTree.addKeys(i);
    }

    bTree.getRootNode().comparisons = 0;
    Random random = new Random();
    for (int i = 0; i < 15; i++) {
      bTree.getRootNode().comparisons = 0;
      bTree.getData(random.nextInt(10000));
      System.out.println(i + "\t" + bTree.getRootNode().comparisons);
    }
  }

  private static void bootstrapWebUI() {
    BTree webTree = new BTree(3, 100, new File("data/btree/web"));
    get("/", (req, res) -> getFileContents("index.html"));
    get("/app.css", (req, res) -> {
      res.type("text/css");
      return getFileContents("app.css");
    });
    get("/app.js", (req, res) -> getFileContents("app.js"));

    get("/api/insert", (req, res) -> {
      try {
        int key = Integer.parseInt(req.queryParams("key"));
        byte[] value = req.queryParams("value").getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(value.length + 4);
        buffer.putInt(value.length);
        buffer.put(value);
        System.out.println("Inserting: " + key + " -> " + req.queryParams("value"));
        webTree.insertData(key, buffer.array());
        return "ok";
      } catch(KeyAlreadyExistsException ke) {
        return "key already exists";
      } catch(Exception e) {
        e.printStackTrace();
        return "internal error";
      }
    });

    get("/api/search", (req, res) -> {
      try {
        int key = Integer.parseInt(req.queryParams("key"));
        System.out.println("Searching: " + key);
        byte[] result = webTree.getData(key);
        if (result == null) {
          return "not found";
        } else {
          ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
          sizeBuffer.put(new byte[]{result[0], result[1], result[2], result[3]});
          sizeBuffer.position(0);
          int size = sizeBuffer.getInt();
          byte[] data = new byte[size];
          System.arraycopy(result, 4, data, 0, data.length);
          return "result:" + (new String(data));
        }
      } catch (Exception e) {
        e.printStackTrace();
        return "internal error";
      }
    });

    get("/api/delete", (req, res) -> {
      try {
        int key = Integer.parseInt(req.queryParams("key"));
        System.out.println("Deleting: " + key);
        webTree.removeKey(key);
        return "ok";
      } catch(KeyNotFoundException nf) {
        return "not found";
      } catch (Exception e) {
        e.printStackTrace();
        return "internal error";
      }
    });

    get("/api/update", (req, res) -> {
      try {
        int key = Integer.parseInt(req.queryParams("key"));
        byte[] data = req.queryParams("value").getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(data.length + 4);
        buffer.putInt(data.length);
        buffer.put(data);
        System.out.println("Update: " + key + "->" + req.queryParams("value"));
        webTree.updateData(key, buffer.array());
        return "ok";
      } catch (KeyNotFoundException nf) {
        return "not found";
      } catch(Exception e) {
        e.printStackTrace();
        return "internal error";
      }
    });

    get("/api/list", (req, res) -> {
      try {
        return webTree.getRootNode().getKeysAndDataRecursive().stream().map(pair -> {
          byte[] pairData = pair.value();
          ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
          sizeBuffer.put(new byte[]{pairData[0], pairData[1], pairData[2], pairData[3]});
          sizeBuffer.position(0);
          int size = sizeBuffer.getInt();
          byte[] data = new byte[size];
          System.arraycopy(pairData, 4, data, 0, data.length);
          return pair.key() + ";" + new String(data);
        }).collect(Collectors.joining("\n"));
      } catch (Exception e) {
        e.printStackTrace();
        return "internal error";
      }
    });

    get("/api/render", (req, res) -> {
      try {
        return "\n" +
                "<!doctype html><html class=\"no-js\" lang=\"\"><head><meta charset=\"utf-8\">" +
                "<meta http-equiv=\"x-ua-compatible\" content=\"ie-edge\"><title>B-Tree</title>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\" />" +
                "<link rel=\"stylesheet\" href=\"/app.css\" />" +
                "<link href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,400\" rel=\"stylesheet\"></head>" +
                "<body><div class=\"btree-render\">" +
                webTree.getRootNode().renderToHtml(true) +
                "</div></body><script type=\"application/javascript\" src=\"/app.js\"></script></html>";
      } catch(Exception e) {
        e.printStackTrace();
        return "internal error";
      }
    });
  }

  private static String getFileContents(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(new File("data/btree_static", fileName)));
      StringBuffer result = new StringBuffer();
      String line = reader.readLine();
      while (line != null) {
        result.append(line).append("\n");
        line = reader.readLine();
      }
      reader.close();
      return result.toString();
    } catch (IOException e) {
      throw new RuntimeException("Failed to read static file", e);
    }
  }

}
