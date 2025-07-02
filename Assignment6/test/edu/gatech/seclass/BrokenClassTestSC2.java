package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

public class BrokenClassTestSC2 {

    // Covers both if statements and the statement in the first branch, but not the fault

    // The first if statement and its inside statement are covered.
    @Test
    public void test_x_gt_0() {
        BrokenClass.brokenMethod2(1);
    }

    // Optionally, test with x = -1 to cover both if condition statements
    // Since x is not equal to 0, the statement inside the second if statement is not executed.
    @Test
    public void test_x_lt_0() {
        BrokenClass.brokenMethod2(-1);
    }
}
