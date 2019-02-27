package com.nikitavbv.assignments.discretestructures.lab1;

import static junit.framework.TestCase.assertEquals;

import com.nikitavbv.assignments.discretestructures.lab1.stack.MyFixedStack;
import com.nikitavbv.assignments.discretestructures.lab1.stack.StackFullException;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MyFixedStackTests {

    @Test
    public void testPushPopAndTop() {
        MyFixedStack<Integer> stack = new MyFixedStack<>(10);

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
        MyFixedStack stack = new MyFixedStack(10);
        assertEquals(10, stack.getCapacity());
        assertEquals(0, stack.getSize());
    }

    @Test
    public void testDefaultCapacity() {
        MyFixedStack stack = new MyFixedStack();
        assertEquals(100, stack.getCapacity());
        assertEquals(0, stack.getSize());
    }

    @Test
    public void testClear() {
        MyFixedStack<Integer> stack = new MyFixedStack<>(10);

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
        new MyFixedStack(-10);
    }

    @Test(expected = StackFullException.class)
    public void testAddingToFullStack() {
        MyFixedStack<Integer> stack = new MyFixedStack<>(1);

        assertEquals(0, stack.getSize());
        stack.push(42);
        assertEquals(1, stack.getSize());

        stack.push(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void testPopOnEmptyStack() {
        MyFixedStack stack = new MyFixedStack(10);
        assertEquals(0, stack.getSize());
        stack.pop();
    }

    @Test(expected = NoSuchElementException.class)
    public void testTopOnEmptyStack() {
        MyFixedStack stack = new MyFixedStack(10);
        assertEquals(0, stack.getSize());
        stack.top();
    }

}
