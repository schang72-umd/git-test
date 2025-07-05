package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

public class BrokenClassTestSC2 {

    // x > 0 true, y > 5 true -- every statement runs, but no division by zero!
    @Test
    void testStatement() {
        BrokenClass.brokenMethod2(1, 6);
    }
}
