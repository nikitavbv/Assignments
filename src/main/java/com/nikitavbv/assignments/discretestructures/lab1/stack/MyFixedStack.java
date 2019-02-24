package com.nikitavbv.assignments.discretestructures.lab1.stack;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Stack implementation with fixed capacity.
 *
 * @param <T> the type of elements in this stack
 *
 * @author Nikita Volobuev
 * @see MyStackInterface
 */
public class MyFixedStack<T> implements MyStackInterface<T> {

    private static final int DEFAULT_CAPACITY = 100;

    T[] arr;
    private int pos = 0;

    /** Creates stack with default capacity */
    public MyFixedStack() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates stack with specified capacity.
     *
     * @param capacity maximum size of this stack.
     * @throws IllegalArgumentException if stack capacity is < 0
     */
    public MyFixedStack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity should be >= 0");
        }

        // noinspection unchecked
        this.arr = (T[]) new Object[capacity];
    }

    @Override
    public void push(T element) {
        if (pos >= arr.length) {
            throw new StackFullException();
        }

        arr[pos] = element;
        pos++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        pos--;
        final T element = arr[pos];
        arr[pos] = null;
        return element;
    }

    @Override
    public T top() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return arr[pos - 1];
    }

    @Override
    public void clear() {
        pos = 0;
        Arrays.fill(arr, null);
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public int getSize() {
        return pos;
    }

    public int getCapacity() {
        return arr.length;
    }
}
