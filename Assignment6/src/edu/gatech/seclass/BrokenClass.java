package edu.gatech.seclass;

import java.util.Arrays;

/**
 * This is a Georgia Tech provided code example for use in assigned
 * private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it
 * available on publicly viewable websites including repositories such
 * as GitHub and GitLab. Such sharing may be investigated as a GT
 * honor code violation. Created for CS6300 Summer 2025.
 *
 * Template provided for the White-Box Testing Assignment. Follow the
 * assignment directions to either implement or provide comments for
 * the appropriate methods.
 */

public class BrokenClass {

    public static void exampleMethod1(int a) {
        // ...
        int x = a / 0; // Example of instruction that makes the method
                       // fail with an ArithmeticException
        // ...
    }

    public static int exampleMethod2(int a, int b) {
        // ...
        return (a + b) / 0; // Example of instruction that makes the
                            // method fail with an ArithmeticException
    }

    public static void exampleMethod3() {
        // NOT POSSIBLE: This method cannot be implemented because
        // <REPLACE WITH REASON> (this is the example format for a
        // method that is not possible)
    }

    //public static void brokenMethod1() { // Change the signature as needed
        // Either add a comment in the format provided above or
        // implement the method.
    public static int brokenMethod1(int a, int b) {
        if (a > 0) {
            return 1;
        }
        if (b > 0) {
            return 2;
        }
        // Only executed if a <= 0 and b <= 0: triggers division by zero
        return a + b/0;
    }

    public static void brokenMethod2(int x) { // Change the signature as needed
        // Either add a comment in the format provided above or
        // implement the method.
        if (x > 0) {
            int y = 1 / x; // Safe
        }
        if (x == 0) {
            int z = 1 / x; // Fault: division by zero
        }
    }

    public static void brokenMethod3() { // Change the signature as needed
        // It is not possible to construct a method with only simple predicates,
        // no nested ifs, and no compound branches, such that:
        // (1) The fault is only revealed by achieving 100% statement coverage, and
        // (2) Any test suite that achieves 100% branch coverage reveals the fault.
        // This is because each branch can be executed independently, so the faulty
        // statement can always be executed without covering all statements.
    }

    public static int brokenMethod4(int a, int b, int c, boolean d, boolean e) {
        int result = 0;
        if (a == 0) {
            result = 1;
        } else {
            if (((b >= 0) || (c < 0)) && (d != e)) {
                result = 2;
            } else {
                result = 3;
            }
        }
        return result;
    }

    //public static String[] brokenMethod5() {
    public static boolean brokenMethod5(boolean a, boolean b) {
        int x;
        int y;
        if (a) {
            x = 0;
        } else {
            x = -2;
        }
        if (b) {
            y = 2 * x + x;
        } else {
            y = 1;
        }
        return ((3 * x - 6) / y > 0);
    }

    private static String resultBrokenMethod5(boolean a, boolean b) {
        try {
            boolean result = brokenMethod5(a, b);
            if (result) {
                return "T";
            } else {
                return "F";
            }
        }
        catch (ArithmeticException e) {
            return "E";
        }
    }

    public static String testPathCoverage5() {
        try {
            brokenMethod5(true, true);
            brokenMethod5(true, false);
            brokenMethod5(false, true);
            brokenMethod5(false, false);
        }
        catch (ArithmeticException e) {
            return "ALWAYS";
        }
        return "NEVER";
    }

    // The branch and statement coverages have the same test suite.
    public static String testBranchStatementCoverage5() {
        boolean foundFault1 = false;
        boolean foundFault2 = false;
        try {
            // Test the first branch and statement coverages
            // The fault is not found.
            brokenMethod5(true, false);
            brokenMethod5(false, true);
        }
        catch (ArithmeticException e) {
            foundFault1 = true;
        }

        try {
            // Test the second branch and statement coverages
            // The fault is supposed to be found.
            brokenMethod5(true, true);
            brokenMethod5(false, false);
        }
        catch (ArithmeticException e) {
            foundFault2 = true;
        }

        if (!foundFault1 && foundFault2) {
            return "SOMETIMES";
        }
        else {
            return "NEVER";
        }
    }


    //public static void main(String[] args) {
    public static String[] brokenMethod5() {
        //BrokenClass brokenObj = new BrokenClass();

        String a[] = new String[7];

        //
        // Replace the "?" in column "output" with "T", "F", or "E":
        //
        // | a | b |output|
        // ================
        //a[0] = /* | T | T | <T, F, or E> (e.g., "T") */ "?";
        //a[1] = /* | T | F | <T, F, or E> (e.g., "T") */ "?";
        //a[2] = /* | F | T | <T, F, or E> (e.g., "T") */ "?";
        //a[3] = /* | F | F | <T, F, or E> (e.g., "T") */ "?";

        a[0] = resultBrokenMethod5(true, true);
        a[1] = resultBrokenMethod5(true, false);
        a[2] = resultBrokenMethod5(false, true);
        a[3] = resultBrokenMethod5(false, false);
        // ================
        //
        // Replace the "?" in the following sentences with "NEVER",
        // "SOMETIMES" or "ALWAYS":
        //
        //a[4] = /* Test suites with 100% path coverage */ "?";
        /* reveal the fault in this method. */
        //a[5] = /* Test suites with 100% branch coverage */ "?";
        /* reveal the fault in this method. */
        //a[6] = /* Test suites with 100% statement coverage */ "?";
        /* reveal the fault in this method. */
        // ================
        a[4] = testPathCoverage5();
        a[5] = testBranchStatementCoverage5();
        a[6] = testBranchStatementCoverage5();

        return a;
        //System.out.println(Arrays.toString(a));
    }
}
