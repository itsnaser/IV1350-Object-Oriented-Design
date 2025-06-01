package se.kth.iv1350.model.interfaces;

/**
 * Observer interface for receiving updates about completed sales.
 */
public interface ISaleObserver {
    /**
     * Called when a sale is completed to update the observer with the total amount
     * of the sale.
     *
     * @param totalAmountOfSale The total amount paid for the completed sale.
     */
    void updateRevenue(double totalAmountOfSale);
}
