package com.acme.traverser;

import java.io.File;
import java.io.IOException;

public interface FileAction {
    void execute(File file) throws IOException;
    void writeOutput(String outputFilePath) throws IOException;
}
