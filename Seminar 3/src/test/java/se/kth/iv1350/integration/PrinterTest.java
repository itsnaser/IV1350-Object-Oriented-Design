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
  void testPrintReceipt_WithNullSale_PrintsNoSaleInfo() {
    // Arrange: mock Receipt to return null for getSale()
    when(mockReceipt.getSale()).thenReturn(null);

    // Act
    printer.printReceipt(mockReceipt);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("No sale information available."), "Should print message for missing sale info");
  }

  @Test
  void testPrintReceipt_WithSale_PrintsFormattedReceipt() {
    // Arrange: create mocks for SaleDTO, SaleItemDTO, ItemDTO, and payment
    var mockSale = mock(se.kth.iv1350.model.dto.SaleDTO.class);
    var mockSaleItem = mock(se.kth.iv1350.model.dto.SaleItemDTO.class);
    var mockItem = mock(se.kth.iv1350.model.dto.ItemDTO.class);
    var mockPayment = mock(se.kth.iv1350.model.dto.PaymentDTO.class);

    when(mockReceipt.getSale()).thenReturn(mockSale);
    when(mockSale.saleItems()).thenReturn(java.util.List.of(mockSaleItem));
    when(mockSaleItem.item()).thenReturn(mockItem);
    when(mockSaleItem.quantity()).thenReturn(2);
    when(mockItem.description()).thenReturn("Milk");
    when(mockItem.price()).thenReturn(10.0);
    when(mockItem.VAT()).thenReturn(12);
    when(mockSale.payment()).thenReturn(mockPayment);
    when(mockPayment.totalPrice()).thenReturn(22.4);
    when(mockPayment.amountPaid()).thenReturn(30.0);
    when(mockPayment.change()).thenReturn(7.6);
    when(mockSale.discount()).thenReturn(0.0);
    when(mockSale.totalVAT()).thenReturn(2.4);
    when(mockSale.datetime()).thenReturn(new java.util.Date(0)); // 1970-01-01 00:00:00

    // Act
    printer.printReceipt(mockReceipt);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("Printing receipt..."));
    assertTrue(output.contains("Milk"), "Should contain item description");
    assertTrue(output.contains("Total(incl. VAT):"), "Should contain total price label");
    assertTrue(output.contains("Time of Sale:"), "Should contain time of sale");
    assertTrue(output.contains("Discount:"), "Should contain discount info");
    assertTrue(output.contains("Total VAT:"), "Should contain VAT info");
    assertTrue(output.contains("Cash:"), "Should contain cash info");
    assertTrue(output.contains("Change:"), "Should contain change info");
    assertTrue(output.contains("Begin receipt"), "Should contain receipt start delimiter");
    assertTrue(output.contains("End receipt"), "Should contain receipt end delimiter");
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }
}