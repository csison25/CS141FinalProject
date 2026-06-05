import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        );
    }
}