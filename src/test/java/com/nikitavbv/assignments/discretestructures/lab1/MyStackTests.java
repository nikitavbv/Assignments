package com.nikitavbv.assignments.discretestructures.lab1;

import static junit.framework.TestCase.assertEquals;

import com.nikitavbv.assignments.discretestructures.lab1.stack.MyStack;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MyStackTests {

    @Test
    public void testPushTopAndPop() {
        MyStack<Integer> stack = new MyStack<>();

        assertEquals(0, stack.getSize());
        stack.push(2);
        assertEquals(1, stack.getSize());
        stack.push(4);
        assertEquals(2, stack.getSize());
        stack.push(7);
        assertEquals(3, stack.getSize());

        assertEquals(Integer.valueOf(7), stack.top());
        assertEquals(3, stack.getSize());

        assertEquals(Integer.valueOf(7), stack.pop());
        assertEquals(2, stack.getSize());
        assertEquals(Integer.valueOf(4), stack.pop());
        assertEquals(1, stack.getSize());
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(0, stack.getSize());
    }

    @Test
    public void testClear() {
        MyStack<Integer> stack = new MyStack<>();
        assertEquals(0, stack.getSize());
        stack.push(42);
        stack.push(1);
        stack.push(42);
        stack.push(2);
        stack.push(3);
        assertEquals(5, stack.getSize());
        stack.clear();
        assertEquals(0, stack.getSize());
    }

    @Test(expected = NoSuchElementException.class)
    public void testPopFromEmptyStack() {
        MyStack stack = new MyStack();
        assertEquals(0, stack.getSize());
        stack.pop();
    }

    @Test(expected = NoSuchElementException.class)
    public void testTopOnEmptyStack() {
        MyStack stack = new MyStack();
        assertEquals(0, stack.getSize());
        stack.top();
    }

}
