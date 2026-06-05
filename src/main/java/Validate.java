import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Validates and sanitizes user input from the application UI.
 */
class Validate {
    private final String courseName;
    private final String assignmentName;
    private final String dropMenuSelect;
    private final String userGradeString;
    private final String examWeightString;
    private final String userWantedGradeString;

    /**
     * Reads and stores user input from the UI controls.
     *
     * @param course     the text field for class name
     * @param assignment the text field for assignment name
     * @param drop       the combo box for assignment type
     * @param grade      the text field for current grade
     * @param exam       the text field for assignment weight
     * @param user       the text field for desired final grade
     */
    public Validate(final TextField course, final TextField assignment, final ComboBox<String> drop, final TextField grade, final TextField exam, final TextField user) {
        this.courseName = course.getText().trim().toUpperCase();
        fileNameChecker(this.courseName);
        this.assignmentName = assignment.getText();
        this.dropMenuSelect = drop.getValue();
        this.userGradeString = grade.getText();
        this.examWeightString = exam.getText();
        this.userWantedGradeString = user.getText();
    }

    /**
     * Returns the normalized course name from the input field.
     *
     * @return the course name in uppercase
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Returns the entered assignment name.
     *
     * @return the assignment name text
     */
    public String getAssignmentName() {
        return assignmentName;
    }

    /**
     * Returns the selected assignment type from the dropdown.
     *
     * @return the selected assignment type
     */
    public String getDropMenuSelect() {
        return dropMenuSelect;
    }

    /**
     * Returns the current grade as a parsed double.
     *
     * @return the user's current grade
     */
    public double getUserGradeString() {
        return Double.parseDouble(userGradeString);
    }

    /**
     * Returns the assignment weight as a parsed double.
     *
     * @return the assignment weight percentage
     */
    public double getExamWeightString() {
        return Double.parseDouble(examWeightString);
    }

    /**
     * Returns the desired final grade as a parsed integer.
     *
     * @return the user's target final grade
     */
    public int getUserWantedGradeString() {
        return Integer.parseInt(userWantedGradeString);
    }

    /**
     * Checks that none of the required input fields are empty.
     *
     * @param input   the validated user input container
     * @param results the label used to display error messages
     * @return true when all fields are present and valid, otherwise false
     */
    public static boolean nullChecker(final Validate input, final Label results) {
        if (input.courseName.isEmpty() ||
            input.assignmentName.isEmpty() ||
            input.dropMenuSelect == null ||
            input.userGradeString.isEmpty() ||
            input.examWeightString.isEmpty() ||
            input.userWantedGradeString.isEmpty()) {
            results.setText("Please fill in all fields properly");
            return false;
        }
        return true;
    }

    /**
     * Validates the calculated percentage and throws when it is out of allowable range.
     *
     * @param percentGrade the calculated assignment percentage
     */
    public static void gradeRangeChecker(final double percentGrade) {
        if (percentGrade > 100.0) {
            throw new IllegalArgumentException("Not possible with current grade, will need over 100% on final.");
        } else if (percentGrade < 0) {
            throw new IllegalArgumentException("Not possible, will need a negative percent on the final.");
        }
    }

    /**
     * Ensures the course name contains only valid file-name characters.
     *
     * @param courseName the sanitized course name to validate
     */
    public static void fileNameChecker(final String courseName) {
        if (!courseName.matches("[a-zA-Z0-9_-]+")) {
            throw new IllegalArgumentException("Invalid file name. Only letters, numbers, underscores, and dashes are allowed.");
        }
    }
}
