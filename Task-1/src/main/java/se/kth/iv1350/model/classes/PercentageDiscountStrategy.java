package se.kth.iv1350.model.classes;

import se.kth.iv1350.model.interfaces.IDiscountStrategy;

/**
 * A discount strategy that applies a percentage-based discount to the total
 * price.
 */
public class PercentageDiscountStrategy implements IDiscountStrategy {
  private final double percentage;

  /**
   * Creates a new instance with the specified discount percentage.
   *
   * @param percentage The percentage of the discount to be applied (e.g., 10 for
   *                   10%).
   */
  public PercentageDiscountStrategy(double percentage) {
    this.percentage = percentage;
  }

  /**
   * Applies the percentage discount to the given total price.
   *
   * @param totalPrice The original total price before discount.
   * @return The discount amount to be subtracted from the total price.
   */
  @Override
  public double applyDiscount(double totalPrice) {
    return totalPrice * percentage / 100;
  }
}
