# ACME File System Traverser Framework

## Overview

The ACME File System Traverser is a Java-based framework designed to traverse a specified file system path and perform various actions on each file encountered. This framework supports pluggable actions, allowing new functionality to be easily integrated without modifying the core codebase.

### Features

- **Default Action**: The default action records the last modified date and file path of each file encountered in the file system.
- **Pluggable Actions**: Additional actions can be implemented by extending the `FileAction` interface. Predefined action include finding specified text within files.
- **Output Handling**: The framework outputs results to specified files, including a comma-delimited file containing last modified dates and paths (`file_list.txt`), and a secondary action output file (`secondary_action.txt`).

## Design

### Structure

The framework is structured around the following key components:

- **FileSystemTraverser**: The main class responsible for traversing directories and invoking specified actions on files.
- **FileAction**: An interface that defines a contract for actions that can be performed on files. Specific actions implement this interface.
- **Action Implementations**: Each action (e.g., `LastModifiedAction`, `FindTextAction`, etc.) is implemented as a separate class that defines the specific functionality.

### Control Flow

1. The user specifies the file system path and action type via command line arguments.
2. The `FileSystemTraverser` initializes the specified action and starts traversing the directory structure recursively.
3. For each file encountered, the action's `execute` method is called, performing the defined operation.
4. Results are written to output files as specified by the framework.

## Assumptions

- The framework assumes that the provided file path exists and is accessible. If the path is invalid, an error message is displayed, and the program exits gracefully.
- The actions implemented are expected to handle their own file-specific logic, including file I/O and error handling.
- The user has the necessary permissions to read the files and directories being traversed.
- The framework is designed to be extensible. Users can add new actions by implementing the `FileAction` interface and modifying the action selection logic in the `FileSystemTraverser` class accordingly.

## Usage

### Running the Application

To run the ACME File System Traverser, compile the project and execute the JAR file with the following command:

```bash
java -jar acmeTraverser.jar <path_to_directory> <action> [searchText]
