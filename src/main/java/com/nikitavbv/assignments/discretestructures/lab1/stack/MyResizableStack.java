package com.nikitavbv.assignments.discretestructures.lab1.stack;

public class MyResizableStack<T> extends MyFixedStack<T> {

    private static final int SCALE_FACTOR = 2;

    public MyResizableStack() {
        super();
    }

    public MyResizableStack(int capacity) {
        super(capacity);
    }

    @Override
    public void push(T element) {
        if (this.getSize() >= this.getCapacity()) {
            this.resize();
        }
        super.push(element);
    }

    private void resize() {
        // noinspection unchecked
        T[] nArr = (T[]) new Object[this.getCapacity() * SCALE_FACTOR];
        System.arraycopy(super.arr, 0, nArr, 0, super.arr.length);
        super.arr = nArr;
    }

}
