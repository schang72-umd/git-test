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
            "Usage: transformtxt [ -s number | -x | -g | -r old new | -t num | -w spacing ] FILE"
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
    // Frame 1: -s not 0 and 1 <error>
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

    // Frame 2: -s has no parameter <error>
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

    // Frame 3: -g does not follow -s <error>
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

    // Frame 4: -r first instance with empty string <error>
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

    // Frame 5: -r has no old or new parameters <error>
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

    // Frame 6: -w has no parameter <error>
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

    // Frame 7: -w has invalid parameter <error>
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

    // Frame 8: -t has no parameter between 0 and 100 <error>
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

    // Frame 9: -t with invalid parameter not between 0 and 100 <error>
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

    // Frame 10: Invalid options <error>
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

    // Frame 11: File does not exist <error>
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
        String input = "" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x","-w","all", inputFile.toString()};
        Main.main(args);

        String expectedOut = "" + System.lineSeparator();

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    // Frame 13: Not end with "\r\n" or "\n" <error>
    // TODO - verify the test
    @Test
    public void transformtxtTest13() {
        // input has no EOL at the end
        String input = "abc" + System.lineSeparator()
                + "def";

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
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
                + "Tweso is not real." + System.lineSeparator();

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

        String expectedOut = "   Too is tomor" + System.lineSeparator()
                + "Tweso is not re" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 16: (Key = 1.1.1.3.0.0.0.)
    @Test
    public void transformtxtTest16() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-r","day","o",inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Too is tomorrow." + System.lineSeparator()
                + "Tweso is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }


    // Frame 17: (Key = 1.1.2.1.0.0.0.)
    @Test
    public void transformtxtTest17() {
        String input = "   Today is tomorrow.    " + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real.     " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-g","-r","day","o","-w", "trailing", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Too is tomorrow." + System.lineSeparator()
                + "Tweso is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 18: (Key = 1.1.2.2.0.0.0.)
    @Test
    public void transformtxtTest18() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-g","-r","day","o","-t", "10", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Too is " + System.lineSeparator()
                + "Tweso is n" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 19: (Key = 1.1.2.3.0.0.0.)
    @Test
    public void transformtxtTest19() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-g","-r","day","o", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Too is tomorrow." + System.lineSeparator()
                + "Tweso is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }


    // Frame 20: (Key = 1.1.6.1.0.0.0.)
    @Test
    public void transformtxtTest20() {
        String input = "   Today is tomorrow.    " + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real.        " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-w","trailing", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 21: (Key = 1.1.6.2.0.0.0.)
    @Test
    public void transformtxtTest21() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-t", "14", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is to" + System.lineSeparator()
                + "Twesday is not" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 22: (Key = 1.1.6.3.0.0.0.)
    @Test
    public void transformtxtTest22() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 23: (Key = 1.2.1.1.0.0.0.)
    @Test
    public void transformtxtTest23() {
        String input = "   Today is tomorrow.    " + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real.    " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-r", "day", "o", "-w", "all", inputFile.toString()};
        Main.main(args);

        String expectedOut = "Tooistomorrow." + System.lineSeparator()
                + "Twesoisnotreal." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 24: (Key = 1.2.1.2.0.0.0.)
    @Test
    public void transformtxtTest24() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-r","day", "o", "-t", "15", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Too is tomor" + System.lineSeparator()
                + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 25: (Key = 1.2.1.3.0.0.0.)
    @Test
    public void transformtxtTest25() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-r","day", "o", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Too is tomorrow." + System.lineSeparator()
                + "Tweso is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 26: (Key = 1.2.2.1.0.0.0.)
    @Test
    public void transformtxtTest26() {
        String input = "   Today is tomorrow.    " + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real.    " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-g","-r","day", "o", "-w", "all", inputFile.toString()};
        Main.main(args);

        String expectedOut = "Tooistomorrow." + System.lineSeparator()
                + "Twesoisnotreal." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 27: (Key = 1.2.2.2.0.0.0.)
    @Test
    public void transformtxtTest27() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-g","-r","day", "o", "-t", "10", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Too is " + System.lineSeparator()
                + "Tweso is n" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 28: (Key = 1.2.2.3.0.0.0.)
    @Test
    public void transformtxtTest28() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-g","-r","day", "o", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Too is tomorrow." + System.lineSeparator()
                + "Tweso is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 29: (Key = 1.2.6.1.0.0.0.)
    @Test
    public void transformtxtTest29() {
        String input = "   Today is tomorrow.    " + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-w", "trailing", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 30: (Key = 1.2.6.2.0.0.0.)
    @Test
    public void transformtxtTest30() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-t", "10", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today i" + System.lineSeparator()
                + "Twesday is" + System.lineSeparator();

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

        String expectedOut = "   Today is tomorrow." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 32: (Key = 2.1.1.1.0.0.0.)
    // Starting "-s 1"
    @Test
    public void transformtxtTest32() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", "-r", "day", "DAY", "-w", "leading", inputFile.toString()};
        Main.main(args);

        String expectedOut = "YesterDAY is today." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // Frame 33: (Key = 2.1.1.2.0.0.0.)
    @Test
    public void transformtxtTest33() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", "-r", "day", "DAY", "-t", "54", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         YesterDAY is today." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	// Frame 34: (Key = 2.1.1.3.0.0.0.)
    @Test
    public void transformtxtTest34() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", "-r", "day", "DAY", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         YesterDAY is today." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 35: (Key = 2.1.2.1.0.0.0.)
    @Test
    public void transformtxtTest35() {
        String input = "   Today is tomorrow.      " + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", "-g", "-r", "day", "DAY", "-w", "all", inputFile.toString()};
        Main.main(args);

        String expectedOut = "YesterDAYistoDAY." + System.lineSeparator()
                + "TwesDAYisnotreal." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 36: (Key = 2.1.2.2.0.0.0.)
    @Test
    public void transformtxtTest36() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", "-g", "-r", "day", "DAY", "-t", "34", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         YesterDAY is toDAY." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 37: (Key = 2.1.1.3.0.0.0.)
    @Test
    public void transformtxtTest37() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", "-g", "-r", "day", "DAY", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         YesterDAY is toDAY." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 38: (Key = 2.1.6.1.0.0.0.)
    @Test
    public void transformtxtTest38() {
        String input = "   Today is tomorrow.   " + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", "-w", "trailing", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 39: (Key = 2.1.6.2.0.0.0.)
    @Test
    public void transformtxtTest39() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", "-t", "9", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         " + System.lineSeparator()
                + "Twesday i" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 40: (Key = 2.1.6.3.0.0.0.)
    @Test
    public void transformtxtTest40() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-x", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 41: (Key = 2.2.1.1.0.0.0.)
    @Test
    public void transformtxtTest41() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-r", "day", "DAY", "-w", "leading", inputFile.toString()};
        Main.main(args);

        String expectedOut = "YesterDAY is today." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 42: (Key = 2.2.1.2.0.0.0.)
    @Test
    public void transformtxtTest42() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-r", "day", "DAY", "-t", "0", inputFile.toString()};
        Main.main(args);

        String expectedOut = System.lineSeparator()
                + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 43: (Key = 2.2.1.3.0.0.0.)
    @Test
    public void transformtxtTest43() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-r", "day", "DAY", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         YesterDAY is today." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 44: (Key = 2.2.2.1.0.0.0.)
    @Test
    public void transformtxtTest44() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-g", "-r", "day", "DAY", "-w", "leading", inputFile.toString()};
        Main.main(args);

        String expectedOut = "YesterDAY is toDAY." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 45: (Key = 2.2.2.2.0.0.0.)
    @Test
    public void transformtxtTest45() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-g", "-r", "day", "DAY", "-t", "11", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         Ye" + System.lineSeparator();
                //+ "TwesDAY is " + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 46: (Key = 2.2.2.3.0.0.0.)
    @Test
    public void transformtxtTest46() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-g", "-r", "day", "DAY", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         YesterDAY is toDAY." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
		// Frame 47: (Key = 2.2.6.1.0.0.0.)
    @Test
    public void transformtxtTest47() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-w", "trailing", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	
		// Frame 48: (Key = 2.2.6.2.0.0.0.)
    @Test
    public void transformtxtTest48() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-t", "88", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	
		// Frame 49: (Key = 2.2.6.3.0.0.0.)
    @Test
    public void transformtxtTest49() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", inputFile.toString()};
        Main.main(args);

        String expectedOut = "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	
    // Frame 50: (Key = 5.1.1.1.0.0.0.)
    // starting no -s
    @Test
    public void transformtxtTest50() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-r", "day", "Day", "-w", "leading", inputFile.toString()};
        Main.main(args);

        String expectedOut = "ToDay is tomorrow."  + System.lineSeparator()
                + "YesterDay is today." + System.lineSeparator()
                + "TwesDay is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	
    // Frame 51: (Key = 5.1.1.2.0.0.0.)
    @Test
    public void transformtxtTest51() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-r", "day", "Day", "-t", "75", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   ToDay is tomorrow." + System.lineSeparator()
                + "         YesterDay is today."  + System.lineSeparator()
                + "TwesDay is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	
    // Frame 52: (Key = 5.1.1.3.0.0.0.)
    @Test
    public void transformtxtTest52() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-r", "day", "Day", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   ToDay is tomorrow." + System.lineSeparator()
                + "         YesterDay is today." + System.lineSeparator()
                + "TwesDay is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 53: (Key = 5.1.2.1.0.0.0.)
    @Test
    public void transformtxtTest53() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-g", "-r", "day", "Day", "-w", "all", inputFile.toString()};
        Main.main(args);

        String expectedOut = "ToDayistomorrow." + System.lineSeparator()
                + "YesterDayistoDay." + System.lineSeparator()
                + "TwesDayisnotreal." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	
    // Frame 54: (Key = 5.1.2.2.0.0.0.)
    @Test
    public void transformtxtTest54() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-g", "-r", "day", "Day", "-t", "10", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   ToDay i" + System.lineSeparator()
                + "         Y" + System.lineSeparator()
                + "TwesDay is" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 55: (Key = 5.1.2.3.0.0.0.)
    @Test
    public void transformtxtTest55() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-g", "-r", "day", "Day", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   ToDay is tomorrow." + System.lineSeparator()
                + "         YesterDay is toDay." + System.lineSeparator()
                + "TwesDay is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	
    // Frame 56: (Key = 5.1.6.1.0.0.0.)
    @Test
    public void transformtxtTest56() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-w", "all", inputFile.toString()};
        Main.main(args);

        String expectedOut = "Todayistomorrow." + System.lineSeparator()
                + "Yesterdayistoday." + System.lineSeparator()
                + "Twesdayisnotreal." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
	
    // Frame 57: (Key = 5.1.6.2.0.0.0.)
    @Test
    public void transformtxtTest57() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-t", "14", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is to" + System.lineSeparator()
                + "         Yeste" + System.lineSeparator()
                + "Twesday is not" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 58: (Key = 5.1.6.3.0.0.0.)
    @Test
    public void transformtxtTest58() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 59: (Key = 5.2.1.1.0.0.0.)
    // start with no -s and no -x
    @Test
    public void transformtxtTest59() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-r", "day", "DAy", "-w", "leading", inputFile.toString()};
        Main.main(args);

        String expectedOut = "ToDAy is tomorrow." + System.lineSeparator()
                + "YesterDAy is today." + System.lineSeparator()
                + System.lineSeparator()
                + "TwesDAy is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 60: (Key = 5.2.1.2.0.0.0.)
    @Test
    public void transformtxtTest60() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-r", "day", "DAy", "-t", "30", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   ToDAy is tomorrow." + System.lineSeparator()
                + "         YesterDAy is today." + System.lineSeparator()
                + System.lineSeparator()
                + "TwesDAy is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 61: (Key = 5.2.1.3.0.0.0.)
    @Test
    public void transformtxtTest61() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-r", "day", "DAy", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   ToDAy is tomorrow." + System.lineSeparator()
                + "         YesterDAy is today." + System.lineSeparator()
                + System.lineSeparator()
                + "TwesDAy is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 62: (Key = 5.2.2.1.0.0.0.)
    @Test
    public void transformtxtTest62() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-g", "-r", "day", "DAy", "-w", "all", inputFile.toString()};
        Main.main(args);

        String expectedOut = "ToDAyistomorrow." + System.lineSeparator()
                + "YesterDAyistoDAy." + System.lineSeparator()
                + System.lineSeparator()
                + "TwesDAyisnotreal." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 63: (Key = 5.2.2.2.0.0.0.)
    @Test
    public void transformtxtTest63() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-g", "-r", "day", "DAy", "-t", "15", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   ToDAy is tom" + System.lineSeparator()
                + "         Yester" + System.lineSeparator()
                + System.lineSeparator()
                + "TwesDAy is not " + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 64: (Key = 5.2.2.3.0.0.0.)
    @Test
    public void transformtxtTest64() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-g", "-r", "day", "DAy", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   ToDAy is tomorrow." + System.lineSeparator()
                + "         YesterDAy is toDAy." + System.lineSeparator()
                + System.lineSeparator()
                + "TwesDAy is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 65: (Key = 5.2.6.1.0.0.0.)
    // Start with no-s, no-x, no-r
    @Test
    public void transformtxtTest65() {
        String input = "   Today is tomorrow.    " + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real.  " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-w", "trailing", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 66: (Key = 5.2.6.2.0.0.0.)
    @Test
    public void transformtxtTest66() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t", "20", inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is tomorrow" + System.lineSeparator()
                + "         Yesterday i" + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
	
    // Frame 67: (Key = 5.2.6.3.0.0.0.)
    @Test
    public void transformtxtTest67() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        String expectedOut = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    /* -------- Additional Cases -------- */
    // -r missing one parameters <error>
    @Test
    public void transformtxtTest68() {
        String input = "Today is a sunny day!";

        Path inputFile = createFile(input);
        String[] args = {"-g", "-r", "new", inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());
    }

    // -r and -g has different order (-r goes first)
    @Test
    public void transformtxtTest69() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1", "-r", "day", "DAY", "-g", "-w", "leading", inputFile.toString()};
        Main.main(args);

        String expectedOut = "YesterDAY is toDAY." + System.lineSeparator()
                + "TwesDAY is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    // -t 0

    // -t 100

}
