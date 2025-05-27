package edu.gatech.seclass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStringTest2 {
    private MyString str;

    @BeforeEach
    void setUp() {
        str = new MyString();
    }

    // ======================= countAlphabeticWords() ==========================

    @Test
    @DisplayName("Count alphabetic words - normal string")
    void testCountAlphabeticWords_Normal() {
        str.setString("My numbers are 11, 96, and thirteen");
        assertEquals(5, str.countAlphabeticWords());
    }

    @Test
    @DisplayName("Count alphabetic words - mixed with numbers and symbols")
    void testCountAlphabeticWords_MixedContent() {
        str.setString("i#love 2 pr00gram.");
        assertEquals(4, str.countAlphabeticWords());
    }

    @Test
    @DisplayName("Count alphabetic words - null string throws")
    void testCountAlphabeticWords_Null() {
        str.setString(null);
        assertThrows(NullPointerException.class, () -> str.countAlphabeticWords());
    }

    @Test
    @DisplayName("Count alphabetic words - only numbers and symbols")
    void testCountAlphabeticWords_NoWords() {
        str.setString("123 4567 89!!");
        assertEquals(0, str.countAlphabeticWords());
    }

    @Test
    void testCountAlphabeticWords_SingleWord() {
        str.setString("Hello");
        assertEquals(1, str.countAlphabeticWords());
    }

    @Test
    void testCountAlphabeticWords_MultipleSpaces() {
        str.setString("  one   two  three  ");
        assertEquals(3, str.countAlphabeticWords());
    }

    @Test
    void testCountAlphabeticWords_EmptyString() {
        //str.setString("   ");
        //assertEquals(0, str.countAlphabeticWords());
        assertThrows(IllegalArgumentException.class, () -> str.setString("   "));
    }

    @Test
    void testCountAlphabeticWords_SingleCharWords() {
        str.setString("a b c d e");
        assertEquals(5, str.countAlphabeticWords());
    }

    @Test
    void testCountAlphabeticWords_WordsWithUnderscores() {
        str.setString("foo_bar baz");
        assertEquals(3, str.countAlphabeticWords());
    }

    @Test
    void testCountAlphabeticWords_PunctuationInsideWord() {
        str.setString("well-done! yes.no");
        assertEquals(4, str.countAlphabeticWords());
    }

    // ========================== encrypt(int, int) ============================

    @Test
    void testEncrypt_Example() {
        str.setString("Cat & 5 DogS");
        String encrypted = str.encrypt(5, 3);
        assertEquals("7Ro & s cZlp", encrypted);
    }

    @Test
    void testEncrypt_AllDigits() {
        str.setString("0123456789");
        assertEquals(10, str.encrypt(5, 3).length());
    }

    @Test
    void testEncrypt_AllLowerCase() {
        str.setString("abcdefghijklmnopqrstuvwxyz");
        assertEquals(26, str.encrypt(5, 3).length());
    }

    @Test
    void testEncrypt_AllUpperCase() {
        str.setString("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(26, str.encrypt(5, 3).length());
    }

    @Test
    void testEncrypt_AllAlphaNumeric() {
        str.setString("0aA9zZ");
        String encrypted = str.encrypt(1, 1);
        assertNotNull(encrypted);
        assertEquals(6, encrypted.length());
    }

    @Test
    void testEncrypt_NonAlphaNumericRemainUnchanged() {
        str.setString("Hello, World! 123.");
        String encrypted = str.encrypt(5, 3);
        assertEquals(',', encrypted.charAt(5));
        assertEquals('!', encrypted.charAt(12));
        assertEquals('.', encrypted.charAt(encrypted.length() - 1));
    }

    @Test
    void testEncrypt_Null() {
        str.setString(null);
        assertThrows(NullPointerException.class, () -> str.encrypt(5, 3));
    }

    @Test
    void testEncrypt_InvalidArg1() {
        str.setString("abc123");
        assertThrows(IllegalArgumentException.class, () -> str.encrypt(2, 3));
    }

    @Test
    void testEncrypt_Arg1Zero() {
        str.setString("abc");
        assertThrows(IllegalArgumentException.class, () -> str.encrypt(0, 2));
    }

    @Test
    void testEncrypt_Arg2Zero() {
        str.setString("abc");
        assertThrows(IllegalArgumentException.class, () -> str.encrypt(5, 0));
    }

    @Test
    void testEncrypt_Arg2Negative() {
        str.setString("abc");
        assertThrows(IllegalArgumentException.class, () -> str.encrypt(5, -1));
    }

    @Test
    void testEncrypt_Arg2Equal62() {
        str.setString("abc");
        assertThrows(IllegalArgumentException.class, () -> str.encrypt(5, 62));
    }

    @Test
    void testEncrypt_Arg1NotCoPrime() {
        str.setString("abc");
        assertThrows(IllegalArgumentException.class, () -> str.encrypt(2, 5));
    }

    // =================== convertDigitsToNamesInSubstring(int, int) ======================

    @Test
    void testConvertDigitsToNamesInSubstring_Example1() {
        str.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        str.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", str.getString());
    }

    @Test
    void testConvertDigitsToNamesInSubstring_Example2() {
        str.setString("abc416d");
        str.convertDigitsToNamesInSubstring(2, 7);
        assertEquals("abcFourOneSixd", str.getString());
    }

    @Test
    void testConvertDigitsToNamesInSubstring_MiddleDigits() {
        str.setString("X123Y");
        str.convertDigitsToNamesInSubstring(2, 4);
        assertEquals("XOneTwoThreeY", str.getString());
    }

    @Test
    void testConvertDigitsToNamesInSubstring_DigitsAtEnds() {
        str.setString("1Hello2");
        str.convertDigitsToNamesInSubstring(1, 7);
        assertEquals("OneHelloTwo", str.getString());
    }

    @Test
    void testConvertDigitsToNamesInSubstring_NoDigits() {
        str.setString("abcdef");
        str.convertDigitsToNamesInSubstring(1, 6);
        assertEquals("abcdef", str.getString());
    }

    @Test
    void testConvertDigitsToNamesInSubstring_OnlyDigits() {
        str.setString("123");
        str.convertDigitsToNamesInSubstring(1, 3);
        assertEquals("OneTwoThree", str.getString());
    }

    @Test
    void testConvertDigitsToNamesInSubstring_DigitsOutsideRange() {
        str.setString("a1b2c3d4");
        str.convertDigitsToNamesInSubstring(3, 6); // Only positions 3 ("b"), 4 ("2"), 5 ("c"), 6 ("3")
        assertEquals("a1bTwocThreed4", str.getString());
    }

    @Test
    void testConvertDigitsToNamesInSubstring_BoundaryPositions() {
        str.setString("7startend8");
        str.convertDigitsToNamesInSubstring(1, 10);
        assertEquals("SevenstartendEight", str.getString());
    }

    @Test
    void testConvertDigitsToNamesInSubstring_OutOfBoundsHigh() {
        str.setString("abc123");
        assertThrows(MyIndexOutOfBoundsException.class, () -> str.convertDigitsToNamesInSubstring(1, 10));
    }

    @Test
    void testConvertDigitsToNamesInSubstring_RangeInvalid() {
        str.setString("123456");
        assertThrows(IllegalArgumentException.class, () -> str.convertDigitsToNamesInSubstring(4, 2));
    }

    @Test
    void testConvertDigitsToNamesInSubstring_FirstZero() {
        str.setString("abc");
        assertThrows(IllegalArgumentException.class, () -> str.convertDigitsToNamesInSubstring(0, 2));
    }

    @Test
    void testConvertDigitsToNamesInSubstring_StringNull() {
        str.setString(null);
        assertThrows(NullPointerException.class, () -> str.convertDigitsToNamesInSubstring(1, 2));
    }
}
