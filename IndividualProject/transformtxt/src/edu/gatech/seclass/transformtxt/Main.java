package edu.gatech.seclass.transformtxt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class Main {
    // Empty Main class for compiling Individual Project
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement it

    private static String filename;
    private static boolean error = false;

    private static Integer skipMode = null; // parameter for -s
    private static boolean removeEmpty = false; // -x option is used
    private static boolean globalReplace = false; // -g option is used
    private static boolean replaceOption = false; // -s option is used
    private static String oldStr = null; // the first parameter for -r
    private static String newStr = null; // the second parameter for -r
    private static String whitespaceMode = null; // -w option with parameter "leading", "trailing", or "all"
    private static Integer truncateLen = null;  // -t option with parameter truncateLen where 0 <= truncateLen <= 100

    public static void main(String[] args) {
        initializeTransformTxt();
        if (parseArgs(args)) {
            // Options are all correct.
            transformTxt();
        }
    }

    private static void initializeTransformTxt() {
        skipMode = null; // parameter for -s
        removeEmpty = false; // -x option is used
        globalReplace = false; // -g option is used
        replaceOption = false; // -s option is used
        oldStr = null; // the first parameter for -r
        newStr = null; // the second parameter for -r
        whitespaceMode = null; // -w option with parameter "leading", "trailing", or "all"
        truncateLen = null;
    }

    // Check if the file ends with the current platform’s line separator
    public static boolean endsWithNewline(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Invalid file path: " + filePath);
        }

        long fileLength = file.length();
        if (fileLength == 0) {
            return false; // empty file: no EOL
        }

        String separator = System.lineSeparator();
        byte[] sepBytes = separator.getBytes(StandardCharsets.UTF_8);
        if (fileLength < sepBytes.length) {
            return false; // file smaller than the separator itself
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            // Seek to where the separator would start
            raf.seek(fileLength - sepBytes.length);
            byte[] tail = new byte[sepBytes.length];
            raf.readFully(tail);
            return Arrays.equals(tail, sepBytes);
        } catch (IOException e) {
            // On any I/O error, fall back to usage() and report false
            usage();
            return false;
        }
    }

    // Return false when parsing options syntax fails.
    private static Boolean parseArgs(String[] args) {
        if (args.length < 1) {
            usage();
            return false;
        }
        // last arg is filename and check if the file exists
        filename = args[args.length - 1];
        File file = new File(filename);
        if (!file.exists()) {
            //System.err.println("The file does not exist.");
            usage();
            return false;
        }
        // Check the file content is ended with an EOL


        for (int i = 0; i < args.length - 1; i++) {
            String opt = args[i];
            switch (opt) {
                case "-s":
                    if (i + 1 >= args.length - 1) {
                        // -s without parameter
                        usage();
                        return false;
                    }
                    try {
                        i++;
                        int m = Integer.parseInt(args[i]);
                        if (m != 0 && m != 1) {
                            // invalid integer parameter
                            usage();
                            return false;
                        }
                        skipMode = m;
                    } catch (NumberFormatException e) {
                        // invalid non-integer parameter
                        usage();
                        return false;
                    }
                    break;
                case "-x":
                    removeEmpty = true;
                    break;
                case "-g":
                    globalReplace = true;
                    break;
                case "-r":
                    // Need two parameters
                    if (i + 2 >= args.length - 1) {
                        // incomplete parameters of -r
                        usage();
                        return false;
                    }
                    oldStr = args[++i];
                    newStr = args[++i];
                    if (oldStr.isEmpty()) {
                        // invalid first parameter
                        usage();
                        return false;
                    }
                    replaceOption = true;
                    break;
                case "-w":
                    if (truncateLen != null) {
                        // -t is already there and -w is not allowed
                        usage();
                        return false;
                    }
                    if (i + 1 >= args.length - 1) {
                        // parameter is missing for -w
                        usage();
                        return false;
                    }
                    whitespaceMode = args[++i];
                    if (!whitespaceMode.equals("leading")
                            && !whitespaceMode.equals("trailing")
                            && !whitespaceMode.equals("all")) {
                        // invalid parameter for -w
                        usage();
                        return false;
                    }
                    break;
                case "-t":
                    if (whitespaceMode != null) {
                        // -w is already there and -t is not allowed
                        usage();
                        return false;
                    }
                    if (i + 1 >= args.length - 1) {
                        // parameter is missing for -t
                        usage();
                        return false;
                    }
                    try {
                        int value = Integer.parseInt(args[++i]);
                        if (value < 0 || value > 100) {
                            // invalid parameter for -t
                            usage();
                            return false;
                        }
                        truncateLen = value;
                    } catch (NumberFormatException e) {
                        // invalid parameter for -t
                        usage();
                        return false;
                    }
                    break;
                default:
                    // invalid option
                    usage();
                    return false;
            }
        }
        // Specifying option -g without -r.
        if (globalReplace == true && replaceOption == false) {
            usage();
            return false;
        }

        return true; // valid options and ready to be executed
    }

    private static void usage() {
        //System.err.println(
        //        "Usage: transformtxt [ -s number | -x | -g | -r old new | -t num | -w ] FILE");
        System.err.println(
                "Usage: transformtxt [ -s number | -x | -g | -r old new | -t num | -w spacing ] FILE");

    }

    private static void transformTxt() {
        if (!endsWithNewline(filename)) {
            // error
            usage();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;

                if (skipMode != null) {
                    // -s option is on
                    if (lineNum % 2 == skipMode.intValue()) {
                        // skip either even or odd line depending on the value of skipMode.
                        continue;
                    }
                }

                if (removeEmpty && line.length() == 0) {
                    // -x option is on and the empty line shall be removed.
                    continue;
                }

                if (replaceOption) {
                    // -r option is on.
                    if (globalReplace) {
                        // -g option is on. Replace all occurences
                        line = line.replace(oldStr, newStr);
                    }
                    else {
                        // no -g option and replace only the first occurence
                        int index = line.indexOf(oldStr);
                        if (index != -1) {
                            // Found the first oldStr in the line.
                            line = line.substring(0, index)
                                    + newStr
                                    + line.substring(index + oldStr.length());
                        }
                    }
                }

                if (whitespaceMode != null) {
                    // -w option is on.
                    switch (whitespaceMode) {
                        case "leading":
                            line = line.replaceAll("^\\s+", "");
                            break;
                        case "trailing":
                            line = line.replaceAll("\\s+$", "");
                            break;
                        case "all":
                            line = line.replaceAll("\\s+", "");
                            break;
                    }
                } else if (truncateLen != null) {
                    if (line.length() > truncateLen.intValue()) {
                        line = line.substring(0, truncateLen.intValue());
                    }
                    // nothing to be truncated if the line.length() <= truncateLen.
                }
                // Output the transformed line
                System.out.println(line);
            }
        }
        catch (IOException e) {
            // Any I/O error (including missing newline at end)
            usage();
        }
    }

}
