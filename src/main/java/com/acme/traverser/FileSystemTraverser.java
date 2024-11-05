package com.acme.traverser;

import com.acme.traverser.actions.*;

import java.io.File;
import java.io.IOException;

public class FileSystemTraverser {

    private final FileAction action;

    public FileSystemTraverser(FileAction action) {
        this.action = action;
    }

    public void traverse(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    traverse(subFile); //recursively traverse sub dir
                }
            }
        } else {
            action.execute(file);
        }
    }

    public void writeOutput(String outputFileName) throws IOException {
        action.writeOutput(outputFileName);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java -jar acmeTraverser.jar <path> <action>");
            return;
        }

        final var path = args[0];
        final var actionType = args[1];
        FileAction action;
        String outputFileName;

        switch (actionType.toLowerCase()) {
            case "findtext":
                if (args.length < 3) {
                    System.out.println("Please provide the text to search for as the third argument.");
                    return;
                }
                String searchText = args[2];
                action = new FindTextAction(searchText);
                outputFileName = "secondary_action.txt";
                break;
            case "lastmodified":
            default:
                action = new LastModifiedAction();
                outputFileName = "file_list.txt";
                break;
        }

        FileSystemTraverser traverser = new FileSystemTraverser(action);
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            System.out.println("The specified path does not exist: " + path);
            return;
        }
        traverser.traverse(rootFile);
        traverser.writeOutput(outputFileName);
    }
}
