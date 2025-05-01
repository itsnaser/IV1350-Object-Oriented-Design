package se.kth.iv1350.model.classes;

import se.kth.iv1350.model.dto.ItemDTO;
import se.kth.iv1350.model.dto.PaymentDTO;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a sale, containing information about the items sold, applied
 * discounts, payment, total VAT, and the date and time of the sale.
 */
public class Sale {
  private List<SaleItem> saleItems;
  private Date datetime;
  private double discount;
  private PaymentDTO payment;
  private double totalVAT;

  /**
   * Creates a new {@code Sale}.
   */
  public Sale() {
    this.saleItems = new ArrayList<>();
    this.datetime = null;
    this.discount = 0.0;
    this.payment = null;
    this.totalVAT = 0.0;
  }

  /**
   * Adds an item to the sale. If the item already exists in the sale, its
   * quantity is increased.
   *
   * @param itemView The {@link ItemDTO} representing the item to add.
   * @param quantity The quantity of the item to add.
   * @throws IllegalArgumentException if the quantity is less than or equal to 0.
   */
  public void addItem(ItemDTO itemView, int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero.");
    }
    SaleItem matchedSaleItem = isItemInSale(itemView);
    if (matchedSaleItem != null) {
      matchedSaleItem.increaseQuantity(quantity);
    } else {
      saleItems.add(new SaleItem(itemView, quantity));
    }
    updateTotalVAT();
  }

  /**
   * Applies a fixed discount to the total price.
   *
   * @param discount The fixed discount amount to apply.
   */
  public void addFixedDiscount(double discount) {
    if (discount >= getTotalPrice()) {
      this.discount = getTotalPrice();
      return;
    }
    this.discount += discount;
  }

  /**
   * Calculates and applies a percentage discount to the total price.
   *
   * @param discount The percentage discount to apply (e.g., 10 for 10%).
   */
  public void addPercentageDiscount(double discount) {
    this.discount += this.getTotalPrice() * discount / 100;
  }

  /**
   * Calculates and returns the change to be given to the customer based on the
   * amount paid. Also sets the payment information and the date/time of the sale
   * if not already set.
   *
   * @param amountPaid The amount paid by the customer.
   * @return The change to be returned to the customer.
   */
  public double getAmountChange(double amountPaid) {
    if (payment == null) {
      double totalPrice = getTotalPrice();
      this.payment = new PaymentDTO(totalPrice, amountPaid, amountPaid - totalPrice);
      this.datetime = new Date();
    }
    return this.payment.change();
  }

  /**
   * Calculates and returns the total price of the sale, including VAT and after
   * applying discounts.
   *
   * @return The total price of the sale.
   */
  public double getTotalPrice() {
    double totalPrice = 0;

    for (SaleItem saleItem : saleItems) {
      totalPrice += saleItem.getItem().price() * saleItem.getQuantity() * (1 + (saleItem.getItem().VAT() / 100.0));
    }
    return Math.round((totalPrice - discount) * 100.0) / 100.0;
  }

  /**
   * Updates the total VAT for the sale based on the items and their quantities.
   */
  private void updateTotalVAT() {
    double totalVAT = 0;
    for (SaleItem saleItem : saleItems) {
      totalVAT += saleItem.getItem().price() * saleItem.getItem().VAT() * saleItem.getQuantity() / 100.0;
    }
    this.totalVAT = totalVAT;
  }

  /**
   * Checks if the specified item is already included in the sale.
   *
   * @param item The {@link ItemDTO} to check.
   * @return The {@link SaleItem} if the item is already in the sale, otherwise
   *         {@code null}.
   */
  private SaleItem isItemInSale(ItemDTO item) {
    if (saleItems == null) {
      return null;
    }
    for (SaleItem saleItem : saleItems) {
      if (saleItem != null && saleItem.getItem().itemID() == item.itemID()) {
        return saleItem;
      }
    }
    return null;
  }

  /**
   * Returns the list of items included in the sale.
   *
   * @return The list of {@link SaleItem} in the sale.
   */
  public List<SaleItem> getSaleItems() {
    return this.saleItems;
  }

  /**
   * Returns the discounts applied to the sale.
   *
   * @return The total discounts applied to the sale.
   */
  public double getDiscounts() {
    return this.discount;
  }

  /**
   * Returns the payment information for the sale.
   *
   * @return The {@link PaymentDTO} for the sale.
   */
  public PaymentDTO getPayment() {
    return this.payment;
  }

  /**
   * Returns the total VAT for the sale.
   *
   * @return The total VAT amount.
   */
  public double getTotalVAT() {
    return this.totalVAT;
  }

  /**
   * Returns the date and time when the sale was completed.
   *
   * @return The {@link Date} and time of the sale.
   */
  public Date getDateTime() {
    return this.datetime;
  }
}
