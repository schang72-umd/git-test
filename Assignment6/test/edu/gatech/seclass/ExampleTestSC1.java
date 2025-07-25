package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

/**
 * This is a Georgia Tech provided code example for use in assigned
 * private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it
 * available on publicly viewable websites including repositories such
 * as GitHub and GitLab. Such sharing may be investigated as a GT
 * honor code violation. Created for CS6300 Summer 2025.
 *
 * Junit test class provided for the White-Box Testing Assignment.
 * This class should not be altered. Follow the directions to create
 * similar test classes when required.
 */

public class ExampleTestSC1 {

    // This is an example of a test suite supposed to fail with an uncaught
    // exception.
    // Notes:
    // - Do NOT test for division by zero exceptions. Avoid:
    // • JUnit 4: @Test(expected = ArithmeticException.class)
    // • JUnit 4: ExpectedException rule
    // • JUnit 5: assertThrows(ArithmeticException.class, ...)
    // • Any try-catch blocks to handle exceptions
    // - Unlike for normal test, there is no need to have an assertion
    // in the test.
    @Test
    public void Test1() {
        BrokenClass.exampleMethod1(1);
    }

    // This is another example of a test supposed to fail with an
    // uncaught exception.
    @Test
    public void Test2() {
        BrokenClass.exampleMethod1(100);
    }
}
