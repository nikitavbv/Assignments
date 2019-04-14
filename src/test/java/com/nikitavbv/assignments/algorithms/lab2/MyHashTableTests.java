package com.nikitavbv.assignments.algorithms.lab2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class MyHashTableTests {

  @Test
  public void testAdd() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    myHashTable.put("foobar", 42);
    myHashTable.put("Aa", 1);
    myHashTable.put("BB", 2); // cause collision
    assertEquals(42, myHashTable.get("foobar").get().intValue());
    assertEquals(1, myHashTable.get("Aa").get().intValue());
    assertEquals(2, myHashTable.get("BB").get().intValue());
  }

  @Test
  public void testResize() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>(2, true);
    myHashTable.put("foobar", 42);
    myHashTable.put("Aa", 1);
    myHashTable.put("BB", 2); // cause collision
    myHashTable.put("hello", 5);
    myHashTable.put("world", 5);
    myHashTable.put("hello world", 10);

    assertEquals(6, myHashTable.getSize());
    assertEquals(42, myHashTable.get("foobar").get().intValue());
    assertEquals(1, myHashTable.get("Aa").get().intValue());
    assertEquals(2, myHashTable.get("BB").get().intValue());
    assertEquals(5, myHashTable.get("hello").get().intValue());
    assertEquals(5, myHashTable.get("world").get().intValue());
    assertEquals(10, myHashTable.get("hello world").get().intValue());
  }

  @Test
  public void testRemove() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    myHashTable.put("foobar", 42);
    myHashTable.put("Aa", 1);
    myHashTable.put("BB", 2); // cause collision
    myHashTable.remove("BB");
    assertFalse(myHashTable.get("BB").isPresent());
    myHashTable.remove("Aa");
    assertFalse(myHashTable.get("Aa").isPresent());
    myHashTable.remove("foobar");
    assertFalse(myHashTable.get("foobar").isPresent());
  }

}
