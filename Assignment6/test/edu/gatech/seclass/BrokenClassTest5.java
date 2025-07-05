package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BrokenClassTest5 {

    @Test
    void testBrokenMethod5() {
        String a[] = new String[7];
        a = BrokenClass.brokenMethod5();
        //System.out.println(Arrays.toString(a));
    }
}
