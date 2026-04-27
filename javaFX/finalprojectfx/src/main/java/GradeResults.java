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
                "\nYou need to get a(n): " + String.format("%.2f%%",grade) +
                "\nWhich is a(n): " + letterGrade +
                "\nFor a final grade of: " + userWantedGrade +
                "\nWith a percent weight of: " + String.format("%.2f%%",examWeight);
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
}
