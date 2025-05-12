package se.kth.iv1350.model.classes;

import se.kth.iv1350.model.dto.ItemDTO;

/**
 * Represents a sale item in the system, including item details and quantity.
 */
public class SaleItem {
  /**
   * The item information for this sale item.
   */
  private ItemDTO item;

  /**
   * The quantity of this item in the sale.
   */
  private int quantity;

  /**
   * Creates a new instance representing a sale item.
   *
   * @param item     The <code>ItemDTO</code> object that holds information about
   *                 the item.
   * @param quantity The number of items added to the sale.
   */
  public SaleItem(ItemDTO item, int quantity) {
    this.item = item;
    this.quantity = quantity;
  }

  /**
   * Gets the <code>ItemDTO</code> for this sale item.
   *
   * @return The <code>ItemDTO</code> of this sale item.
   */
  public ItemDTO getItem() {
    return item;
  }

  /**
   * Gets the quantity of this item in the sale.
   *
   * @return The quantity of this sale item.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Increase the quantity of this sale item.
   *
   * @param quantity The quantity to increase by.
   */
  public void increaseQuantity(int quantity) {
    this.quantity += quantity;
  }
}
