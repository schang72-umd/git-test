package edu.gatech.seclass;
import org.junit.jupiter.api.Test;

public class BrokenClassTestMCDC4 {

    // Cover a == 0 path (outer if true)
    @Test
    void testAEqualsZero() {
        BrokenClass.brokenMethod4(0, 1, -1, true, false);
    }

    // MC/DC for ((b >= 0) || (c < 0)) && (d != e), a != 0 path
    // 1. C1 F→T, C2 F, C3 T: should toggle inner if FALSE→TRUE (result 3→2)
    @Test
    void testC1FalseC2FalseC3True() {
        BrokenClass.brokenMethod4(1, -1, 1, true, true);
    }
    @Test
    void testC1TrueC2FalseC3True() {
        BrokenClass.brokenMethod4(1, 0, 1, true, false);
    }

    // 2. C1 F, C2 F→T, C3 T: toggling C2 from F to T toggles inner if (3→2)
    @Test
    void testC1FalseC2TrueC3True() {
        BrokenClass.brokenMethod4(1, -1, -1, true, false);
    }

    // 3. C1 F, C2 T, C3 T→F: toggling C3 from T to F toggles inner if (2→3)
    @Test
    void testC1FalseC2TrueC3False() {
        BrokenClass.brokenMethod4(1, -1, -1, true, true);
    }

    // 4. (Bonus - extra): C1 T, C2 x, C3 F (should be result 3)
    @Test
    void testC1TrueC2DontCareC3False() {
        BrokenClass.brokenMethod4(1, 0, -1, true, true);
    }
}
