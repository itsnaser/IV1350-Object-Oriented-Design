/**
 * Demonstrates the usage of LoggingArrayList (inheritance-based)
 * and LoggingList (composition-based) classes.
 */
public class Main {
  /**
   * The main method to run the demonstration.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    // Test LoggingArrayList (uses inheritance)
    System.out.println("=== Testing Inheritance-Based LoggingArrayList ===");
    LoggingArrayListInheritance inheritedList = new LoggingArrayListInheritance();
    inheritedList.add("Apple");
    inheritedList.add("Banana");
    inheritedList.remove("Apple");
    System.out.println("Inherited List Contents: " + inheritedList);
    inheritedList.clear();

    // Test LoggingList (uses composition)
    System.out.println("\n=== Testing Composition-Based LoggingList ===");
    LoggingArrayListComposition composedList = new LoggingArrayListComposition();
    composedList.add("Carrot");
    composedList.add("Lemon");
    composedList.remove("Carrot");
    System.out.println("Composed List Contents: " + composedList);
    composedList.clear();
  }
}
