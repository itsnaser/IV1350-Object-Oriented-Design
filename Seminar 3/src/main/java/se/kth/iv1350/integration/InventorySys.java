package se.kth.iv1350.integration;

import se.kth.iv1350.model.dto.ItemDTO;
import se.kth.iv1350.model.dto.SaleDTO;
import se.kth.iv1350.model.dto.SaleItemDTO;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents the external inventory system integration.
 * Responsible for managing inventory items, fetching item information, and
 * updating inventory after a sale.
 */
public class InventorySys {
  private List<InventoryItem> items;

  /**
   * Represents an item in the inventory with its quantity.
   */
  public class InventoryItem {
    private ItemDTO item;
    private int quantity;

    /**
     * Creates a new {@code InventoryItem} with the specified item and quantity.
     *
     * @param item     The {@link ItemDTO} representing the item.
     * @param quantity The quantity of the item in stock.
     */
    public InventoryItem(ItemDTO item, int quantity) {
      this.item = item;
      this.quantity = quantity;
    }

    /**
     * Returns the item information.
     *
     * @return The {@link ItemDTO} for this inventory item.
     */
    public ItemDTO getItem() {
      return item;
    }

    /**
     * Returns the quantity of this item in stock.
     *
     * @return The quantity of the item.
     */
    public int getQuantity() {
      return quantity;
    }

    /**
     * Sets the quantity of this item in stock.
     *
     * @param quantity The new quantity to set.
     */
    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }
  }

  /**
   * Creates a new {@code InventorySys} and initializes the inventory with some
   * items.
   */
  public InventorySys() {
    this.items = new ArrayList<>();
    items.add(new InventoryItem(new ItemDTO(1, "Apple", 10.00, 25), 34));
    items.add(new InventoryItem(new ItemDTO(2, "Banana", 20.00, 25), 57));
    items.add(new InventoryItem(new ItemDTO(3, "Orange", 8.0, 25), 21));
    items.add(new InventoryItem(new ItemDTO(4, "Milk", 20.0, 6), 88));
    items.add(new InventoryItem(new ItemDTO(5, "Bread", 15.0, 12), 49));
  }

  /**
   * Returns the list of inventory items.
   *
   * @return The list of {@link InventoryItem} in the inventory.
   */
  public List<InventoryItem> getItems() {
    return items;
  }

  /**
   * Prints the current inventory to the console.
   */
  public void printInventory() {
    System.out.println("Current inventory:");
    for (InventoryItem inventoryItem : items) {
      System.out.println(inventoryItem.getItem().itemID() + ": " +
          inventoryItem.getItem().description() + " - " +
          inventoryItem.getQuantity() + " units available.");
    }
  }

  /**
   * Fetches item information from the inventory system based on the item ID.
   *
   * @param itemId The unique identifier of the item to fetch.
   * @return The {@link ItemDTO} containing information about the item, or
   *         {@code null} if not found.
   */
  public ItemDTO getItem(int itemId) {
    System.out.println("Fetching item information from inventory system...");
    for (InventoryItem inventoryItem : items) {
      if (inventoryItem.getItem().itemID() == itemId) {
        return inventoryItem.getItem();
      }
    }
    return null;
  }

  /**
   * Updates the inventory system with information about a completed sale.
   * Reduces the quantity of each sold item in the inventory.
   *
   * @param saleDTO The {@link SaleDTO} containing information about the completed
   *                sale.
   */
  public void updateInventory(SaleDTO saleDTO) {
    System.out.println("Updating inventory with sale information...");
    for (SaleItemDTO soldItem : saleDTO.saleItems()) {
      for (InventoryItem inventoryItem : items) {
        if (inventoryItem.getItem().itemID() == soldItem.item().itemID()) {
          int newQuantity = inventoryItem.getQuantity() - soldItem.quantity();
          inventoryItem.setQuantity(newQuantity);
          break;
        }
      }
    }
  }
}
