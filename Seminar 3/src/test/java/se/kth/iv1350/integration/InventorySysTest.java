package se.kth.iv1350.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.model.dto.ItemDTO;
import se.kth.iv1350.model.dto.SaleDTO;
import se.kth.iv1350.model.dto.SaleItemDTO;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link InventorySys} class.
 * <p>
 * This test class verifies the following functionalities of the inventory
 * system:
 * <ul>
 * <li>Retrieving an item that exists in the inventory.</li>
 * <li>Handling the case when an item does not exist in the inventory.</li>
 * <li>Retrieving all items from the inventory.</li>
 * <li>Updating the inventory after a sale, ensuring item quantities decrease
 * accordingly.</li>
 * <li>Updating the inventory for multiple items sold in a single sale.</li>
 * </ul>
 * <p>
 * Each test is isolated and uses a fresh instance of {@code InventorySys}.
 */
class InventorySysTest {
  private InventorySys inventorySys;

  @BeforeEach
  void setUp() {
    inventorySys = new InventorySys();
  }

  @Test
  void testGetItem_ItemExists() {
    ItemDTO item = inventorySys.getItem(1);
    assertNotNull(item);
    assertEquals(1, item.itemID());
    assertEquals("Apple", item.description());
  }

  @Test
  void testGetItem_ItemDoesNotExist() {
    ItemDTO item = inventorySys.getItem(999);
    assertNull(item);
  }

  @Test
  void testGetItems_ReturnsAllInventoryItems() {
    List<InventorySys.InventoryItem> items = inventorySys.getItems();
    assertEquals(5, items.size());
    assertEquals("Apple", items.get(0).getItem().description());
  }

  @Test
  void testUpdateInventory_QuantityDecreasesAfterSale() {
    // Arrange
    ItemDTO item = inventorySys.getItem(1);
    int initialQuantity = inventorySys.getItems().get(0).getQuantity();
    SaleItemDTO saleItem = new SaleItemDTO(item, 5);
    SaleDTO saleDTO = new SaleDTO(List.of(saleItem), null, 0, 0, null);

    // Act
    inventorySys.updateInventory(saleDTO);

    // Assert
    int updatedQuantity = inventorySys.getItems().get(0).getQuantity();
    assertEquals(initialQuantity - 5, updatedQuantity);
  }

  @Test
  void testUpdateInventory_MultipleItems() {
    ItemDTO apple = inventorySys.getItem(1);
    ItemDTO banana = inventorySys.getItem(2);
    int appleQty = inventorySys.getItems().get(0).getQuantity();
    int bananaQty = inventorySys.getItems().get(1).getQuantity();

    SaleItemDTO saleApple = new SaleItemDTO(apple, 2);
    SaleItemDTO saleBanana = new SaleItemDTO(banana, 3);
    SaleDTO saleDTO = new SaleDTO(List.of(saleApple, saleBanana), null, 0, 0, null);

    inventorySys.updateInventory(saleDTO);

    assertEquals(appleQty - 2, inventorySys.getItems().get(0).getQuantity());
    assertEquals(bananaQty - 3, inventorySys.getItems().get(1).getQuantity());
  }
}