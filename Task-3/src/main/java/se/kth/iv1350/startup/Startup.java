package se.kth.iv1350.startup;

import se.kth.iv1350.integration.*;
import se.kth.iv1350.logger.ErrorLogger;

import java.io.IOException;

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
   * @throws IOException If an I/O error occurs during initialization.
   */
  public static void main(String[] args) throws IOException {
    // Startup flow
    System.out.println("Starting the application..." + "\n");
    DiscountDBHandler discountDBHandler = DiscountDBHandler.getInstance(); // Singleton
    InventorySys inventorySys = InventorySys.getInstance(); // Singleton
    AccountingSys accountingSys = new AccountingSys();
    Printer printer = new Printer();
    Controller controller = new Controller(discountDBHandler, inventorySys, accountingSys, printer);
    ErrorLogger logger = new ErrorLogger();
    View view = new View(controller, logger);
  }
}
