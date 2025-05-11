package se.kth.iv1350.model.dto;

/**
 * Represents a data transfer object (DTO) for an item.
 * Contains the item ID, description, price, and VAT percentage.
 *
 * @param itemID      The unique identifier for the item. Must be non-negative.
 * @param description The description of the item. Cannot be null or empty.
 * @param price       The price of the item. Must be non-negative.
 * @param VAT         The value-added tax (VAT) percentage for the item. Must be
 *                    between 0 and 100.
 * @throws IllegalArgumentException if {@code itemID} or {@code price} is
 *                                  negative,
 *                                  if {@code description} is null or empty,
 *                                  or if {@code VAT} is not between 0 and 100.
 */
public record ItemDTO(int itemID, String description, double price, int VAT) {
  /**
   * Constructs a new {@code ItemDTO} with the specified item ID, description,
   * price, and VAT.
   *
   * @param itemID      The unique identifier for the item. Must be non-negative.
   * @param description The description of the item. Cannot be null or empty.
   * @param price       The price of the item. Must be non-negative.
   * @param VAT         The value-added tax (VAT) percentage for the item. Must be
   *                    between 0 and 100.
   * @throws IllegalArgumentException if {@code itemID} or {@code price} is
   *                                  negative,
   *                                  if {@code description} is null or empty,
   *                                  or if {@code VAT} is not between 0 and 100.
   */
  public ItemDTO {
    if (itemID < 0 || price < 0) {
      throw new IllegalArgumentException("Item ID, price, and VAT must be non-negative.");
    }
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Description cannot be null or empty.");
    }
    if (VAT < 0 || VAT > 100) {
      throw new IllegalArgumentException("VAT must be between 0 and 100.");
    }
  }
}
