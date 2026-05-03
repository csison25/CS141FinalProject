import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for result persistence operations.
 * Handles file I/O for deleting results and parsing existing result files.
 */
public class ResultPersistence {

    /**
     * Deletes a specific result from the file associated with its class name.
     * If deletion fails (permissions, file locked), throws exception to allow caller to show error dialog.
     *
     * @param record The GradeResults to delete
     * @throws Exception if file operation fails
     */
    public static void deleteResultFromFile(GradeResults record) throws Exception {
        try {
            String filename = record.getClassName() + ".txt";
            Path filePath = Paths.get(filename);

            // Check if file exists
            if (!Files.exists(filePath)) {
                throw new Exception("Result file not found: " + filename);
            }

            // Read all lines, filter out the matching record
            String recordFileFormat = record.toFile();
            List<String> lines = Files.readAllLines(filePath);
            List<String> filteredLines = lines.stream()
                    .filter(line -> !line.equals(recordFileFormat))
                    .collect(Collectors.toList());

            // Write back filtered lines
            if (filteredLines.isEmpty()) {
                // If no results left, delete the file
                Files.delete(filePath);
            } else {
                // Otherwise write the remaining results
                Files.write(filePath, filteredLines);
            }
        } catch (Exception e) {
            throw new Exception("Failed to delete result from file: " + e.getMessage(), e);
        }
    }

    /**
     * Parses an existing result file and returns all GradeResults found in it.
     * Skips malformed lines gracefully without throwing.
     *
     * @param filename The filename to read (e.g., "CS141.txt")
     * @return List of GradeResults parsed from the file
     * @throws Exception if file cannot be read
     */
    public static List<GradeResults> parseExistingResultsFile(String filename) throws Exception {
        try {
            Path filePath = Paths.get(filename);

            if (!Files.exists(filePath)) {
                throw new Exception("File not found: " + filename);
            }

            List<String> lines = Files.readAllLines(filePath);
            List<GradeResults> results = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                try {
                    GradeResults result = GradeResults.fromString(line);
                    if (result != null) {
                        results.add(result);
                    }
                } catch (Exception e) {
                    // Skip malformed lines; log them optionally
                    System.err.println("Skipping malformed result line: " + line);
                }
            }

            return results;
        } catch (Exception e) {
            throw new Exception("Failed to parse results file: " + e.getMessage(), e);
        }
    }

    /**
     * Clears all results from a file by deleting it or truncating to empty.
     *
     * @param className The class name (used to derive filename)
     * @throws Exception if file operation fails
     */
    public static void clearResultsFile(String className) throws Exception {
        try {
            String filename = className + ".txt";
            Path filePath = Paths.get(filename);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            throw new Exception("Failed to clear results file: " + e.getMessage(), e);
        }
    }
}
