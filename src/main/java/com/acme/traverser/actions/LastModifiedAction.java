package com.acme.traverser.actions;

import com.acme.traverser.FileAction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LastModifiedAction implements FileAction {

    private final List<String> fileRecords = new ArrayList<>();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(File file) {
        if (file.isFile()) {
            FileTime lastModified = FileTime.fromMillis(file.lastModified());
            String formattedDate = DATE_FORMAT.format(new Date(lastModified.toMillis()));
            String record = file.getAbsolutePath() + "," + formattedDate;
            fileRecords.add(record);
        }
    }

    @Override
    public void writeOutput(String outputFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String record : fileRecords) {
                writer.write(record);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFileRecords() {
        return fileRecords;
    }
}
