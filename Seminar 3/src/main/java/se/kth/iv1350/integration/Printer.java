package se.kth.iv1350.integration;

import se.kth.iv1350.model.classes.Receipt;

/**
 * Represents the external printer integration.
 * Responsible for printing receipts for completed sales.
 */
public class Printer {

  /**
   * Prints the receipt for the sale.
   *
   * @param receipt The {@link Receipt} to be printed.
   */
  public void printReceipt(Receipt receipt) {
    // Simulate printing the receipt
    System.out.println("Printing receipt...");
    System.out.println(receipt.toString());

  }
}