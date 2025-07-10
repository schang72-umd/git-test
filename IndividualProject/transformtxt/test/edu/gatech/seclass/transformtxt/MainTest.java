package edu.gatech.seclass.transformtxt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

// DO NOT ALTER THIS CLASS. Use it as an example for MyMainTest.java

@Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class MainTest {
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


    @Test
    public void exampleTest1() {
        String input = "";

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void exampleTest2() {
        String input = "";
        Path inputFile = createFile(input);
        String[] args = {"-x","-w","all",inputFile.toString()};
        Main.main(args);
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
    @Test
    public void exampleTest3() {
        String input = "Hello, World." + System.lineSeparator()
                + "Hello, World." + System.lineSeparator()
                + "Hello, World." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","2",inputFile.toString()};
        Main.main(args);
        // Expected output is empty; usage string is printed to stderr.
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr,capture.stderr());

    }
    @Test
    public void exampleTest4() {
        String input ="Okay, here is how this is going to work."+  System.lineSeparator()
                + "No shouting!" + System.lineSeparator()
                + "Does that make sense?" + System.lineSeparator()
                +"Alright, good meeting." +  System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1",inputFile.toString()};
        Main.main(args);

        String expectedOut = "No shouting!" + System.lineSeparator()
                        + "Alright, good meeting." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
    @Test
    public void exampleTest5() {
        String input ="Okay, here is how this is going to work."+  System.lineSeparator()
                + "No shouting!" + System.lineSeparator()
                + "Does that make sense?" + System.lineSeparator()
                +"Alright, good meeting." +  System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t","10",inputFile.toString()};
        Main.main(args);

        String expectedOut = "Okay, here"+  System.lineSeparator()
                + "No shoutin" + System.lineSeparator()
                + "Does that " + System.lineSeparator()
                +"Alright, g" +  System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
    @Test
    public void exampleTest6() {
        String input ="This is a normal text file.     " + System.lineSeparator()
                + "Perhaps too normal...    " + System.lineSeparator()
                + "    Or not normal at all." + System.lineSeparator();


        Path inputFile = createFile(input);
        String[] args = {"-w","trailing",inputFile.toString()};
        Main.main(args);

        String expectedOut = "This is a normal text file." + System.lineSeparator()
                + "Perhaps too normal..." + System.lineSeparator()
                + "    Or not normal at all." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
    @Test
    public void exampleTest7() {
        String input = "I love red apples. I love all red fruits." + System.lineSeparator()
                + "I love red hats. I love all red clothes." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-g","-r","red", "green",inputFile.toString()};
        Main.main(args);

        String expectedOut = "I love green apples. I love all green fruits." + System.lineSeparator()
                + "I love green hats. I love all green clothes." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
    @Test
    public void exampleTest8() {
        String input = "One,    two,    three,        " + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "          Ten" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x","-w","all",inputFile.toString()};
        Main.main(args);

        String expectedOut = "One,two,three," + System.lineSeparator()
                + "Ten" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
    @Test
    public void exampleTest9() {
        String input = "____*" + System.lineSeparator()
                + "___**" + System.lineSeparator()
                + "__***" + System.lineSeparator()
                + "_****" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-t","3",inputFile.toString()};
        Main.main(args);

        String expectedOut = "___" + System.lineSeparator()
                + "__*" + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
    @Test
    public void exampleTest10() {
        String input = "Hello, World. Hello, Minecraft." + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "hello, Nintendo. Hello, Steam." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x","-r","Hello","HELLO",inputFile.toString()};
        Main.main(args);

        String expectedOut = "HELLO, World. Hello, Minecraft." + System.lineSeparator()
                + "hello, Nintendo. HELLO, Steam." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }
    @Test
    public void exampleTest11() {
        String input = "   Today is tomorrow." + System.lineSeparator()
                + "         Yesterday is today." + System.lineSeparator()
                + "Twesday is not real." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-x","-g","-r","day","o","-w","leading",inputFile.toString()};
        Main.main(args);

        String expectedOut = "Too is tomorrow." + System.lineSeparator()
                + "Tweso is not real." + System.lineSeparator();

        Assertions.assertEquals(expectedOut,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

}


