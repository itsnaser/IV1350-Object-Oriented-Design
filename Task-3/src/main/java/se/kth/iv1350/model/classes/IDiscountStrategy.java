package se.kth.iv1350.model.classes;

/**
 * Represents a strategy for applying a discount to a total price.
 * Implementations of this interface define specific discount algorithms.
 */
public interface IDiscountStrategy {
  double applyDiscount(double totalPrice);
}