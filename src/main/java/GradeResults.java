/**
 * Holds calculated grade result details generated from user input.
 */
public class GradeResults {
    // Data fields for a calculated grade result
    private final String className;
    private final String assignmentName;
    private final String assignmentType;
    private final double userGrade;
    private final double examWeight;
    private final int userWantedGrade;
    private final double grade;
    private final String letterGrade;

    /**
     * Creates a GradeResults record from calculated values.
     *
     * @param className       the class name
     * @param assignmentName  the assignment name
     * @param assignmentType  the assignment type
     * @param userGrade       the user's current grade
     * @param examWeight      the assignment weight percentage
     * @param userWantedGrade the desired final grade
     * @param grade           the calculated needed grade
     * @param letterGrade     the letter grade for the calculated value
     */
    public GradeResults(
        final String className,
        final String assignmentName,
        final String assignmentType,
        final double userGrade,
        final double examWeight,
        final int userWantedGrade,
        final double grade,
        final String letterGrade) {
            this.className = className;
            this.assignmentName = assignmentName;
            this.assignmentType = assignmentType;
            this.userGrade = userGrade;
            this.examWeight = examWeight;
            this.userWantedGrade = userWantedGrade;
            this.grade = grade;
            this.letterGrade = letterGrade;
    }

    /**
     * accessor method for getting the className value
     * @return className String object
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns a multi-line string for UI display of this grade result.
     *
     * @return the formatted display string for this result
     */
    @Override
    public String toString() {
        return
                "For your " + className + " \"" + assignmentName + "\" " + assignmentType +
                " you need to get a(n): " + String.format("%.2f%%",grade) +
                "\nWhich is a(n): " + letterGrade +
                "\nFor a final grade of: " + userWantedGrade + "%" +
                " with a percent weight of: " + String.format("%.2f%%",examWeight);
    }

    /**
     * Formats this grade result for persistence to a text file.
     *
     * @return the file-format string for this result
     */
    public String toFile() {
        return
                "Class Type: " + className +
                " | Assignment Name: " + assignmentName +
                " | Assignment Type: " + assignmentType +
                " | Percent Weight: " + String.format("%.2f%%",examWeight) +
                " | Current Grade: " + String.format("%.2f%%",userGrade) +
                " | Needed Grade: " + String.format("%.2f%%",grade) +
                " | Letter Grade: " + letterGrade +
                " | Target Grade: " + userWantedGrade;
    }

    /**
     * Parses a GradeResults object from a single file line produced by {@link #toFile()}.
     *
     * @param fileLine a single line from the saved results file
     * @return the parsed GradeResults instance
     * @throws Exception if the line format is invalid
     */
    public static GradeResults fromString(String fileLine) throws Exception {
        try {
            String[] parts = fileLine.split(" \\| ");
            if (parts.length != 8) {
                throw new Exception("Invalid format: expected 8 pipe-delimited fields");
            }

            // Parse each field, extracting the value after the colon
            String className = parts[0].split(": ", 2)[1].trim();
            String assignmentName = parts[1].split(": ", 2)[1].trim();
            String assignmentType = parts[2].split(": ", 2)[1].trim();
            double examWeight = Double.parseDouble(parts[3].split(": ", 2)[1].trim().replace("%", ""));
            double userGrade = Double.parseDouble(parts[4].split(": ", 2)[1].trim().replace("%", ""));
            double grade = Double.parseDouble(parts[5].split(": ", 2)[1].trim().replace("%", ""));
            String letterGrade = parts[6].split(": ", 2)[1].trim();
            int userWantedGrade = Integer.parseInt(parts[7].split(": ", 2)[1].trim());

            return new GradeResults(className, assignmentName, assignmentType, userGrade,
                    examWeight, userWantedGrade, grade, letterGrade);
        } catch (Exception e) {
            throw new Exception("Failed to parse GradeResults from file format: " + e.getMessage(), e);
        }
    }
}
