package com.nikitavbv.assignments.discretestructures.lab1;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertFalse;

public class Lab1Tests {

    @Test
    public void testLab() {
        System.setIn(new ByteArrayInputStream("(((())))(())()".getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Lab1.main(new String[]{});

        assertEquals("Enter brackets sequence: \ncorrect\n", outContent.toString());

        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    public void testCheckCorrect() {
        assertTrue(Lab1.checkIfSequenceIsCorrect("(((())))(())()"));
    }

    @Test
    public void testCheckNoOpeningBracket() {
        assertFalse(Lab1.checkIfSequenceIsCorrect("()())()"));
    }

    @Test(expected = RuntimeException.class)
    public void testCheckUnknownCharacter() {
        assertFalse(Lab1.checkIfSequenceIsCorrect("()()[)()"));
    }

}
