package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

public class BrokenClassTestSC4 {
    @Test
    void testAEqualsZero() {
        // Covers: 'if (a == 0)' branch, sets result=1, returns 1.
        BrokenClass.brokenMethod4(0, 1, 1, true, false);
    }

    @Test
    void testInnerIfTrue() {
        // Covers: 'else' branch, inner if TRUE: (b >= 0) || (c < 0) && d != e
        BrokenClass.brokenMethod4(1, 0, 1, true, false);
    }

    @Test
    void testInnerIfFalse() {
        // Covers: 'else' branch, inner if FALSE: (b < 0) && (c >= 0) && (d == e)
        BrokenClass.brokenMethod4(1, -1, 1, true, true);
    }
}
