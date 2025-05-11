package se.kth.iv1350.model.classes;

import se.kth.iv1350.model.dto.SaleDTO;

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
}
