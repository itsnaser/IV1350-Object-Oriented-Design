package se.kth.iv1350.exceptions;

/**
 * Thrown when the inventory database cannot be called (e.g., server down).
 */
public class InventoryDatabaseException extends RuntimeException {

  /**
   * Exception thrown when there is a failure to connect to or interact with the
   * inventory database.
   *
   * @param msg A message describing the specific error that occurred.
   */
  public InventoryDatabaseException(String msg) {
    super(msg);
  }

}