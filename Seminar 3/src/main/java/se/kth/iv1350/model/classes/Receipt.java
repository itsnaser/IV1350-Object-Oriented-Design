package se.kth.iv1350.model.classes;

import java.text.SimpleDateFormat;

import se.kth.iv1350.model.dto.ItemDTO;
import se.kth.iv1350.model.dto.SaleDTO;
import se.kth.iv1350.model.dto.SaleItemDTO;

/**
 * Represents a receipt for a completed sale.
 * Contains the sale information as a {@link SaleDTO}.
 */
public class Receipt {
  private SaleDTO sale;

  /**
   * Creates a new {@code Receipt} for the specified sale.
   *
   * @param sale The {@link SaleDTO} containing information about the completed
   *             sale.
   */
  public Receipt(SaleDTO sale) {
    this.sale = sale;
  }

  /**
   * Returns the sale information associated with this receipt.
   *
   * @return The {@link SaleDTO} for this receipt.
   */
  public SaleDTO getSale() {
    return sale;
  }

  /**
   * Returns a string representation of the receipt, including sale items,
   * discounts, VAT, payment, and the date/time of the sale.
   *
   * @return A string describing the receipt and its sale information.
   */
  @Override
  public String toString() {
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

    SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return "\n------------------" + String.format("%-14s", " Begin receipt") + " ------------------\n" +
        "Time of Sale: " + datetimeFormat.format(sale.datetime()) + "\n\n" +
        itemsInfo.toString() + "\n" +
        String.format("%-28s", "Discount: ") + String.format("-%5.2f", sale.discount()) + " SEK\n" +
        String.format("%-29s", "Total VAT: ") + String.format("%5.2f", sale.totalVAT()) + " SEK\n\n" +
        sale.payment().toString() + "\n" +
        "------------------" + String.format("%-14s", " End receipt") + "------------------\n";
  }
}
