package edu.gatech.seclass;
import org.junit.jupiter.api.Test;

// Test for 100% path coverage and there is one failure
public class BrokenClassTestPC1 {
    // Test a > 0 and b is arbitrary for path one
    @Test
    public void test_a_gt_0() {
        BrokenClass.brokenMethod1(1, 5);
    }

    // Test a <= 0 and b > 0 for path two
    @Test
    public void test_a_le_0_b_gt_0() {
        BrokenClass.brokenMethod1(0, 4);
    }

    // Test a <= 0 and b <= 0 for path three
    @Test
    public void test_a_le_0_b_le_0() {
        BrokenClass.brokenMethod1(0, -2);
    }
}
