package se.kth.iv1350.model.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.model.dto.DiscountDTO;
import se.kth.iv1350.model.dto.ItemDTO;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Sale} class.
 * <p>
 * These tests verify the correctness of total price calculation in various
 * scenarios,
 * including:
 * <ul>
 * <li>No discounts applied</li>
 * <li>Fixed amount discounts</li>
 * <li>Percentage-based discounts</li>
 * <li>Combination of fixed and percentage discounts</li>
 * <li>Discounts exceeding the total price</li>
 * <li>Negative totals after discounts (should be floored at zero)</li>
 * <li>No items in the sale</li>
 * </ul>
 * <p>
 * The tests use {@link ItemDTO} and {@link DiscountDTO} to simulate sale items
 * and discounts.
 * Each test asserts that the calculated total price matches the expected value
 * after
 * applying the relevant discounts and taxes.
 */
class SaleTest {
  private Sale sale;
  private ItemDTO item1;
  private ItemDTO item2;

  @BeforeEach
  void setUp() {
    sale = new Sale();
    item1 = new ItemDTO(1, "Milk", 10.0, 12);
    item2 = new ItemDTO(2, "Bread", 20.0, 6);
  }

  @Test
  void testGetTotalPrice_NoDiscounts() {
    sale.addItem(item1, 2); // 2 * 10 * 1.12 = 22.4
    sale.addItem(item2, 1); // 1 * 20 * 1.06 = 21.2
    double expected = Math.round((22.4 + 21.2) * 100.0) / 100.0;
    assertEquals(expected, sale.getTotalPrice());
  }

  @Test
  void testGetTotalPrice_WithFixedDiscount() {
    sale.addItem(item1, 1); // 10 * 1.12 = 11.2
    sale.addItem(item2, 1); // 20 * 1.06 = 21.2
    DiscountDTO discount = new DiscountDTO(1, 0, 0, 0.0, 5.0, 0, true); // fixed discount
    sale.addFixedDiscount(discount.fixedDiscount());
    double expected = Math.round((11.2 + 21.2 - 5.0) * 100.0) / 100.0;
    assertEquals(expected, sale.getTotalPrice());
  }

  @Test
  void testGetTotalPrice_WithPercentageDiscount() {
    sale.addItem(item1, 1); // 11.2
    sale.addItem(item2, 1); // 21.2
    DiscountDTO discount = new DiscountDTO(2, 0, 0, 0.0, 0.0, 10, true); // 10% percentage discount
    sale.addPercentageDiscount(discount.percentageDiscount());
    double expected = Math.round(((11.2 + 21.2) * (1 - 0.10)) * 100.0) / 100.0;
    assertEquals(expected, sale.getTotalPrice());
  }

  @Test
  void testGetTotalPrice_WithFixedAndPercentageDiscount() {
    sale.addItem(item1, 2); // 22.4
    sale.addItem(item2, 1); // 21.2
    DiscountDTO fixedDiscount = new DiscountDTO(3, 0, 0, 0.0, 5.0, 0, true); // fixed
    DiscountDTO percentageDiscount = new DiscountDTO(4, 0, 0, 0.0, 0.0, 20, true); // 20% percentage
    sale.addFixedDiscount(fixedDiscount.fixedDiscount());
    sale.addPercentageDiscount(percentageDiscount.percentageDiscount());
    double subtotal = 22.4 + 21.2 - 5.0;
    double expected = Math.round(subtotal * (1 - 0.20) * 100.0) / 100.0;
    assertEquals(expected, sale.getTotalPrice());
  }

  @Test
  void testGetTotalPrice_FixedDiscountGreaterThanTotal() {
    sale.addItem(item1, 1); // 11.2
    DiscountDTO discount = new DiscountDTO(5, 0, 0, 0.0, 20.0, 0, true); // fixed discount greater than total
    sale.addFixedDiscount(discount.fixedDiscount());
    assertEquals(0.0, sale.getTotalPrice());
  }

  @Test
  void testGetTotalPrice_NegativeTotalAfterDiscounts() {
    sale.addItem(item1, 1); // 11.2
    DiscountDTO fixedDiscount = new DiscountDTO(6, 0, 0, 0.0, 10.0, 0, true); // fixed
    DiscountDTO percentageDiscount = new DiscountDTO(7, 0, 0, 0.0, 0.0, 90, true); // 90% percentage
    sale.addFixedDiscount(fixedDiscount.fixedDiscount());
    sale.addPercentageDiscount(percentageDiscount.percentageDiscount());
    double subtotal = 11.2 - 10.0;
    double expected = Math.round(Math.max(0, subtotal * (1 - 0.90)) * 100.0) / 100.0;
    assertEquals(expected, sale.getTotalPrice());
  }

  @Test
  void testGetTotalPrice_NoItems() {
    assertEquals(0.0, sale.getTotalPrice());
  }
}