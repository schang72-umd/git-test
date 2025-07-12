package edu.gatech.seclass.transformtxt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

@Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class MyMainTest {
    // Place all of your tests in this class, optionally using MainTest.java as an example
    private final String usageStr =
            "Usage: transformtxt [ -s number | -x | -g | -r old new | -t num | -w ] FILE"
                    + System.lineSeparator();

    @TempDir Path tempDirectory;

    @RegisterExtension OutputCapture capture = new OutputCapture();

    /* ----------------------------- Test Utilities ----------------------------- */

    /**
     * Returns path of a new "input.txt" file with specified contents written into it. The file will
     * be created using {@link TempDir TempDir}, so it is automatically deleted after test
     * execution.
     *
     * @param contents the text to include in the file
     * @return a Path to the newly written file, or null if there was an issue creating the file
     */
    private Path createFile(String contents) {
        return createFile(contents, "input.txt");
    }

    /**
     * Returns path to newly created file with specified contents written into it. The file will be
     * created using {@link TempDir TempDir}, so it is automatically deleted after test execution.
     *
     * @param contents the text to include in the file
     * @param fileName the desired name for the file to be created
     * @return a Path to the newly written file, or null if there was an issue creating the file
     */
    private Path createFile(String contents, String fileName) {
        Path file = tempDirectory.resolve(fileName);
        try {
            Files.writeString(file, contents);
        } catch (IOException e) {
            return null;
        }

        return file;
    }

    /**
     * Takes the path to some file and returns the contents within.
     *
     * @param file the path to some file
     * @return the contents of the file as a String, or null if there was an issue reading the file
     */
    private String getFileContent(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* ------------------------------- Test Cases ------------------------------- */
    // Frame 1: Test Case 1 <error>
    @Test
    public void transformtxtTest1() {
        String input = "abc";

        Path inputFile = createFile(input);
        String[] args = {"-s","5",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 2: Test Case 2 <error>
    @Test
    public void transformtxtTest2() {
        String input = "abc";

        Path inputFile = createFile(input);
        String[] args = {"-s",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 3: Test Case 3 <error>
    @Test
    public void transformtxtTest3() {
        String input = "abc";

        Path inputFile = createFile(input);
        String[] args = {"-g",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 4: Test Case 4 <error>
    @Test
    public void transformtxtTest4() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        String[] args = {"-r", "", "rain",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 5: Test Case 5 <error>
    @Test
    public void transformtxtTest5() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        String[] args = {"-r",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 6: Test Case 6 <error>
    @Test
    public void transformtxtTest6() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        String[] args = {"-w",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 7: Test Case 7 <error>
    @Test
    public void transformtxtTest7() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        // should be -w (leading|trailing|all)
        String[] args = {"-w", "akk",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 8: Test Case 8 <error>
    @Test
    public void transformtxtTest8() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        // should have a parameter after -t
        String[] args = {"-t",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 9: -t with wrong parameter <error>
    @Test
    public void transformtxtTest9() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        // should be -t <length> where 0 <= length <= 100
        String[] args = {"-t", "102",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 10: Other Options <error>
    @Test
    public void transformtxtTest10() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        // File is missing (file not found)
        String[] args = {"-m","-n","all",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 11: File not exists <error>
    @Test
    public void transformtxtTest11() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        // File is missing (file not found)
        String[] args = {"-x","-w","all",inputFile.toString() + "1"};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // Frame 12:  Empty input file <single>
    // Test input string is empty.
    @Test
    public void transformtxtTest12() {
        String input = "";

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    // Frame 13: Input File with newline only <single>
    // Test input string is "\n".
    @Test
    public void transformtxtTest13() {
        String input = "\n";

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    // Frame 14: (Key = 1.1.1.1.0.0.0.)
    @Test
    public void transformtxtTest14() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-r","day","o","-w","leading",inputFile.toString()};
        Main.main(args);

        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 15: (Key = 1.1.1.2.0.0.0.)
    @Test
    public void transformtxtTest15() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-r","day","o","-t","15",inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 15: Test Case 15
    @Test
    public void transformtxtTest16() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-r","day","o",inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }


    // Frame 16: Test Case 16
    @Test
    public void transformtxtTest17() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-g","-r","day","o","-w", "trailing", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 17: Test Case 17
    @Test
    public void transformtxtTest18() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-g","-r","day","o","-t", "10", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 18: Test Case 18
    @Test
    public void transformtxtTest19() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-g","-r","day","o", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }


    // Frame 19: Test Case 19
    @Test
    public void transformtxtTest20() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-w","trailing", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 20: Test Case 20
    @Test
    public void transformtxtTest21() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-t", "14", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 21: Test Case 21
    @Test
    public void transformtxtTest22() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 22: Test Case 22
    @Test
    public void transformtxtTest23() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","r", "one", "two", "-w", "all", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 23: Test Case 23
    @Test
    public void transformtxtTest24() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-r","one", "two", "-t", "15", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 24: Test Case 24
    @Test
    public void transformtxtTest25() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-r","one", "two", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 25: Test Case 25
    @Test
    public void transformtxtTest26() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-g","-r","one", "two", "-w", "leading", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 26: Test Case 26
    @Test
    public void transformtxtTest27() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-g","-r","one", "two", "-t", "10", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 27: Test Case 27
    @Test
    public void transformtxtTest28() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-g","-r","one", "two", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 28: Test Case 28
    @Test
    public void transformtxtTest29() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-w", "training", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 29: Test Case 29
    @Test
    public void transformtxtTest30() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-t", "10", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 31: (Key = 1.2.6.3.0.0.0.)
    @Test
    public void transformtxtTest31() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0", inputFile.toString()};
        Main.main(args);

        // TODO: need to figure out the expectedOut
        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

}
