package se.kth.iv1350.systemout;

import org.junit.jupiter.api.*;
import se.kth.iv1350.model.dto.*;
import se.kth.iv1350.model.classes.*;
import se.kth.iv1350.integration.*;
import se.kth.iv1350.view.*;
import se.kth.iv1350.controller.*;
import se.kth.iv1350.logger.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for all methods that print to System.out in the program.
 */
class SystemOutTest {
  private final PrintStream originalOut = System.out;
  private ByteArrayOutputStream outContent;

  /**
   * Redirects System.out to a {@link ByteArrayOutputStream} before each test.
   */
  @BeforeEach
  void setUpStreams() {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  /**
   * Restores System.out to its original stream after each test.
   */
  @AfterEach
  void restoreStreams() {
    System.setOut(originalOut);
  }

  // --- AccountingSys ---
  /**
   * Verifies that {@link AccountingSys#sendSaleInfo(SaleDTO)} prints the correct
   * message.
   */
  @Test
  void sendSaleInfo_shouldPrintMessage() {
    AccountingSys accountingSys = new AccountingSys();
    SaleDTO mockSaleDTO = mock(SaleDTO.class);
    accountingSys.sendSaleInfo(mockSaleDTO);
    assertTrue(outContent.toString().contains("Sending sale information to accounting system"));
  }

  // --- Printer ---
  /**
   * Verifies that {@link Printer#printReceipt(Receipt)} prints the correct
   * receipt information.
   */
  @Test
  void printReceipt_shouldPrintReceiptInfo() {
    Printer printer = new Printer();
    SaleDTO saleDTO = mock(SaleDTO.class);
    PaymentDTO paymentDTO = mock(PaymentDTO.class);
    when(saleDTO.saleItems()).thenReturn(Collections.emptyList());
    when(saleDTO.payment()).thenReturn(paymentDTO);
    when(saleDTO.discount()).thenReturn(9.9);
    when(saleDTO.totalVAT()).thenReturn(0.0);
    when(saleDTO.datetime()).thenReturn(new Date());
    when(paymentDTO.totalPrice()).thenReturn(100.0);
    when(paymentDTO.amountPaid()).thenReturn(100.0);
    when(paymentDTO.change()).thenReturn(0.0);

    Receipt receipt = new Receipt(saleDTO);
    printer.printReceipt(receipt);
    String discount = "Discount:                   - 9,90 SEK";
    String total = "Total(incl. VAT):           100,00 SEK";
    String cash = "Cash:                       100,00 SEK";
    String change = "Change:                       0,00 SEK";

    String output = outContent.toString();
    assertTrue(output.contains(discount));
    assertTrue(output.contains(cash));
    assertTrue(output.contains(change));
    assertTrue(output.contains(total));
    assertTrue(output.contains("Printing receipt"));
    assertTrue(output.contains("Begin receipt"));
    assertTrue(output.contains("End receipt"));
  }

  // --- InventorySys ---
  /**
   * Verifies that {@link InventorySys#updateInventory(SaleDTO)} prints the
   * correct message.
   */
  @Test
  void updateInventory_shouldPrintUpdatingInventory() {
    InventorySys invSys = InventorySys.getInstance();
    SaleDTO saleDTO = mock(SaleDTO.class);
    when(saleDTO.saleItems()).thenReturn(Collections.emptyList());
    invSys.updateInventory(saleDTO);
    assertTrue(outContent.toString().contains("Updating inventory with sale information"));
  }

  /**
   * Verifies that {@link InventorySys#printInventory()} prints the current
   * inventory.
   */
  @Test
  void printInventory_shouldPrintCurrentInventory() {
    InventorySys invSys = InventorySys.getInstance();
    invSys.printInventory();
    String output = outContent.toString();
    String expectedOutput = "1: Apple - 34 units available.";
    String expectedOutput2 = "2: Banana - 57 units available.";
    String expectedOutput3 = "3: Orange - 21 units available.";
    String expectedOutput4 = "4: Milk - 88 units available.";
    String expectedOutput5 = "5: Bread - 49 units available.";
    assertTrue(output.contains("Current inventory:"));
    assertTrue(output.contains(expectedOutput));
    assertTrue(output.contains(expectedOutput2));
    assertTrue(output.contains(expectedOutput3));
    assertTrue(output.contains(expectedOutput4));
    assertTrue(output.contains(expectedOutput5));
  }

  // --- TotalRevenueView ---
  /**
   * Verifies that {@link TotalRevenueView#updateRevenue(double)} prints the total
   * revenue.
   */
  @Test
  void totalRevenueView_updateRevenue_shouldPrintTotalRevenue() {
    TotalRevenueView view = new TotalRevenueView();
    view.updateRevenue(123.45);
    assertTrue(outContent.toString().contains("Total revenue: 123.45 SEK"));
  }

  // --- Startup main method ---
  /**
   * Verifies that the startup main method prints the starting application
   * message.
   * 
   * @throws Exception if reflection or main method invocation fails
   */
  @Test
  void startupMain_shouldPrintStartingApplication() throws Exception {
    try {
      se.kth.iv1350.startup.Startup.main(new String[0]);
    } catch (Exception ignored) {
      // Ignore exceptions from the rest of the startup
    }
    assertTrue(outContent.toString().contains("Starting the application"));
  }

  // --- View class ---
  /**
   * Verifies that scanning an item prints the correct item information.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void view_printScannedItem_shouldPrintItemInfo() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    ItemDTO item = mock(ItemDTO.class);
    when(item.itemID()).thenReturn(1);
    when(item.description()).thenReturn("Apple");
    when(item.price()).thenReturn(10.0);
    when(item.VAT()).thenReturn(25);
    when(controller.scanItem(anyInt(), anyInt())).thenReturn(item);

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("printScannedItem", int.class, int.class);
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view, 1, 1);

    String output = outContent.toString();
    assertTrue(output.contains("Apple"));
    assertTrue(output.contains("VAT: 25%"));
  }

  /**
   * Verifies that the correct message is printed when an item is not found.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void printScannedItem_shouldPrintNoItemFoundMessage_whenItemNotFoundException() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    when(controller.scanItem(eq(99), anyInt()))
        .thenThrow(new se.kth.iv1350.exceptions.ItemNotFoundException("No item found"));

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("printScannedItem", int.class, int.class);
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view, 99, 1);

    String output = outContent.toString();
    assertTrue(output.contains("No item found with ID: 99. Please try another Item."));
  }

  /**
   * Verifies that the correct message is printed when an inventory database error
   * occurs.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void printScannedItem_shouldPrintInventoryDatabaseError_whenInventoryDatabaseException() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    when(controller.scanItem(eq(1), anyInt()))
        .thenThrow(new se.kth.iv1350.exceptions.InventoryDatabaseException("DB error"));

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("printScannedItem", int.class, int.class);
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view, 1, 1);

    String output = outContent.toString();
    assertTrue(output.contains("Could not connect to the inventory database. Please try again later."));
  }

  /**
   * Verifies that the correct message is printed when an unexpected exception
   * occurs.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void printScannedItem_shouldPrintUnexpectedError_whenOtherException() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    when(controller.scanItem(eq(2), anyInt()))
        .thenThrow(new RuntimeException("Something went wrong"));

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("printScannedItem", int.class, int.class);
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view, 2, 1);

    String output = outContent.toString();
    assertTrue(output.contains("An unexpected error occurred: Something went wrong"));
  }

  /**
   * Verifies that the basic sale flow prints the sale ended and total price
   * message.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void view_basicFlow_shouldPrintSaleEndedAndTotalPrice() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    when(controller.scanItem(anyInt(), anyInt())).thenReturn(mock(ItemDTO.class));
    when(controller.endSale()).thenReturn(42.0);
    when(controller.setAmountPaid(anyDouble())).thenReturn(58.0);

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("basicFlow");
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view);

    String output = outContent.toString();
    assertTrue(output.contains("Sale ended. Total price:"));
  }

  /**
   * Verifies that the alternative flow 3-4a prints the correct messages.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void view_altFlow_3_4_a_shouldPrintNoItemFoundMessage() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    when(controller.scanItem(eq(99), anyInt()))
        .thenThrow(new se.kth.iv1350.exceptions.ItemNotFoundException("No item found"));
    when(controller.scanItem(eq(69), anyInt())).thenReturn(mock(ItemDTO.class));
    when(controller.scanItem(eq(2), anyInt())).thenReturn(mock(ItemDTO.class));
    when(controller.endSale()).thenReturn(100.0);
    when(controller.setAmountPaid(anyDouble())).thenReturn(0.0);

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("altFlow_3_4_a");
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view);

    String output = outContent.toString();
    assertTrue(output.contains("No item found with ID: 99. Please try another Item."));
    assertTrue(output.contains("Sale ended. Total price:"));
  }

  /**
   * Verifies that the alternative flow 3-4b prints the sale ended and total price
   * message.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void view_altFlow_3_4_b_shouldPrintSaleEndedAndTotalPrice() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    when(controller.scanItem(anyInt(), anyInt())).thenReturn(mock(ItemDTO.class));
    when(controller.endSale()).thenReturn(200.0);
    when(controller.setAmountPaid(anyDouble())).thenReturn(0.0);

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("altFlow_3_4_b");
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view);

    String output = outContent.toString();
    assertTrue(output.contains("Sale ended. Total price:"));
  }

  /**
   * Verifies that the alternative flow 3-4c prints the sale ended and total price
   * message.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void view_altFlow_3_4_c_shouldPrintSaleEndedAndTotalPrice() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    when(controller.scanItem(anyInt(), anyInt())).thenReturn(mock(ItemDTO.class));
    when(controller.endSale()).thenReturn(300.0);
    when(controller.setAmountPaid(anyDouble())).thenReturn(0.0);

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("altFlow_3_4_c");
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view);

    String output = outContent.toString();
    assertTrue(output.contains("Sale ended. Total price:"));
  }

  /**
   * Verifies that the alternative flow 9a prints the discount and new total
   * price.
   * 
   * @throws Exception if reflection fails
   */
  @Test
  void view_altFlow_9_a_shouldPrintDiscountAndNewTotal() throws Exception {
    Controller controller = mock(Controller.class);
    ErrorLogger logger = mock(ErrorLogger.class);
    when(controller.scanItem(anyInt(), anyInt())).thenReturn(mock(ItemDTO.class));
    when(controller.endSale()).thenReturn(400.0, 350.0);
    when(controller.setAmountPaid(anyDouble())).thenReturn(0.0);

    View view = new View(controller, logger);

    var method = View.class.getDeclaredMethod("altFlow_9_a");
    method.setAccessible(true);
    outContent.reset();
    method.invoke(view);

    String output = outContent.toString();
    assertTrue(output.contains("Discounts requested and applied"));
    assertTrue(output.contains("New Total price: 350,00 SEK"));
  }

}