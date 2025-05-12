package se.kth.iv1350.view;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.model.dto.ItemDTO;
import se.kth.iv1350.exceptions.*;
import se.kth.iv1350.logger.ErrorLogger;
import se.kth.iv1350.logger.TotalRevenueFileOutput;

import java.io.IOException;

/**
 * The View class represents the user interface of the application.
 * It interacts with the {@link Controller} to perform operations and display
 * information.
 */
public class View {
  private Controller controller;
  private ErrorLogger logger;

  /**
   * Creates a new instance of the {@code View} class, initializing the controller
   * and
   * registering observers for sale events. Also attempts to initialize the error
   * logger.
   * If the logger cannot be initialized, an error message is printed to standard
   * error.
   * Finally, starts the main execution loop by calling {@code run()}.
   *
   * @param controller The {@link Controller} to be used by this view.
   */
  public View(Controller controller) {
    this.controller = controller;
    this.controller.addSaleObserver(new TotalRevenueView());
    this.controller.addSaleObserver(new TotalRevenueFileOutput());
    try {
      this.logger = new ErrorLogger();
    } catch (IOException e) {
      System.err.println("Could not initialize error logger: " + e.getMessage());
    }
    run();
  }

  /**
   * Constructs a formatted string containing information about an item.
   * The information includes the item's ID, description, price (including VAT),
   * and VAT percentage.
   *
   * @param item The {@link ItemDTO} object containing the item's details.
   * @return A formatted string with the item's ID, description, price including
   *         VAT, and VAT percentage.
   */
  private String constructItemInfo(ItemDTO item) {
    return "Item ID: " + item.itemID() + "\n" +
        "Item description: " + item.description() + '\n' +
        "Item price: " + item.price() * (1 + (item.VAT() / 100)) + " SEK\n" +
        "VAT: " + item.VAT() + "%";
  }

  /**
   * Prints information about a scanned item, including its details, the current
   * total cost
   * (including VAT), and the total VAT for the sale so far.
   *
   * @param itemID   The ID of the item to be scanned.
   * @param quantity The quantity of the item to be scanned.
   */
  private void printScannedItem(int itemID, int quantity) {
    try {
      ItemDTO item = controller.scanItem(itemID, quantity);
      System.out.println(constructItemInfo(item) + "\n");

    } catch (ItemNotFoundException e) {
      // Unchecked exception
      System.out.println("No item found with ID: " + itemID + ". Please try another Item.");

    } catch (InventoryDatabaseException e) {
      // Checked exception
      System.out.println("Could not connect to the inventory database. Please try again later.");
      if (logger != null)
        logger.logException(e);

    } catch (Exception e) {
      System.out.println("An unexpected error occurred: " + e.getMessage());
      if (logger != null)
        logger.logException(e);
    }
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
    printScannedItem(1, 1);

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 2");
    printScannedItem(2, 1);
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
    printScannedItem(99, 1);

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 69");
    printScannedItem(69, 1);

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 2");
    printScannedItem(2, 1);
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
    printScannedItem(1, 1);

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 2");
    printScannedItem(2, 1);

    System.out.println("------------------------------");
    System.out.println("Add 1 items with item id 1");
    printScannedItem(1, 1);
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
    printScannedItem(1, 4);

    System.out.println("------------------------------");
    System.out.println("Add 5 items with item id 2");
    printScannedItem(2, 5);
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
    printScannedItem(1, 4);

    System.out.println("------------------------------");
    System.out.println("Add 5 items with item id 2");
    printScannedItem(2, 5);
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
    basicFlow();
    altFlow_3_4_a();
    altFlow_3_4_b();
    altFlow_3_4_c();
    altFlow_9_a();
  }
}
