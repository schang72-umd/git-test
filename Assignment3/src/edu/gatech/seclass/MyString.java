package edu.gatech.seclass;

public class MyString implements MyStringInterface {
    private String myString;

    public MyString() {
        myString = null;
    }

    public MyString(String string) {
        this.setString(string);
    }

    @Override
    public String getString() {
        return myString;
    }
/*
    @Override
    public void setString(String myString) {
        this.myString = myString;
    }

 */

    @Override
    public void setString(String string) {
        // null is allowed, sets myString to null
        if (string == null) {
            myString = null;
            return;
        }
        // Check for easterEgg
        if (string.equals(easterEgg)) {
            throw new IllegalArgumentException();
        }
        // Check empty string
        if (string.isEmpty()) {
            throw new IllegalArgumentException();
        }
        // Check must contain at least one letter or number
        if (!string.matches(".*[a-zA-Z0-9].*")) {
            throw new IllegalArgumentException();
        }
        myString = string;
    }

    @Override
    public int countAlphabeticWords() {
        if (myString == null) {
            throw new NullPointerException();
        }
        int count = 0;
        boolean countingWord = false;
        for (int i = 0; i < myString.length(); i++) {
            char c = myString.charAt(i);
            if (Character.isLetter(c)) {
                if (!countingWord) {
                    // Find the first letter
                    countingWord = true;
                    count++;
                }
                // else: still a letter, do nothing and continue
                else continue;
            } else {
                // a non-letter found, set the flag countingWord to false
                countingWord = false;
            }
        }
        return count;
    }

    @Override
    public String encrypt(int arg1, int arg2) {
        if (myString == null) {
            throw new NullPointerException();
        }
        // Check arg1: co-prime to 62, 0 < arg1 < 62, and odd (all valid values)
        int[] validArg1 = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61};
        boolean foundArg1 = false;
        for (int a : validArg1) {
            if (a == arg1) {
                foundArg1 = true;
                break;
            }
        }
        // arg2 is between 1 and 62 included.
        if (!foundArg1 || arg2 < 1 || arg2 >= 62) {
            throw new IllegalArgumentException();
        }
        // Check myString contains at least one letter or digit
        if (!myString.matches(".*[a-zA-Z0-9].*")) {
            throw new IllegalArgumentException();
        }
        // Affine Cipher table
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (char c : myString.toCharArray()) {
            int idx = chars.indexOf(c);
            if (idx != -1) {
                int newIdx = (arg1 * idx + arg2) % 62;
                sb.append(chars.charAt(newIdx));
            } else {
                sb.append(c); // non-alpha and digit remains
            }
        }
        return sb.toString();
    }

    @Override
    public void convertDigitsToNamesInSubstring(int firstPosition, int finalPosition) {
        if (myString == null) {
            throw new NullPointerException();
        }
        if (firstPosition < 1 || firstPosition > finalPosition) {
            throw new IllegalArgumentException();
        }
        if (finalPosition > myString.length()) {
            throw new MyIndexOutOfBoundsException();
        }
        // Mapping for digits
        String[] digitNames = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        StringBuilder sb = new StringBuilder();
        int start = firstPosition - 1;
        int end = finalPosition; // exclusive for substring
        sb.append(myString.substring(0, start));
        for (int i = start; i < end; i++) {
            char c = myString.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(digitNames[c - '0']);
            } else {
                sb.append(c);
            }
        }
        sb.append(myString.substring(end));
        myString = sb.toString();
    }
}
