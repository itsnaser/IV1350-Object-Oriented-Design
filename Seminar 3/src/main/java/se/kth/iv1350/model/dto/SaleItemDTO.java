package se.kth.iv1350.model.dto;

import se.kth.iv1350.model.classes.SaleItem;

/**
 * Represents a data transfer object (DTO) for an item in a sale,
 * including the item details and the quantity sold.
 *
 * @param item     The {@link ItemDTO} representing the item being sold.
 * @param quantity The number of units of the item being sold. Must be
 *                 non-negative.
 * @throws IllegalArgumentException if {@code quantity} is negative.
 */
public record SaleItemDTO(ItemDTO item, int quantity) {
  /**
   * Constructs a new {@code SaleItemDTO} with the specified item and quantity.
   *
   * @param item     The {@link ItemDTO} representing the item being sold.
   * @param quantity The number of units of the item being sold. Must be
   *                 non-negative.
   * @throws IllegalArgumentException if {@code quantity} is negative.
   */

  public SaleItemDTO(SaleItem saleItem) {
    this(saleItem.getItem(), saleItem.getQuantity());
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity must be non-negative.");
    }
  }

}
