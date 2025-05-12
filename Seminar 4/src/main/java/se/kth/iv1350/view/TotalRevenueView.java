package se.kth.iv1350.view;

import se.kth.iv1350.model.classes.ISaleObserver;

/**
 * Displays and tracks the total revenue from all completed sales.
 */
public class TotalRevenueView implements ISaleObserver {
  private double totalRevenue;

  /**
   * Creates a new instance of TotalRevenueView with initial revenue set to zero.
   */
  public TotalRevenueView() {
    this.totalRevenue = 0;
  }

  /**
   * Updates the total revenue with the amount of the completed sale and prints
   * the updated total.
   *
   * @param totalAmountOfSale The total amount paid for the completed sale.
   */
  @Override
  public void updateRevenue(double totalAmountOfSale) {
    this.totalRevenue += totalAmountOfSale;
    printTotalRevenue();
  }

  /**
   * Prints the current total revenue to the standard output.
   *
   * @return void
   */
  private void printTotalRevenue() {
    System.out.println("Total revenue: " + totalRevenue + " SEK");
  }

}
