package se.kth.iv1350.view;

import se.kth.iv1350.controller.Controller;

/**
 * The View class represents the user interface of the application.
 * It interacts with the {@link Controller} to perform operations and display
 * information.
 */
public class View {
  private Controller controller;

  /**
   * Creates a new {@code View} with the specified controller and starts the view.
   *
   * @param controller The {@link Controller} to be used by this view.
   */
  public View(Controller controller) {
    this.controller = controller;
    run();
  }

  /**
   * Simulates scenario 1: starts a sale, registers some items, and ends the sale.
   */
  public void scenario1() {
    // startSale flow
    System.out.println("Version 1.0.0" + "\n");
    controller.startNewSale();
    System.out.println("New sale started." + "\n");

    // scan flow
    System.out.println("------------------------------");
    System.out.println("Add 3 items with item id 1");
    controller.scanItem(1, 1);

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 2");
    controller.scanItem(2, 1);

    System.out.println("------------------------------");
    System.out.println("Add 5 items with item id 1");
    controller.scanItem(1, 1);
    System.out.println("------------------------------");

    System.out.println("All Items scanned.");

    // requestDiscount flow
    System.out.println("");

    // endSale flow
    double totalPrice = controller.endSale();
    System.out.println("Sale ended. Total price: " + String.format("%.2f", totalPrice));

    // payment flow
    double amountChange = controller.setAmountPaid(100.0);
    System.out.println("\n");
  }

  /**
   * Simulates scenario 2: starts a sale, registers some items, request some
   * discounts, and ends the sale.
   */
  public void scenario2() {
    // startSale flow
    System.out.println("Version 1.0.0" + "\n");
    controller.startNewSale();
    System.out.println("New sale started." + "\n");

    // scan flow
    System.out.println("------------------------------");
    System.out.println("Add 3 items with item id 1");
    controller.scanItem(1, 1);

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 2");
    controller.scanItem(2, 1);

    System.out.println("------------------------------");
    System.out.println("Add 5 items with item id 1");
    controller.scanItem(1, 1);
    System.out.println("------------------------------");

    System.out.println("All Items scanned.\n");

    // endSale flow
    double totalPrice = controller.endSale();
    System.out.println("Sale ended. Total price: " + String.format("%.2f", totalPrice) + " SEK\n");

    // requestDiscount flow
    controller.signalDiscountRequest(1);
    System.out.println("Discounts requested and applied.");
    System.out.println("New Total price: " + String.format("%.2f", controller.endSale()) + " SEK\n");

    // payment flow
    double amountChange = controller.setAmountPaid(100.0);
    System.out.println("\n");
  }

  /**
   * Runs the view logic. This method simulates the user interface.
   */
  public void run() {
    scenario2();

  }
}
