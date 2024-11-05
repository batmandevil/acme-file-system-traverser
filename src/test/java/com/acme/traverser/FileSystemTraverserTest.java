package com.acme.traverser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FileSystemTraverserTest {

    private Path testDir;
    private FileAction mockAction;

    @BeforeEach
    public void setUp() throws IOException {
        testDir = Files.createTempDirectory("testDir");
        Files.createFile(testDir.resolve("file1.txt"));
        Files.createFile(testDir.resolve("file2.txt"));
        Files.createDirectories(testDir.resolve("subDir"));
        Files.createFile(testDir.resolve("subDir/file3.txt"));
        mockAction = mock(FileAction.class);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walk(testDir)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    public void testTraverseAndExecute() throws IOException {
        // Setup
        FileSystemTraverser traverser = new FileSystemTraverser(mockAction);
        // Execute
        traverser.traverse(testDir.toFile());
        // Verify
        verify(mockAction, times(3)).execute(any(File.class));
        verify(mockAction).execute(new File(testDir.resolve("file1.txt").toString()));
        verify(mockAction).execute(new File(testDir.resolve("file2.txt").toString()));
        verify(mockAction).execute(new File(testDir.resolve("subDir/file3.txt").toString()));
    }

    @Test
    public void testMainMethodWithValidInput() throws IOException {
        // Setup
        String[] args = {testDir.toString(), ""}; //use default action
        // Execute
        FileSystemTraverser.main(args);
        // Verify
        File outputFile = new File("file_list.txt");
        assertTrue(outputFile.exists(), "Output file should be created.");

        List<String> lines = Files.readAllLines(outputFile.toPath());
        assertTrue(lines.size() > 0, "Output file should have content.");

        Files.delete(outputFile.toPath());
    }

    @Test
    public void testMainMethodWithInvalidPath() throws IOException {
        // Setup
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        String[] args = {"/invalid/path", "lastmodified"};
        // Execute
        FileSystemTraverser.main(args);
        System.setOut(originalOut);

        // Verify
        String output = outputStream.toString().trim();
        assertTrue(output.contains("The specified path does not exist"), "Output should indicate the invalid path.");

        File outputFile = new File("file_list.txt");
        assertFalse(outputFile.exists(), "Output file should not be created for invalid path.");
    }
}
