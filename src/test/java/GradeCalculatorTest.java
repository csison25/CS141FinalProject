import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class GradeCalculatorTest {

    @Test
    void letterGradeA() {
        assertEquals("A",
                GradeCalculator.letterCalc(95));
    }

    @Test
    void letterGradeB() {
        assertEquals("B",
                GradeCalculator.letterCalc(85));
    }

    @Test
    void letterGradeF() {
        assertEquals("F",
                GradeCalculator.letterCalc(45));
    }
    @Test
    void impossibleGradeThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> GradeCalculator.gradeCalc(50, 10, 100)
        );
        assertEquals("Not possible with current grade, will need over 100% on final.",
                exception.getMessage());
    }

    @Test
    void calculateNeededGradeValidCase() {
        double result = GradeCalculator.gradeCalc(80, 50, 90);

        assertEquals(100.0, result, 0.01);
}
}