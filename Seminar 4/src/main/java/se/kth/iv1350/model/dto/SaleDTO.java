package se.kth.iv1350.model.dto;

import se.kth.iv1350.model.classes.Sale;

import java.util.List;
import java.util.Date;

/**
 * Represents a data transfer object (DTO) for a completed sale,
 * containing information about the items sold, date and time, applied
 * discounts, total VAT, and payment details.
 *
 * @param saleItems The list of {@link SaleItemDTO} representing the items
 *                  included in the sale.
 * @param datetime  The {@link Date} and time when the sale was completed.
 * @param discounts The list of {@link DiscountDTO} representing discounts
 *                  applied to the sale.
 * @param totalVAT  The total value-added tax (VAT) for the sale.
 * @param payment   The {@link PaymentDTO} containing payment information for
 *                  the sale.
 */
public record SaleDTO(
                List<SaleItemDTO> saleItems,
                Date datetime,
                double discount,
                double totalVAT,
                PaymentDTO payment) {
        public SaleDTO(Sale sale) {
                this(sale.getSaleItems().stream().map(SaleItemDTO::new).toList(),
                                sale.getDateTime(),
                                sale.getDiscounts(),
                                sale.getTotalVAT(),
                                sale.getPayment());
        }
}
