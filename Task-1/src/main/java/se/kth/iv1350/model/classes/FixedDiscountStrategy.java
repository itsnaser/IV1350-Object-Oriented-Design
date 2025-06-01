package se.kth.iv1350.model.classes;

import se.kth.iv1350.model.interfaces.IDiscountStrategy;

/**
 * A discount strategy that applies a fixed discount amount to the total price.
 */
public class FixedDiscountStrategy implements IDiscountStrategy {
  private final double discount;

  /**
   * Creates a new instance with the specified fixed discount amount.
   *
   * @param discount The fixed discount amount to be applied.
   */
  public FixedDiscountStrategy(double discount) {
    this.discount = discount;
  }

  /**
   * Applies the fixed discount to the given total price.
   * The discount will not exceed the total price.
   *
   * @param totalPrice The original total price before discount.
   * @return The discount amount to be subtracted from the total price.
   */
  @Override
  public double applyDiscount(double totalPrice) {
    return Math.min(discount, totalPrice);
  }

}
