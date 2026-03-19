/*
    Class that holds the calculated information based off the user's input
*/

public class GradeResults {
    //Class to create the Grade object
    private String className;
    private String assignmentName;
    private String assignmentType;
    private double userGrade;
    private double examWeight;
    private int userWantedGrade;
    private double grade;
    private String letterGrade;

    //method that constructs the objects with the given values
    public GradeResults (
        String className,
        String assignmentName,
        String assignmentType,
        double userGrade,
        double examWeight,
        int userWantedGrade,
        double grade,
        String letterGrade) {
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
    public String toString() {
        return
                "For your " + className + " \"" + assignmentName + "\" " + assignmentType +
                "\nYou need to get a(n): " + String.format("%.2f%%",grade) +
                "\nWhich is a(n): " + letterGrade +
                "\nFor a final grade of: " + userWantedGrade;
    }

    //method for formatting the output to be saved to a file
    public String toFile() {
        return
                "Class Type: " + className +
                " | Assignment Name: " + assignmentName +
                " | Assignment Type: " + assignmentType +
                " | Current Grade: " + String.format("%.2f%%",userGrade) +
                " | Needed Grade: " + String.format("%.2f%%",grade) +
                " | Letter Grade: " + letterGrade +
                " | Target Grade: " + userWantedGrade;
    }
}
