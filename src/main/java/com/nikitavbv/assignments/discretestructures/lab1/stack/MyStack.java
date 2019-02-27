package com.nikitavbv.assignments.discretestructures.lab1.stack;

import java.util.NoSuchElementException;

/**
 * Stack implementation based on linked list.
 * Thus capacity of this stack is not limited.
 *
 * @param <T> the type of elements in this stack
 *
 * @author Nikita Volobuev
 * @see MyStackInterface
 */
public class MyStack<T> implements MyStackInterface<T> {

    private class MyStackElement<T> {
        private T element;
        private MyStackElement<T> next;

        private MyStackElement(T element, MyStackElement<T> next) {
            this.element = element;
            this.next = next;
        }
    }

    private MyStackElement<T> top = null;
    private int size = 0;

    @Override
    public void push(T element) {
        top = new MyStackElement<>(element, top);
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        final T element = top.element;
        top = top.next;
        size--;
        return element;
    }

    @Override
    public T top() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return top.element;
    }

    @Override
    public void clear() {
        size = 0;
        top = null;
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public int getSize() {
        return size;
    }
}
