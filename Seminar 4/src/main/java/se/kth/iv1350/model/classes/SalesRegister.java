package se.kth.iv1350.model.classes;

/**
 * Represents the sales register that keeps track of the total amount of money
 * present.
 */
public class SalesRegister {
  private double presentAmount;

  /**
   * Creates a new {@code SalesRegister} with the present amount initialized to
   * zero.
   */
  public SalesRegister() {
    this.presentAmount = 0.0;
  }

  /**
   * Updates the register by adding the specified amount to the present amount.
   *
   * @param amount The amount to add to the register. Must be non-negative.
   * @throws IllegalArgumentException if {@code amount} is negative.
   */
  public void updateRegister(double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount cannot be negative.");
    }
    this.presentAmount += amount;
  }

  /**
   * Returns the current amount present in the register.
   *
   * @return The present amount in the register.
   */
  public double getPresentAmount() {
    return presentAmount;
  }
}
