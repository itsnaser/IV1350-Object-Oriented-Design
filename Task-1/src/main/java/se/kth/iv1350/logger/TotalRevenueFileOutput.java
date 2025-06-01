package se.kth.iv1350.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import se.kth.iv1350.model.classes.TotalRevenueTemplate;

/**
 * Logs and tracks the total revenue from all completed sales to a file.
 */
public class TotalRevenueFileOutput extends TotalRevenueTemplate {
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

  public void doShowTotalIncome() {
    writeTotalRevenueToFile();
  }

  public void handleErrors(Exception e) {
    writer.println(e.getMessage());
    writer.println(e.getCause());
  }

  /**
   * Writes the current total revenue and timestamp to the log file.
   *
   * @return void
   */
  public void writeTotalRevenueToFile() {
    LocalDateTime time = LocalDateTime.now();
    String formattedTime = time.format(DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]"));
    writer.println(formattedTime + "\nTotal Revenue: " + this.totalRevenue + " SEK");
  }

}
