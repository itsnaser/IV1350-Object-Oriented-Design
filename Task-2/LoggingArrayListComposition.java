import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * A list of strings that logs every modification operation (add, remove, clear)
 * with a timestamp.
 * Demonstrates composition by using an internal {@link ArrayList} to store
 * elements.
 */
public class LoggingArrayListComposition {
  private ArrayList<String> list;

  /**
   * Creates an empty LoggingList.
   */
  public LoggingArrayListComposition() {
    this.list = new ArrayList<>();
  }

  /**
   * Adds an element to the list and logs the operation with a timestamp.
   *
   * @param element the string to add
   * @return {@code true} if the element was added successfully
   */
  public boolean add(String element) {
    String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println("[COMPOSITION] " + timestamp + " - Adding element: " + element);
    return list.add(element);
  }

  /**
   * Removes the first occurrence of the specified element from the list and logs
   * the operation.
   *
   * @param element the string to remove
   * @return {@code true} if the element was removed
   */
  public boolean remove(String element) {
    String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println("[COMPOSITION] " + timestamp + " - Removing element: " + element);
    return list.remove(element);
  }

  /**
   * Removes all elements from the list and logs the operation.
   */
  public void clear() {
    String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println("[COMPOSITION] " + timestamp + " - Clearing list");
    list.clear();
  }

  /**
   * Returns the element at the specified position in the list.
   *
   * @param index index of the element to return
   * @return the element at the specified position
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  public String get(int index) {
    return list.get(index);
  }

  /**
   * Returns the number of elements in the list.
   *
   * @return the size of the list
   */
  public int size() {
    return list.size();
  }

  /**
   * Returns {@code true} if the list contains the specified element.
   *
   * @param element the string to check for
   * @return {@code true} if the list contains the element
   */
  public boolean contains(String element) {
    return list.contains(element);
  }

  /**
   * Returns a string representation of the list.
   *
   * @return a string representation of the list
   */
  @Override
  public String toString() {
    return list.toString();
  }
}
