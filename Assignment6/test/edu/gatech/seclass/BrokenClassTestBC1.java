package edu.gatech.seclass;
import org.junit.jupiter.api.Test;

// Test for 100% branch coverage and there is no failure
public class BrokenClassTestBC1 {

    // Test a > 0 and b is arbitrary for branch one
    @Test
    public void test_a_gt_0() {
        BrokenClass.brokenMethod1(1, 2);
    }

    // Test a <= 0 and b > 0 for branch two
    @Test
    public void test_a_le_0_b_gt_0() {
        BrokenClass.brokenMethod1(0, 3);
    }
    // Both branches (a>0 and b>0) are covered in both true/false forms,
    // but never a <= 0, b <= 0, so the division-by-zero is NOT triggered.
}
