package com.nikitavbv.assignments.algorithms.lab5;

class Utils {

  static void repeat(int times, Runnable f) {
    for (int i = 0; i < times; i++) {
      f.run();
    }
  }

  static void repeat(int times, FunctionNoReturnOneArgument<Integer> f) {
    for (int i = 0; i < times; i++) {
      f.apply(i);
    }
  }

  interface FunctionNoReturnOneArgument<T> {
    public void apply(T arg);
  }
}
