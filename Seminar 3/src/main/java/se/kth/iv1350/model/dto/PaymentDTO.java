package se.kth.iv1350.model.dto;

/**
 * Represents a data transfer object (DTO) for payment information in a sale.
 * Contains the total price, the amount paid by the customer, and the calculated
 * change.
 *
 * @param totalPrice The total price to be paid for the sale. Must be
 *                   non-negative.
 * @param amountPaid The amount paid by the customer. Must be non-negative and
 *                   greater than or equal to {@code totalPrice}.
 * @param change     The change to be returned to the customer. Must be
 *                   non-negative and equal to {@code amountPaid - totalPrice}.
 * @throws IllegalArgumentException if any parameter is negative, if
 *                                  {@code amountPaid} is less than
 *                                  {@code totalPrice}, or if {@code change}
 *                                  does not equal
 *                                  {@code amountPaid - totalPrice}.
 */
public record PaymentDTO(double totalPrice, double amountPaid, double change) {
  /**
   * Constructs a new {@code PaymentDTO} with the specified total price, amount
   * paid, and change.
   *
   * @param totalPrice The total price to be paid for the sale. Must be
   *                   non-negative.
   * @param amountPaid The amount paid by the customer. Must be non-negative and
   *                   greater than or equal to {@code totalPrice}.
   * @param change     The change to be returned to the customer. Must be
   *                   non-negative and equal to {@code amountPaid - totalPrice}.
   * @throws IllegalArgumentException if any parameter is negative, if
   *                                  {@code amountPaid} is less than
   *                                  {@code totalPrice}, or if {@code change}
   *                                  does not equal
   *                                  {@code amountPaid - totalPrice}.
   */
  public PaymentDTO {
    if (totalPrice < 0 || amountPaid < 0 || change < 0) {
      throw new IllegalArgumentException("Total price, amount paid, and change must be non-negative.");
    }
    if (amountPaid < totalPrice) {
      throw new IllegalArgumentException("Amount paid must be greater than or equal to the total price.");
    }
    if (change != amountPaid - totalPrice) {
      throw new IllegalArgumentException("Change must be equal to amount paid minus total price.");
    }
  }

  /**
   * Constructs a new {@code PaymentDTO} with the specified total price and amount
   * paid.
   * The change will be calculated as {@code amountPaid - totalPrice}.
   *
   * @param totalPrice The total price to be paid for the sale. Must be
   *                   non-negative.
   * @param amountPaid The amount paid by the customer. Must be non-negative and
   *                   greater than or equal to {@code totalPrice}.
   * @throws IllegalArgumentException if any parameter is negative or if
   *                                  {@code amountPaid} is less than
   *                                  {@code totalPrice}.
   */
  public PaymentDTO(double totalPrice, double amountPaid) {
    this(totalPrice, amountPaid, amountPaid - totalPrice); // delegate to canonical
  }

  @Override
  public String toString() {
    return String.format("%-29s", "Total:") + String.format("%8.2f", totalPrice) + " SEK\n" +
        String.format("%-29s", "Cash:") + String.format("%8.2f", amountPaid) + " SEK\n" +
        String.format("%-29s", "Change:") + String.format("%8.2f", change) + " SEK";
  }
}
