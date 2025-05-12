package se.kth.iv1350.integration;

import se.kth.iv1350.model.dto.SaleDTO;

/**
 * Represents the external accounting system integration.
 * Responsible for sending sale information to the accounting system.
 */
public class AccountingSys {

  /**
   * Sends sale information to the accounting system.
   *
   * @param saleDTO The {@link SaleDTO} containing information about the completed
   *                sale.
   */
  public void sendSaleInfo(SaleDTO saleDTO) {
    System.out.println("Sending sale information to accounting system...");
  }
}
