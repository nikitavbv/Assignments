package com.nikitavbv.assignments.discretestructures.lab1;

import com.nikitavbv.assignments.discretestructures.lab1.stack.MyFixedStack;
import com.nikitavbv.assignments.discretestructures.lab1.stack.MyStackInterface;

import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Lab1Fixed {

    public static void main(String[] args) {
        System.out.println(checkIfSequenceIsCorrect(requestBracketsSequence()) ? "correct" : "incorrect");
    }

    static boolean checkIfSequenceIsCorrect(String seq) {
        MyStackInterface<Character> stack = new MyFixedStack<>(seq.length());

        for (char element : seq.toCharArray()) {
            if (element == '(') {
                stack.push(element);
            } else if (element == ')') {
                if (stack.isEmpty()) {
                    return false;
                }

                stack.pop();
            } else {
                throw new RuntimeException("Unknown character: " + element);
            }
        }

        return stack.isEmpty();
    }

    private static String requestBracketsSequence() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter brackets sequence: ");
        String brackets = in.nextLine();
        in.close();
        return brackets;
    }

}
