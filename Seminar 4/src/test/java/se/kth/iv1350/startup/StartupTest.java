package se.kth.iv1350.startup;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StartupTest {

  /**
   * Tests that the {@code Startup.main} method does not throw any exceptions when
   * invoked
   * with an empty array of arguments. This ensures that the application can start
   * without errors.
   */
  @Test
  void main_DoesNotThrowException() {
    assertDoesNotThrow(() -> Startup.main(new String[0]),
        "Startup.main should not throw any exceptions");
  }
}