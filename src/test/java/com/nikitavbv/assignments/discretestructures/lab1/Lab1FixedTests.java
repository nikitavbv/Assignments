package com.nikitavbv.assignments.discretestructures.lab1;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.*;

public class Lab1FixedTests {

    @Test
    public void testLab() {
        System.setIn(new ByteArrayInputStream("(((())))(())()".getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Lab1Fixed.main(new String[]{});

        assertEquals("Enter brackets sequence: \ncorrect\n", outContent.toString());

        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    public void testCheckCorrect() {
        assertTrue(Lab1Fixed.checkIfSequenceIsCorrect("(((())))(())()"));
    }

    @Test
    public void testCheckNoOpeningBracket() {
        assertFalse(Lab1Fixed.checkIfSequenceIsCorrect("()())()"));
    }

    @Test(expected = RuntimeException.class)
    public void testCheckUnknownCharacter() {
        assertFalse(Lab1Fixed.checkIfSequenceIsCorrect("()()[)()"));
    }

}
