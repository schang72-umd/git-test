package edu.gatech.seclass;

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

    public static String[] brokenMethod5() {
        String a[] = new String[7];
        /*-
        public static boolean brokenMethod5(boolean a, boolean b) {
            int x;
            int y;
            if (a) {
                x = 0;
            } else {
                x = -2;
            }
            if (b) {
                y = 2*x + x;
            } else {
                y = 1;
            }
            return ((3*x-6)/y > 0);
        }
        */

        //
        // Replace the "?" in column "output" with "T", "F", or "E":
        //
        // | a | b |output|
        // ================
        a[0] = /* | T | T | <T, F, or E> (e.g., "T") */ "?";
        a[1] = /* | T | F | <T, F, or E> (e.g., "T") */ "?";
        a[2] = /* | F | T | <T, F, or E> (e.g., "T") */ "?";
        a[3] = /* | F | F | <T, F, or E> (e.g., "T") */ "?";
        // ================
        //
        // Replace the "?" in the following sentences with "NEVER",
        // "SOMETIMES" or "ALWAYS":
        //
        a[4] = /* Test suites with 100% path coverage */ "?";
        /* reveal the fault in this method. */
        a[5] = /* Test suites with 100% branch coverage */ "?";
        /* reveal the fault in this method. */
        a[6] = /* Test suites with 100% statement coverage */ "?";
        /* reveal the fault in this method. */
        // ================
        return a;
    }
}
