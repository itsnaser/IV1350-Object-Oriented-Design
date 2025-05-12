package se.kth.iv1350.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import se.kth.iv1350.model.classes.ISaleObserver;

/**
 * Logs and tracks the total revenue from all completed sales to a file.
 */
public class TotalRevenueFileOutput implements ISaleObserver {
  private double totalRevenue;
  private static final String LOG_FILE = "TotalRevenueLog.txt";
  private PrintWriter writer;

  /**
   * Creates a new instance of TotalRevenueFileOutput and opens the log file for
   * writing.
   * If the file cannot be opened, an error message is printed to the console.
   */
  public TotalRevenueFileOutput() {
    this.totalRevenue = 0;
    try {
      writer = new PrintWriter(new FileWriter(LOG_FILE, true), true);
    } catch (IOException e) {
      System.out.println("Could not open log file: " + LOG_FILE);
      e.printStackTrace();
    }
  }

  /**
   * Updates the total revenue with the amount of the completed sale and writes
   * the updated total to the log file.
   *
   * @param totalAmountOfSale The total amount paid for the completed sale.
   */
  @Override
  public void updateRevenue(double totalAmountOfSale) {
    this.totalRevenue += totalAmountOfSale;
    writeTotalRevenueToFile();
  }

  /**
   * Writes the current total revenue and timestamp to the log file.
   *
   * @return void
   */
  public void writeTotalRevenueToFile() {
    LocalDateTime time = LocalDateTime.now();
    String formattedTime = time.format(DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]"));
    writer.println(formattedTime + "\nTotal Revenue: " + totalRevenue + " SEK");
  }

}
