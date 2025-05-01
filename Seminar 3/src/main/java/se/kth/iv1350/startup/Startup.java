package se.kth.iv1350.startup;

import se.kth.iv1350.integration.*;
import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.view.View;

/**
 * The Startup class contains the main method that starts the application.
 * It initializes all system handlers and the controller, and creates the view.
 */
public class Startup {
  /**
   * The application's entry point. Initializes system handlers, the controller,
   * and the view.
   *
   * @param args The command-line arguments (not used).
   */
  public static void main(String[] args) {
    // Startup flow
    System.out.println("Starting the application..." + "\n");
    DiscountDBHandler discountDBHandler = new DiscountDBHandler();
    InventorySys inventorySys = new InventorySys();
    AccountingSys accountingSys = new AccountingSys();
    Printer printer = new Printer();
    Controller controller = new Controller(discountDBHandler, inventorySys, accountingSys, printer);
    View view = new View(controller);
  }
}
