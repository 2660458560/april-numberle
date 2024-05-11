package com.mobaijun.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: [Text tool class]
 * Author: [mobaijun]
 * Date: [2024/5/7 14:05]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class TextUtil {

    /**
     * User directory
     */
    public static final String HOME_PROJECT_PATH = System.getProperty("user.dir");

    /**
     * File path
     */
    public static final String FILE_PATH = HOME_PROJECT_PATH + "/src/main/resources/equations.txt";

    /**
     * Reads text from a file.
     *
     * @return ArrayList<String> containing lines of text from the file.
     * @Precondition The file path specified should point to a valid text file.
     * The specified text file should exist and be accessible.
     * @Postcondition Returns an ArrayList<String> containing each line of text read from the file.
     * If successful, the returned ArrayList<String> contains the lines of text from the file.
     * If an IOException occurs during file reading, the method throws an IOException.
     * The IOException can be handled or propagated to the calling code for handling.
     */
    public static List<String> readTextFromFile() {
        List<String> lines = new LinkedList<>();

        // Read in the TXT file
        File file = new File(FILE_PATH);

        // Try-with-resources block ensures the BufferedReader is closed
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                // Read the next line of data
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
