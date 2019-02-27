package com.nikitavbv.assignments.discretestructures.lab1.stack;

import java.util.NoSuchElementException;

/**
 * Sequential list of variable size.
 * Adding or removing elements is only possible from the top.
 *
 * @param <T> the type of elements in this stack
 *
 * @author Nikita Volobuev
 * @see MyStack
 * @see MyFixedStack
 * @see MyResizableStack
 */
public interface MyStackInterface<T> {

    /**
     * Inserts the specified element at the top of the stack.
     *
     * @param element element to be inserted
     */
    void push(T element);

    /**
     * Removes and returns top element in this stack.
     *
     * @return top element in this stack
     * @throws NoSuchElementException if this stack is empty
     */
    T pop();

    /**
     * Returns top element in this stack.
     *
     * @return top element in this stack
     * @throws NoSuchElementException if this stack is empty
     */
    T top();

    /**
     * Removes all of the elements from this stack.
     * The stack will be empty after this call returns.
     */
    void clear();

    /** Returns <tt>true</tt> if stack contains no elements. */
    boolean isEmpty();

    /** Returns the number of elements in this stack. */
    int getSize();

}
