package se.kth.iv1350.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.model.dto.*;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DiscountDBHandler} class.
 * <p>
 * This test class verifies the correct behavior of discount retrieval logic for
 * sales, customers, and total cost thresholds.
 * </p>
 * <ul>
 * <li>{@code testGetDiscountsForSaleWithMatchingItem}: Verifies that a discount
 * is correctly applied when a sale contains an item eligible for a
 * discount.</li>
 * <li>{@code testGetDiscountsForSaleWithNoMatchingItem}: Ensures no discount is
 * applied when the sale contains items not eligible for discounts.</li>
 * <li>{@code testGetDiscountsForCustomerWithDiscount}: Checks that a customer
 * with a registered discount receives the correct discount percentage.</li>
 * <li>{@code testGetDiscountsForCustomerWithNoDiscount}: Ensures that a
 * customer without a registered discount receives no discount.</li>
 * <li>{@code testGetDiscountsForTotalCostAboveThresholds}: Verifies that
 * multiple discounts are compounded correctly when the total cost exceeds
 * multiple thresholds.</li>
 * <li>{@code testGetDiscountsForTotalCostBelowThresholds}: Ensures that no
 * discount is applied when the total cost does not meet any discount
 * thresholds.</li>
 * </ul>
 */
class DiscountDBHandlerTest {
  private DiscountDBHandler discountDBHandler;

  @BeforeEach
  void setUp() {
    discountDBHandler = new DiscountDBHandler();
  }

  @Test
  void testGetDiscountsForSaleWithMatchingItem() {
    ItemDTO item = new ItemDTO(1, "TestItem", 100.0, 25);
    SaleItemDTO saleItem = new SaleItemDTO(item, 2);
    List<SaleItemDTO> saleItems = new ArrayList<>();
    saleItems.add(saleItem);
    SaleDTO sale = new SaleDTO(saleItems, null, 0.0, 0.0, null);

    double discount = discountDBHandler.getDiscounts(sale);

    // 10% discount on 2*100*1.25 = 250 => 25.0
    assertEquals(25.0, discount, 0.01);
  }

  @Test
  void testGetDiscountsForSaleWithNoMatchingItem() {
    ItemDTO item = new ItemDTO(99, "NoDiscountItem", 50.0, 12);
    SaleItemDTO saleItem = new SaleItemDTO(item, 1);
    List<SaleItemDTO> saleItems = new ArrayList<>();
    saleItems.add(saleItem);
    SaleDTO sale = new SaleDTO(saleItems, null, 0.0, 0.0, null);

    double discount = discountDBHandler.getDiscounts(sale);

    assertEquals(0.0, discount, 0.01);
  }

  @Test
  void testGetDiscountsForCustomerWithDiscount() {
    // CustomerID 1 has a 10% discount
    double discount = discountDBHandler.getDiscounts(1);

    assertEquals(10.0, discount, 0.01);
  }

  @Test
  void testGetDiscountsForCustomerWithNoDiscount() {
    double discount = discountDBHandler.getDiscounts(99);

    assertEquals(0.0, discount, 0.01);
  }

  @Test
  void testGetDiscountsForTotalCostAboveThresholds() {
    // Both 100 and 50 thresholds are met, so both discounts apply:
    // (1-0.10)*(1-0.10) = 0.81, so discount = 1-0.81 = 0.19 -> 19%
    double discount = discountDBHandler.getDiscounts(150.0);

    assertEquals(19.0, discount, 0.01);
  }

  @Test
  void testGetDiscountsForTotalCostBelowThresholds() {
    double discount = discountDBHandler.getDiscounts(10.0);

    assertEquals(0.0, discount, 0.01);
  }
}