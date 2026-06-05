

/**
 * Provides grade calculation utilities and styling helpers for the UI.
 */
public class GradeCalculator {
    private static final int A = 90;
    private static final int B = 80;
    private static final int C = 70;
    private static final int D = 60;
    private static final double dDecimal = 60.0;

    /**
     * Calculates the percent grade required on an assignment to reach the target final grade.
     *
     * @param current the user's current grade
     * @param weight  the assignment weight percentage
     * @param want    the desired final grade
     * @return the needed grade percentage for the assignment
     */
    public static double gradeCalc(final double current, final double weight, final int want) {
        final double percentGrade = (want - ((1 - (weight / 100.0)) * current)) / (weight / 100.0);
        Validate.gradeRangeChecker(percentGrade);
        return percentGrade;
    }

    /**
     * Converts a numeric percentage into a letter grade.
     *
     * @param percent the calculated assignment percentage
     * @return the corresponding letter grade
     */
    public static String letterCalc(final double percent) {
        final String letter;
        if (percent >= A) {
            letter = "A";
        } else if (percent >= B) {
            letter = "B" ;
        } else if (percent >= C) {
            letter = "C";
        } else if (percent >= D) {
            letter = "D" ;
        } else {
            letter = "F";
        }
        return letter;
    }

    /**
     * Returns a CSS text color style based on whether the percentage is a passing value.
     *
     * @param percent the percentage used to determine styling
     * @return a CSS style string for label text color
     */
    public static String getPercentStyling(final double percent) {
        if (percent >= dDecimal) {
            return "-fx-text-fill: green;";
        } else {
            return "-fx-text-fill: red;";
        }
    }
}
