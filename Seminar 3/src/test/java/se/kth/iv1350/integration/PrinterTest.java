package se.kth.iv1350.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.model.classes.Receipt;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Printer} class.
 * <p>
 * This test class verifies the behavior of the {@code printReceipt} method in
 * the {@code Printer} class.
 * It uses a mocked {@link Receipt} object to ensure that the receipt is printed
 * correctly and that
 * the {@code toString()} method of the receipt is called as expected.
 * <ul>
 * <li>{@code testPrintReceipt_PrintsExpectedOutput}: Ensures that the printer
 * outputs the expected
 * message and the receipt details when printing.</li>
 * </ul>
 * The test class also redirects {@code System.out} to capture printed output
 * for verification and
 * restores it after each test.
 */
class PrinterTest {

  private Printer printer;
  private Receipt mockReceipt;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    printer = new Printer();
    mockReceipt = mock(Receipt.class);
    System.setOut(new PrintStream(outContent));
  }

  @Test
  void testPrintReceipt_PrintsExpectedOutput() {
    String receiptString = "Receipt details here";
    when(mockReceipt.toString()).thenReturn(receiptString);

    printer.printReceipt(mockReceipt);

    String output = outContent.toString();
    assertTrue(output.contains("Printing receipt..."), "Should print 'Printing receipt...'");
    assertTrue(output.contains(receiptString), "Should print the receipt's toString()");
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }
}