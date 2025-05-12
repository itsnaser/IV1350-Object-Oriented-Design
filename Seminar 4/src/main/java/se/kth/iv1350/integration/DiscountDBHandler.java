package se.kth.iv1350.integration;

import se.kth.iv1350.model.dto.DiscountDTO;
import se.kth.iv1350.model.dto.SaleDTO;
import se.kth.iv1350.model.dto.SaleItemDTO;

import java.util.List;
import java.util.ArrayList;

/**
 * Handles access to the discount database.
 * Responsible for storing and retrieving applicable discounts for sales and
 * customers.
 */
public class DiscountDBHandler {
  private static DiscountDBHandler instance;
  private List<DiscountDTO> discountList;

  private DiscountDBHandler() {
    this.discountList = new ArrayList<>();
    discountList.add(new DiscountDTO(1, 1, -1, -1, 0, 10, true)); // 10% item discount
    discountList.add(new DiscountDTO(2, 2, -1, -1, 0, 10, true)); // 10% item discount
    discountList.add(new DiscountDTO(3, 3, -1, -1, 0, 10, true)); // 10% item discount
    discountList.add(new DiscountDTO(4, 4, -1, -1, 0, 10, true)); // 10% item discount

    discountList.add(new DiscountDTO(5, -1, 1, -1, 0.0, 10, true)); // 10% customer discount
    discountList.add(new DiscountDTO(6, -1, 2, -1, 0.0, 10, true)); // 10% customer discount

    discountList.add(new DiscountDTO(7, -1, -1, 100, 0.0, 10, true)); // 10% total price discount
    discountList.add(new DiscountDTO(8, -1, -1, 50, 0.0, 10, true)); // 10% total price discount
  }

  /**
   * Returns the singleton instance of {@code DiscountDBHandler}.
   * Ensures only one instance exists throughout the application.
   *
   * @return The singleton instance of {@code DiscountDBHandler}.
   */
  public static DiscountDBHandler getInstance() {
    if (instance == null) {
      instance = new DiscountDBHandler();
    }
    return instance;
  }

  /**
   * Retrieves a list of applicable discounts for the given sale.
   * Discounts are applicable if they match the item ID in the sale.
   *
   * @param sale The {@link SaleDTO} representing the current sale.
   * @return The total discount amount to be reduced from the total cost of the
   *         entire sale.
   */
  public double getDiscounts(SaleDTO sale) {
    double totalDiscount = 0.0;
    for (SaleItemDTO saleItem : sale.saleItems()) {
      // Simulate fetching discounts from the database based on items
      for (DiscountDTO discount : discountList) {
        if (discount.itemID() == saleItem.item().itemID()) {
          double itemPrice = saleItem.item().price() * saleItem.quantity() * (1 + (saleItem.item().VAT() / 100.0));
          totalDiscount += itemPrice * discount.percentageDiscount() / 100.0;
        }
      }
    }
    return Math.round(totalDiscount * 100.0) / 100.0;
  }

  /**
   * Retrieves all percentage discounts applicable for the given total cost.
   * This is a simple example that returns a fixed percentage based on thresholds.
   *
   * @param totalCost The total cost of the sale.
   * @return The sum of percentage discounts to be reduced from this total cost.
   */
  public double getDiscounts(double totalCost) {
    double totalDiscount = 1.00;
    // Simulate fetching discounts from the database based on total cost
    for (DiscountDTO discount : discountList) {
      if (totalCost >= discount.totalPrice() && discount.totalPrice() > 0) {
        totalDiscount *= 1 - (discount.percentageDiscount() / 100.0);
      }
    }
    return Math.round((1 - totalDiscount) * 100.0);
  }

  /**
   * Retrieves all percentage discounts applicable for the given customer ID.
   *
   * @param customerID The unique identifier of the customer.
   * @return The sum of percentage discounts to be reduced from the total cost of
   *         the entire sale.
   */
  public double getDiscounts(int customerID) {
    double totalDiscount = 1.0;
    // Simulate fetching discounts from the database based on customer ID
    for (DiscountDTO discount : discountList) {
      if (discount.customerID() == customerID) {
        totalDiscount *= 1 - (discount.percentageDiscount() / 100.0);
      }
    }
    return Math.round((1 - totalDiscount) * 100);
  }
}
