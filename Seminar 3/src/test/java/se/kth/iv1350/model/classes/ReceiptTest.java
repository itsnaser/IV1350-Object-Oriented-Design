package se.kth.iv1350.model.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.model.dto.SaleDTO;
import se.kth.iv1350.model.dto.SaleItemDTO;
import se.kth.iv1350.model.dto.ItemDTO;
import se.kth.iv1350.model.dto.PaymentDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Receipt} class.
 * <p>
 * This test class verifies the correct behavior of the Receipt class,
 * including:
 * <ul>
 * <li>Correct retrieval of the associated {@link SaleDTO} via
 * {@code getSale()}.</li>
 * <li>Proper formatting and inclusion of sale items, VAT, total, payment, and
 * change in the string representation.</li>
 * <li>Correct formatting of the sale date and time in the receipt output.</li>
 * <li>Graceful handling of null sale information in the receipt.</li>
 * <li>Proper display of discounts, including when discounts are zero or
 * null.</li>
 * </ul>
 * <p>
 * The tests use fixed data for repeatability and clarity.
 */
class ReceiptTest {
  private SaleDTO saleDTO;
  private Receipt receipt;

  @BeforeEach
  void setUp() {
    ItemDTO item1 = new ItemDTO(1, "Milk", 10.0, 12);
    ItemDTO item2 = new ItemDTO(2, "Bread", 20.0, 6);
    SaleItemDTO saleItem1 = new SaleItemDTO(item1, 2);
    SaleItemDTO saleItem2 = new SaleItemDTO(item2, 1);
    List<SaleItemDTO> saleItems = List.of(saleItem1, saleItem2);

    double totalVAT = (item1.price() * 2 * item1.VAT() / 100.0) + (item2.price() * 1 * item2.VAT() / 100.0);
    PaymentDTO payment = new PaymentDTO(40.0, 50.0, 10.0);

    saleDTO = new SaleDTO(
        saleItems,
        new Date(1710000000000L), // fixed date for test
        0, // discounts
        totalVAT,
        payment);
    receipt = new Receipt(saleDTO);
  }

  @Test
  void testGetSaleReturnsCorrectSale() {
    assertEquals(saleDTO, receipt.getSale());
  }

  @Test
  void testToStringContainsSaleItems() {
    String receiptStr = receipt.toString();
    assertTrue(receiptStr.contains("Milk"));
    assertTrue(receiptStr.contains("Bread"));
    assertTrue(receiptStr.contains("Total VAT"));
    assertTrue(receiptStr.contains("Total"));
    assertTrue(receiptStr.contains("Cash"));
    assertTrue(receiptStr.contains("Change"));
  }

  @Test
  void testToStringContainsDateTime() {
    String receiptStr = receipt.toString();
    assertTrue(
        receiptStr.contains("Time of Sale: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(saleDTO.datetime())));
  }

  @Test
  void testToStringWithNullSale() {
    Receipt nullReceipt = new Receipt(null);
    assertEquals("No sale information available.", nullReceipt.toString());
  }

  @Test
  void testToStringWithNullDiscounts() {
    String receiptStr = receipt.toString();
    assertTrue(receiptStr.contains("- 0,00 SEK"));
  }
}