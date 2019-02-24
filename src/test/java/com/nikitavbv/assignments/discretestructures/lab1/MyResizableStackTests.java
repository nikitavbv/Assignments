package com.nikitavbv.assignments.discretestructures.lab1;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import com.nikitavbv.assignments.discretestructures.lab1.stack.MyResizableStack;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MyResizableStackTests {

    @Test
    public void testPushPopAndTop() {
        MyResizableStack<Integer> stack = new MyResizableStack<>(10);

        assertEquals(0, stack.getSize());

        stack.push(4);
        assertEquals(1, stack.getSize());
        stack.push(7);
        assertEquals(2, stack.getSize());
        stack.push(1);
        assertEquals(3, stack.getSize());

        assertEquals(Integer.valueOf(1), stack.top());
        assertEquals(3, stack.getSize());

        assertEquals(Integer.valueOf(1), stack.pop());
        assertEquals(2, stack.getSize());
        assertEquals(Integer.valueOf(7), stack.pop());
        assertEquals(1, stack.getSize());
        assertEquals(Integer.valueOf(4), stack.pop());
        assertEquals(0, stack.getSize());
    }

    @Test
    public void testGetCapacity() {
        MyResizableStack stack = new MyResizableStack(10);
        assertEquals(10, stack.getCapacity());
        assertEquals(0, stack.getSize());
    }

    @Test
    public void testDefaultCapacity() {
        MyResizableStack stack = new MyResizableStack();
        assertEquals(100, stack.getCapacity());
        assertEquals(0, stack.getSize());
    }

    @Test
    public void testClear() {
        MyResizableStack<Integer> stack = new MyResizableStack<>(10);

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        assertEquals(4, stack.getSize());
        stack.clear();
        assertEquals(0, stack.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisallowNegativeCapacity() {
        new MyResizableStack(-10);
    }

    @Test(expected = NoSuchElementException.class)
    public void testPopOnEmptyStack() {
        MyResizableStack stack = new MyResizableStack(10);
        assertEquals(0, stack.getSize());
        stack.pop();
    }

    @Test(expected = NoSuchElementException.class)
    public void testTopOnEmptyStack() {
        MyResizableStack stack = new MyResizableStack(10);
        assertEquals(0, stack.getSize());
        stack.top();
    }

    @Test
    public void testResize() {
        MyResizableStack<Integer> stack = new MyResizableStack<>(2);
        int capacity = stack.getCapacity();
        assertEquals(2, capacity);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertTrue(stack.getCapacity() > capacity);
    }

}
