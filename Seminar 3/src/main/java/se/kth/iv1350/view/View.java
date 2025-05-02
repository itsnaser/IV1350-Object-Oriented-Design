package se.kth.iv1350.view;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.model.dto.ItemDTO;

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
   * Prints information about a scanned item, including its details, the current
   * total cost
   * (including VAT), and the total VAT for the sale so far.
   *
   * @param item The {@link ItemDTO} representing the item that was just scanned.
   */
  private void printScannedItem(ItemDTO item) {
    System.out.println(item.toString() + "\n");
    // System.out.println("Total cost (incl VAT): " + String.format("%.2f",
    // sale.getTotalPrice()) + " SEK");
    // System.out.println("Total VAT: " + String.format("%.2f", sale.getTotalVAT())
    // + " SEK" + "\n");
  }

  /**
   * Simulates basic flow: starts a sale, registers some items, and ends the sale.
   */
  public void basicFlow() {
    // startSale flow
    System.out.println("[basicFlow]" + "\n");
    controller.startNewSale();
    System.out.println("New sale started." + "\n");

    // scan flow
    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 1");
    printScannedItem(controller.scanItem(1, 1));

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 2");
    printScannedItem(controller.scanItem(2, 1));
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
   * Simulates alternative flow 3-4a: No item with the specified identifier is
   * found.
   * 
   */
  public void altFlow_3_4_a() {
    // startSale flow
    System.out.println("[altFlow_3_4_a]" + "\n");
    controller.startNewSale();
    System.out.println("New sale started." + "\n");

    // scan flow
    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 99");
    try {
      printScannedItem(controller.scanItem(99, 1));
    } catch (IllegalArgumentException e) {
      System.err.println("Error: " + e.getMessage() + "\n");
    }

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 2");
    printScannedItem(controller.scanItem(2, 1));
    System.out.println("------------------------------");

    System.out.println("All Items scanned.\n");

    // endSale flow
    double totalPrice = controller.endSale();
    System.out.println("Sale ended. Total price: " + String.format("%.2f", totalPrice) + " SEK\n");

    // payment flow
    double amountChange = controller.setAmountPaid(100.0);
    System.out.println("\n");
  }

  /**
   * Simulates alternative flow 3-4b: An item with the specified identifier has
   * already been entered in the current sale.
   * 
   */
  public void altFlow_3_4_b() {
    // startSale flow
    System.out.println("[altFlow_3_4_b]" + "\n");
    controller.startNewSale();
    System.out.println("New sale started." + "\n");

    // scan flow
    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 1");
    printScannedItem(controller.scanItem(1, 1));

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 2");
    printScannedItem(controller.scanItem(2, 1));

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 1");
    printScannedItem(controller.scanItem(1, 1));
    System.out.println("------------------------------");

    System.out.println("All Items scanned.\n");

    // endSale flow
    double totalPrice = controller.endSale();
    System.out.println("Sale ended. Total price: " + String.format("%.2f", totalPrice) + " SEK\n");

    // payment flow
    double amountChange = controller.setAmountPaid(100.0);
    System.out.println("\n");
  }

  /**
   * Simulates alternative flow 3-4c: Customer purchases multiple items of the
   * same goods (with the same identifier),
   * and cashier registers them together.
   */
  public void altFlow_3_4_c() {
    // startSale flow
    System.out.println("[altFlow_3_4_c]" + "\n");
    controller.startNewSale();
    System.out.println("New sale started." + "\n");

    // scan flow
    System.out.println("------------------------------");
    System.out.println("Add 4 items with item id 1");
    printScannedItem(controller.scanItem(1, 4));

    System.out.println("------------------------------");
    System.out.println("Add 5 items with item id 2");
    printScannedItem(controller.scanItem(2, 5));
    System.out.println("------------------------------");

    System.out.println("All Items scanned.\n");

    // endSale flow
    double totalPrice = controller.endSale();
    System.out.println("Sale ended. Total price: " + String.format("%.2f", totalPrice) + " SEK\n");

    // payment flow
    double amountChange = controller.setAmountPaid(200.0);
    System.out.println("\n");
  }

  /**
   * Simulates alternative flow 9a: Customer says they are eligible for a discount
   */
  public void altFlow_9_a() {
    // startSale flow
    System.out.println("[altFlow_9_a]" + "\n");
    controller.startNewSale();
    System.out.println("New sale started." + "\n");

    // scan flow
    System.out.println("------------------------------");
    System.out.println("Add 4 items with item id 1");
    printScannedItem(controller.scanItem(1, 4));

    System.out.println("------------------------------");
    System.out.println("Add 5 items with item id 2");
    printScannedItem(controller.scanItem(2, 5));
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
    double amountChange = controller.setAmountPaid(120.0);
    System.out.println("\n");
  }

  /**
   * Runs the view logic. This method simulates the user interface.
   */
  public void run() {
    altFlow_9_a();

  }
}
