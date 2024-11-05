package com.acme.traverser.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindTextActionTest {

    private FindTextAction action;

    @BeforeEach
    public void setup() {
        action = new FindTextAction("sample");
    }

    @Test
    public void testExecute() throws IOException {
        //Setup
        File tempFile = Files.createTempFile("test", ".txt").toFile();
        File outputFile = new File("find_text_output.txt");

        try {
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("This is a sample text.");
            }
            //Execute
            action.execute(tempFile);
            action.writeOutput(outputFile.getName());
            //Verify
            assertTrue(outputFile.exists(), "Output file should exist after execution.");
        } finally {
            tempFile.delete();
            outputFile.delete();
        }
    }
}
