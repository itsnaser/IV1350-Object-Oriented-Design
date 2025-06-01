package se.kth.iv1350.view;

import se.kth.iv1350.model.classes.TotalRevenueTemplate;

/**
 * Displays and tracks the total revenue from all completed sales.
 */
public class TotalRevenueView extends TotalRevenueTemplate {

  @Override
  public void doShowTotalIncome() throws Exception {
    printTotalRevenue();
  }

  public void handleErrors(Exception e) {
    System.out.println("An error occurred: " + e.getMessage());
  }

  /**
   * Prints the current total revenue to the standard output.
   *
   * @return void
   */
  private void printTotalRevenue() {
    System.out.println("Total revenue: " + this.totalRevenue + " SEK");
  }

}
