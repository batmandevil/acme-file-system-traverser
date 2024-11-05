package com.acme.traverser.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LastModifiedActionTest {

    private LastModifiedAction action;
    private File tempFile;

    @BeforeEach
    public void setup() throws IOException {
        action = new LastModifiedAction();
        tempFile = Files.createTempFile("testLastModified", ".txt").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Test content for last modified action.");
        }
    }

    @Test
    public void testExecuteRecordsLastModifiedDate() {
        // Execute
        action.execute(tempFile);
        List<String> records = action.getFileRecords();
        // Verify
        assertEquals(1, records.size(), "There should be exactly one record.");
        assertTrue(records.get(0).contains(tempFile.getAbsolutePath()), "The record should contain the file path.");
    }

    @Test
    public void testWriteOutputCreatesOutputFile() throws IOException {
        // Execute
        action.execute(tempFile);
        String outputFileName = "last_modified_output.txt";
        action.writeOutput(outputFileName);

        // Verify
        File outputFile = new File(outputFileName);
        assertTrue(outputFile.exists(), "Output file should exist.");

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line = reader.readLine();
            assertTrue(line.contains(tempFile.getAbsolutePath()), "Output file should contain the file path.");
        } finally {
            outputFile.delete();
        }
    }
}
