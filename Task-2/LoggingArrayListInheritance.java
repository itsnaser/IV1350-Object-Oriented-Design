import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * An ArrayList of strings that logs every modification operation (add, remove,
 * clear)
 * with a timestamp.
 * Demonstrates inheritance by extending {@link ArrayList}.
 */
public class LoggingArrayListInheritance extends ArrayList<String> {

  /**
   * Adds an element to the list and logs the operation with a timestamp.
   *
   * @param element the string to add
   * @return {@code true} if the element was added successfully
   */
  @Override
  public boolean add(String element) {
    String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println("[INHERITANCE] " + timestamp + " - Adding element: " + element);
    return super.add(element);
  }

  /**
   * Removes the first occurrence of the specified element from the list and logs
   * the operation.
   *
   * @param o the object to remove
   * @return {@code true} if the element was removed
   */
  @Override
  public boolean remove(Object o) {
    String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println("[INHERITANCE] " + timestamp + " - Removing element: " + o);
    return super.remove(o);
  }

  /**
   * Removes all elements from the list and logs the operation.
   */
  @Override
  public void clear() {
    String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println("[INHERITANCE] " + timestamp + " - Clearing list");
    super.clear();
  }
}
