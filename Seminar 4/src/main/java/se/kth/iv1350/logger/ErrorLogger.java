package se.kth.iv1350.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles logging of exceptions to a file for error tracking and debugging.
 */
public class ErrorLogger {
  private static final String LOG_FILE = "error-log.txt";
  private final PrintWriter writer;

  /**
   * Creates a new ErrorLogger that writes to a log file.
   * 
   * @throws IOException if the log file cannot be created or opened for writing.
   */
  public ErrorLogger() throws IOException {
    writer = new PrintWriter(new FileWriter(LOG_FILE, true), true);
  }

  /**
   * Logs the provided exception to the log file, including the timestamp,
   * exception type, message, and stack trace.
   *
   * @param e The exception to log.
   */
  public void logException(Exception e) {
    LocalDateTime time = LocalDateTime.now();
    String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    writer.println(formattedTime + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
    for (StackTraceElement ste : e.getStackTrace()) {
      writer.println("\tat " + ste);
    }
    writer.println();
  }
}
