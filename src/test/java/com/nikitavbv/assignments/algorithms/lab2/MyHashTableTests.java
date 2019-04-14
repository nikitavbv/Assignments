package com.nikitavbv.assignments.algorithms.lab2;

import static org.junit.Assert.assertEquals;

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

}
