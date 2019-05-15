package com.nikitavbv.assignments.algorithms.lab3.btree;

public class KeyAlreadyExistsException extends RuntimeException {

  private final int key;

  KeyAlreadyExistsException(int key) {
    this.key = key;
  }

}
