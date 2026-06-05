import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(
                IllegalArgumentException.class,
                () -> GradeCalculator.gradeCalc(50, 10, 100)
    );
    }
    @Test
    void calculateNeededGradeValidCase() {
        double result = GradeCalculator.gradeCalc(80, 50, 90);

        assertEquals(100.0, result, 0.01);
}
}