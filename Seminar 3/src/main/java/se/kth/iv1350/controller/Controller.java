package se.kth.iv1350.controller;

import se.kth.iv1350.integration.*;
import se.kth.iv1350.model.classes.*;
import se.kth.iv1350.model.dto.*;
import java.util.List;

/**
 * The Controller class coordinates the interaction between the view and the
 * model.
 * It handles the main operations of a sale, such as scanning items, applying
 * discounts,
 * processing payments, and updating external systems.
 */
public class Controller {

  private final DiscountDBHandler dDBHandler;
  private final InventorySys invSys;
  private final AccountingSys accSys;
  private final Printer printer;
  private Sale sale;

  /**
   * Creates a new {@code Controller} with the specified system handlers.
   *
   * @param dDBHandler The {@link DiscountDBHandler} for managing discounts.
   * @param invSys     The {@link InventorySys} for inventory management.
   * @param accSys     The {@link AccountingSys} for accounting integration.
   * @param printer    The {@link Printer} for printing receipts.
   */
  public Controller(DiscountDBHandler dDBHandler, InventorySys invSys, AccountingSys accSys, Printer printer) {
    this.dDBHandler = dDBHandler;
    this.invSys = invSys;
    this.accSys = accSys;
    this.printer = printer;
    this.sale = null;
  }

  /**
   * Starts a new sale.
   */
  public void startNewSale() {
    this.sale = new Sale();
  }

  /**
   * Ends the current sale and returns the total price.
   *
   * @return The total price of the sale.
   * @throws IllegalStateException if no sale is in progress.
   */
  public double endSale() {
    if (sale == null) {
      throw new IllegalStateException("No sale in progress.");
    }
    return sale.getTotalPrice();
  }

  /**
   * Scans an item and adds it to the current sale.
   *
   * @param itemID   The unique identifier of the item to scan.
   * @param quantity The quantity of the item to add.
   * @return The {@link ItemDTO} representing the scanned item.
   * @throws IllegalArgumentException if the item is not found or quantity is not
   *                                  positive.
   */
  public ItemDTO scanItem(int itemID, int quantity) {
    ItemDTO item = invSys.getItem(itemID);
    if (item == null) {
      throw new IllegalArgumentException("Item not found in inventory.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero.");
    }
    sale.addItem(item, quantity);
    printScannedItem(item);
    return item;
  }

  /**
   * Prints information about a scanned item, including its details, the current
   * total cost
   * (including VAT), and the total VAT for the sale so far.
   *
   * @param item The {@link ItemDTO} representing the item that was just scanned.
   */
  private void printScannedItem(ItemDTO item) {
    System.out.println(item.toString() + "\n");
    System.out.println("Total cost (incl VAT): " + String.format("%.2f", sale.getTotalPrice()) + " SEK");
    System.out.println("Total VAT: " + String.format("%.2f", sale.getTotalVAT()) + " SEK" + "\n");
  }

  /**
   * Requests and applies discounts for the current sale based on the customer ID.
   *
   * @param customerID The unique identifier of the customer.
   * @return The {@link Sale} after applying discounts.
   * @throws IllegalStateException if no sale is in progress.
   */
  public Sale signalDiscountRequest(int customerID) {
    if (sale == null) {
      throw new IllegalStateException("No sale in progress.");
    }
    SaleDTO saleDTO = new SaleDTO(sale);
    double customerDiscounts = dDBHandler.getDiscounts(customerID); // percentage discounts
    double itemsDiscounts = dDBHandler.getDiscounts(saleDTO); // fixed discounts
    double totalPriceDiscounts = dDBHandler.getDiscounts(sale.getTotalPrice()); // percentage discounts

    this.sale.addFixedDiscount(itemsDiscounts);
    this.sale.addPercentageDiscount(customerDiscounts);
    this.sale.addPercentageDiscount(totalPriceDiscounts);
    return sale;
  }

  /**
   * Sets the amount paid by the customer, completes the sale, and returns the
   * change.
   *
   * @param amountPaid The amount paid by the customer.
   * @return The change to be returned to the customer.
   * @throws IllegalStateException    if no sale is in progress.
   * @throws IllegalArgumentException if the amount paid is negative.
   */
  public double setAmountPaid(double amountPaid) {
    if (sale == null) {
      throw new IllegalStateException("No sale in progress.");
    }
    if (amountPaid < 0) {
      throw new IllegalArgumentException("Amount paid cannot be negative.");
    }
    double change = sale.getAmountChange(amountPaid);
    completeSale();
    return change;
  }

  /**
   * Completes the sale by printing the receipt, sending sale info to the
   * accounting system,
   * and updating the inventory.
   */
  public void completeSale() {
    SaleDTO saleDTO = new SaleDTO(sale);
    Receipt receipt = new Receipt(saleDTO);
    this.printer.printReceipt(receipt);
    this.accSys.sendSaleInfo(saleDTO);
    this.invSys.updateInventory(saleDTO);
  }
}
