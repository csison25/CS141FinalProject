import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ValidateTest {

    @Test
    void validFileName() {
        assertDoesNotThrow(
            () -> Validate.fileNameChecker("CIS141")
        );
    }

    @Test
    void invalidFileName() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Validate.fileNameChecker("CIS/141")
        ).getMessage();
    }
}