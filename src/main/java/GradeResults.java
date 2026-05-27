/*
    Class that holds the calculated information based off the user's input
*/

public class GradeResults {
    //Class to create the Grade object
    private final String className;
    private final String assignmentName;
    private final String assignmentType;
    private final double userGrade;
    private final double examWeight;
    private final int userWantedGrade;
    private final double grade;
    private final String letterGrade;

    //method that constructs the objects with the given values
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

    //accessor method for getting the className value
    public String getClassName(){
        return className;
    }

    //method for formatting output to be displayed on the UI
    @Override
    public String toString() {
        return
                "For your " + className + " \"" + assignmentName + "\" " + assignmentType +
                " you need to get a(n): " + String.format("%.2f%%",grade) +
                "\nWhich is a(n): " + letterGrade +
                "\nFor a final grade of: " + userWantedGrade + "%" +
                " with a percent weight of: " + String.format("%.2f%%",examWeight);
    }

    //method for formatting the output to be saved to a file
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

    // Static parser method to reconstruct GradeResults from pipe-delimited file format
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
