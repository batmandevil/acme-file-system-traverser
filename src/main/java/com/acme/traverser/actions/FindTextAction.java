package com.acme.traverser.actions;

import com.acme.traverser.FileAction;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FindTextAction implements FileAction {

    private final String searchText;
    private final StringBuilder matches = new StringBuilder();

    public FindTextAction(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public void execute(File file) throws IOException {
        String content = Files.readString(file.toPath());
        if (content.contains(searchText)) {
            matches.append("Found in: ").append(file.getAbsolutePath()).append("\n");
        }
    }

    @Override
    public void writeOutput(String outputFilePath) throws IOException {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write(matches.toString());
        }
    }
}
