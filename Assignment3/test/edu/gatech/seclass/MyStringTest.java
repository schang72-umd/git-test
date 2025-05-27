package edu.gatech.seclass;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Timeout.ThreadMode;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 * <p>
 * This class is provided to interpret your grades via junit tests
 * and as a reminder, should NOT be posted in any public repositories,
 * even after the class has ended.
 */

@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
public class MyStringTest {

    private MyStringInterface myString;

    @BeforeEach
    public void setUp() {
        myString = new MyString();
    }

    @AfterEach
    public void tearDown() {
        myString = null;
    }

    @Test
    // Description: First count number example in the interface documentation
    public void testCountAlphabeticWords1() {
        myString.setString("My numbers are 11, 96, and thirteen");
        assertEquals(5, myString.countAlphabeticWords());
    }

    @Test
    // Description: Count words in a null string
    public void testCountAlphabeticWords2() {
        myString.setString(null);
        assertThrows(NullPointerException.class, () -> myString.countAlphabeticWords());
    }

    @Test
    // Description: A string contains no alphabet
    public void testCountAlphabeticWords3() {
        myString.setString("123 567 %$#");
        assertEquals(0, myString.countAlphabeticWords());
    }

    @Test
    // Description: A String contains no number
    public void testCountAlphabeticWords4() {
        myString.setString("one *_two three-");
        assertEquals(3, myString.countAlphabeticWords());
    }

    @Test
    // Description: The String contains no number nor alphabet
    public void testSetString1() {
        assertThrows(IllegalArgumentException.class, () -> myString.setString("_! %$"));
    }


    @Test
    // Description: Sample encryption 1
    public void testEncrypt1() {
        myString.setString("Cat & 5 DogS");
        assertEquals("7Ro & s cZlp", myString.encrypt(5, 3));
    }

    @Test
    // Description: Test the invalid arg1 which is not c0-prime to 62
    public void testEncrypt2() {
        myString.setString("Cat & 5 DogS");
        assertThrows(IllegalArgumentException.class, () -> myString.encrypt(2, 5));
    }

    @Test
    // Description: Test the invalid arg2 that arg2 > 62
    public void testEncrypt3() {
        myString.setString("Cat & 5 DogS");
        assertThrows(IllegalArgumentException.class, () -> myString.encrypt(3, 65));
    }

    @Test
    // Description: Test null string
    public void testEncrypt4() {
        myString.setString(null);
        assertThrows(NullPointerException.class, () -> myString.encrypt(5, 7));
    }

    @Test
    // Description: Test the string contains only digits and letters
    public void testEncrypt5() {
        myString.setString("aBc123");
        assertEquals("R218di", myString.encrypt(5, 3));
    }

    @Test
    // Description: Test the non-digit and non-letter are not changed
    public void testEncrypt6() {
        myString.setString("Dog & cat are my good friends!");
        String encrypted = myString.encrypt(5, 3);
        assertEquals('&', encrypted.charAt(4));
        assertEquals('!', encrypted.charAt(encrypted.length() - 1));
    }

    @Test
    // Description: First convert digits example in the interface documentation
    public void testConvertDigitsToNamesInSubstring1() {
        myString.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        myString.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", myString.getString());
    }


    @Test
    // Description: Test null string input
    public void testConvertDigitsToNamesInSubstring2() {
        myString.setString(null);
        assertThrows(NullPointerException.class, () -> myString.convertDigitsToNamesInSubstring(5, 7));
    }

    @Test
    // Description: Test invalid arguments: firstPosition > finalPosition
    public void testConvertDigitsToNamesInSubstring3() {
        myString.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertThrows(IllegalArgumentException.class, () -> myString.convertDigitsToNamesInSubstring(7, 5));
    }

    @Test
    // Description: Test invalid argument: firstPosition < 1
    public void testConvertDigitsToNamesInSubstring4() {
        myString.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertThrows(IllegalArgumentException.class, () -> myString.convertDigitsToNamesInSubstring(0, 5));
    }

    @Test
    // Description: Test invalid arguments: finalPosition > myString.length()
    public void testConvertDigitsToNamesInSubstring5() {
        myString.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        int len = myString.getString().length();
        assertThrows(MyIndexOutOfBoundsException.class, () -> myString.convertDigitsToNamesInSubstring(len-5, len+1));
    }
}
