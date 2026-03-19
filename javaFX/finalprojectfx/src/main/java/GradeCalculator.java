/*
Class that has the methods that deal with manipulating the user data, method that does the calculations
*/

public class GradeCalculator {

    //method for calculating a needed grade on an assignment to achieve a desired final grade
    public static double gradeCalc(double current, double weight, int want){
        double percentGrade = (want - ((1 - (weight / 100.0)) * current)) / (weight / 100.0);
        Validate.gradeRangeChecker(percentGrade);
        return percentGrade;
    }

    //method for determining the letter grade associated to the calculated percentGrade from the method gradeCalc()
    public static String letterCalc(double percent){
        String letter;
        if (percent >= 90) {
            letter = "A";
        } else if (percent >= 80) {
            letter = "B" ;
        } else if (percent >= 70) {
            letter = "C";
        } else if (percent >= 60) {
            letter = "D" ;
        } else {
            letter = "F";
        }
        return letter;
    }

    //method for styling (changing color) of the Label 'results', based on pass/fail
    public static String getPercentStyling(double percent) {
        if (percent >= 60.0) {
            return "-fx-text-fill: green;";
        } else {
            return "-fx-text-fill: red;";
        }
    }
}
