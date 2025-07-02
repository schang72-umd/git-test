package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

public class BrokenClassTestBC2 {

    // x > 0 (true for first if): covers first branch.
    @Test
    public void test_x_gt_0() {
        BrokenClass.brokenMethod2(1);
    }

    // x == 0 (true for second if): covers second branch and triggers the fault.
    @Test
    public void test_x_is_0() {
        BrokenClass.brokenMethod2(0);
    }
}
