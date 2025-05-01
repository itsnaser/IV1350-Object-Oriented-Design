package se.kth.iv1350.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.integration.*;
import se.kth.iv1350.model.classes.*;
import se.kth.iv1350.model.dto.*;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ControllerTest {
  private Controller controller;
  private DiscountDBHandler mockDiscountDBHandler;
  private InventorySys mockInventorySys;
  private AccountingSys mockAccountingSys;
  private Printer mockPrinter;
  private ItemDTO testItem;

  @BeforeEach
  void setUp() {
    mockDiscountDBHandler = mock(DiscountDBHandler.class);
    mockInventorySys = mock(InventorySys.class);
    mockAccountingSys = mock(AccountingSys.class);
    mockPrinter = mock(Printer.class);
    controller = new Controller(mockDiscountDBHandler, mockInventorySys, mockAccountingSys, mockPrinter);

    testItem = new ItemDTO(1, "Milk", 10.0, 12);
  }

  /**
   * Tests that starting a new sale initializes the sale correctly in the
   * controller.
   * Verifies that calling {@code endSale()} after {@code startNewSale()} does not
   * throw any exceptions,
   * indicating that the sale was properly initialized and can be ended without
   * errors.
   */
  @Test
  void testStartNewSaleInitializesSale() {
    controller.startNewSale();
    assertDoesNotThrow(() -> controller.endSale());
  }

  @Test
  void testEndSaleReturnsTotalPrice() {
    controller.startNewSale();
    when(mockInventorySys.getItem(1)).thenReturn(testItem);
    controller.scanItem(1, 2);
    double total = controller.endSale();
    assertEquals(Math.round(2 * 10.0 * 1.12 * 100.00) / 100.00, total);
  }

  @Test
  void testEndSaleThrowsIfNoSale() {
    assertThrows(IllegalStateException.class, () -> controller.endSale());
  }

  @Test
  void testScanItemAddsItemAndReturnsDTO() {
    controller.startNewSale();
    when(mockInventorySys.getItem(1)).thenReturn(testItem);
    ItemDTO returned = controller.scanItem(1, 1);
    assertEquals(testItem, returned);
  }

  @Test
  void testScanItemThrowsIfItemNotFound() {
    controller.startNewSale();
    when(mockInventorySys.getItem(2)).thenReturn(null);
    assertThrows(IllegalArgumentException.class, () -> controller.scanItem(2, 1));
  }

  @Test
  void testScanItemThrowsIfQuantityNotPositive() {
    controller.startNewSale();
    when(mockInventorySys.getItem(1)).thenReturn(testItem);
    assertThrows(IllegalArgumentException.class, () -> controller.scanItem(1, 0));
  }

  @Test
  void testSignalDiscountRequestAppliesDiscounts() {
    controller.startNewSale();
    when(mockInventorySys.getItem(1)).thenReturn(testItem);
    controller.scanItem(1, 1);

    double discount = 0.0;
    when(mockDiscountDBHandler.getDiscounts(anyInt()))
        .thenReturn(discount);

    Sale sale = controller.signalDiscountRequest(123);
    assertNotNull(sale);
    // The sale should have discounts applied and total price should be
    // ((price * VAT) - discount)
    double discountedTotal = sale.getTotalPrice();
    System.out.println("Discounted total: " + discountedTotal);
    assertTrue(discountedTotal == Math.round(10.0 * 1.12 * 100.00) / 100.00,
        "Discounted total: " + discountedTotal);
  }

  @Test
  void testSignalDiscountRequestThrowsIfNoSale() {
    assertThrows(IllegalStateException.class, () -> controller.signalDiscountRequest(1));
  }

  @Test
  void testSetAmountPaidReturnsCorrectChange() {
    controller.startNewSale();
    when(mockInventorySys.getItem(1)).thenReturn(testItem);
    controller.scanItem(1, 1);
    double total = controller.endSale();
    double change = controller.setAmountPaid(total + 5.0);
    assertEquals(5.0, change);
  }

  @Test
  void testSetAmountPaidThrowsIfNoSale() {
    assertThrows(IllegalStateException.class, () -> controller.setAmountPaid(100));
  }

  @Test
  void testSetAmountPaidThrowsIfNegative() {
    controller.startNewSale();
    assertThrows(IllegalArgumentException.class, () -> controller.setAmountPaid(-1));
  }

  @Test
  void testCompleteSaleCallsExternalSystems() {
    controller.startNewSale();
    when(mockInventorySys.getItem(1)).thenReturn(testItem);
    controller.scanItem(1, 1);
    controller.completeSale();

    verify(mockPrinter, atLeastOnce()).printReceipt(any(Receipt.class));
    verify(mockAccountingSys, atLeastOnce()).sendSaleInfo(any(SaleDTO.class));
    verify(mockInventorySys, atLeastOnce()).updateInventory(any(SaleDTO.class));
  }
}