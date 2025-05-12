package se.kth.iv1350.exceptions;

/**
 * Thrown when an item with the specified identifier is not found in the
 * inventory.
 */
public class ItemNotFoundException extends Exception {

  /**
   * Exception thrown when an item cannot be found in the inventory system.
   *
   * @param msg A message describing the reason why the item was not found.
   */
  public ItemNotFoundException(String msg) {
    super(msg);
  }

}
