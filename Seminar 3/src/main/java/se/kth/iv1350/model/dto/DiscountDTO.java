package se.kth.iv1350.model.dto;

/**
 * Represents a data transfer object (DTO) for a discount.
 * Contains information about the discount ID, item ID, customer ID, fixed
 * discount amount,
 * percentage discount, and whether the discount is activated.
 *
 * @param discountID         The unique identifier for the discount. Must be
 *                           non-negative.
 * @param itemID             The unique identifier for the item to which the
 *                           discount applies. Must be non-negative.
 * @param customerID         The unique identifier for the customer eligible for
 *                           the discount. Must be non-negative.
 * @param fixedDiscount      The fixed discount amount. Must be non-negative.
 * @param percentageDiscount The percentage discount to be applied. Must be
 *                           between 0 and 100.
 * @param activated          Indicates whether the discount is currently
 *                           activated.
 * @throws IllegalArgumentException if any ID or fixedDiscount is negative, or
 *                                  if percentageDiscount is not between 0 and
 *                                  100.
 */
public record DiscountDTO(
    int discountID,
    int itemID,
    int customerID,
    double totalPrice,
    double fixedDiscount,
    int percentageDiscount,
    boolean activated) {
  /**
   * Constructs a new {@code DiscountDTO} with the specified parameters.
   *
   * @param discountID         The unique identifier for the discount. Must be
   *                           non-negative.
   * @param itemID             The unique identifier for the item to which the
   *                           discount applies. Must be non-negative.
   * @param customerID         The unique identifier for the customer eligible for
   *                           the discount. Must be non-negative.
   * @param totalPrice         Discount for sales with total price more than a
   *                           specific price.
   * @param fixedDiscount      The fixed discount amount. Must be non-negative.
   * @param percentageDiscount The percentage discount to be applied. Must be
   *                           between 0 and 100.
   * @param activated          Indicates whether the discount is currently
   *                           activated.
   * @throws IllegalArgumentException if any ID or fixedDiscount is negative, or
   *                                  if percentageDiscount is not between 0 and
   *                                  100.
   */
  public DiscountDTO {
    if (discountID < 0) {
      throw new IllegalArgumentException("Discount ID cannot be negative.");
    }

    if (fixedDiscount < 0) {
      throw new IllegalArgumentException("Fixed discount cannot be negative.");
    }
    if (percentageDiscount < 0 || percentageDiscount > 100) {
      throw new IllegalArgumentException("Percentage discount must be between 0 and 100.");
    }

  }

  public boolean isFixedDiscount() {
    return fixedDiscount > 0;
  }

  public boolean isPercentageDiscount() {
    return percentageDiscount > 0;
  }

}
