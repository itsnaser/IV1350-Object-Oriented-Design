package se.kth.iv1350.integration;

import java.text.SimpleDateFormat;

import se.kth.iv1350.model.classes.Receipt;
import se.kth.iv1350.model.dto.ItemDTO;
import se.kth.iv1350.model.dto.SaleDTO;
import se.kth.iv1350.model.dto.SaleItemDTO;

/**
 * Represents the external printer integration.
 * Responsible for printing receipts for completed sales.
 */
public class Printer {

  /**
   * Constructs a formatted receipt string based on the provided {@link Receipt}
   * object.
   * The receipt includes item details (description, quantity, unit price
   * including VAT, total price per item),
   * the time of sale, applied discount, total VAT, payment information, and
   * receipt delimiters.
   *
   * @param receipt The {@link Receipt} object containing sale and payment
   *                information.
   * @return A formatted string representing the complete receipt, or a message if
   *         sale information is unavailable.
   */
  private String constructReceiptInfo(Receipt receipt) {
    SaleDTO sale = receipt.getSale();
    if (sale == null) {
      return "No sale information available.";
    }
    StringBuilder itemsInfo = new StringBuilder();
    for (SaleItemDTO saleItem : sale.saleItems()) {
      ItemDTO item = saleItem.item();
      double price = item.price() * saleItem.quantity() * (1 + (item.VAT() / 100.0));
      itemsInfo.append(String.format("%-9s", item.description()))
          .append(String.format("%5d", saleItem.quantity()))
          .append(" x ")
          .append(String.format("%5.2f    ", item.price() * (1 + (item.VAT() / 100.0))))
          .append(String.format("%8.2f", price))
          .append(" SEK (incl. VAT)")
          .append("\n");
    }
    String paymentInfo = String.format("%-26s", "Total(incl. VAT):")
        + String.format("%8.2f", sale.payment().totalPrice()) + " SEK\n" +
        String.format("%-26s", "Cash:") + String.format("%8.2f", sale.payment().amountPaid()) + " SEK\n" +
        String.format("%-26s", "Change:") + String.format("%8.2f", sale.payment().change()) + " SEK";

    SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return "\n------------------" + String.format("%-14s", " Begin receipt") + " ------------------\n" +
        "Time of Sale: " + datetimeFormat.format(sale.datetime()) + "\n\n" +
        itemsInfo.toString() + "\n" +
        String.format("%-28s", "Discount: ") + String.format("-%5.2f", sale.discount()) + " SEK\n" +
        String.format("%-29s", "Total VAT: ") + String.format("%5.2f", sale.totalVAT()) + " SEK\n\n" +
        paymentInfo + "\n" +
        "------------------" + String.format("%-14s", " End receipt") + "------------------\n";
  }

  /**
   * Prints the receipt for the sale.
   *
   * @param receipt The {@link Receipt} to be printed.
   */
  public void printReceipt(Receipt receipt) {
    // Simulate printing the receipt
    System.out.println("Printing receipt...");
    System.out.println(constructReceiptInfo(receipt));
  }
}