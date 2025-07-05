package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

public class BrokenClassTestBC2 {

    // x > 0 true, y > 5 false
    @Test
    void testBranch1() {
        BrokenClass.brokenMethod2(1, 0);
    }

    // x > 0 false, y > 5 true (division by zero)
    @Test
    void testBranch2() {
        BrokenClass.brokenMethod2(0, 6);
    }
}
