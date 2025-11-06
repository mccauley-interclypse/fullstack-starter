package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Inventory Controller.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   * @param inventoryDAO inventoryDAO.
   */
  public InventoryController(InventoryDAO inventoryDAO) {
    Assert.notNull(inventoryDAO, "Inventory DAO must not be null.");
    this.inventoryDAO = inventoryDAO;
  }

  /**
   * Find Products.
   * @return List of Product.
   */
  @GetMapping
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }

  /**
   * Save inventory.
   * @param inventory inventory.
   * @return inventory.
   */
  @PostMapping
  public Inventory create(@Valid @RequestBody Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }

  /**
   * Save inventory.
   * @param inventory inventory.
   * @param id id
   * @return inventory.
   */
  @PutMapping("/{id}")
  public Inventory update(@PathVariable String id, @RequestBody Inventory inventory) {
    Optional<Inventory> optionalInventory = this.inventoryDAO.update(id, inventory);
    if (optionalInventory.isEmpty()) {
      return null;
    }
    return optionalInventory.get();
  }

  @DeleteMapping
  public Inventory delete(@RequestBody String id) {

    Optional<Inventory> inventory = this.inventoryDAO.delete(id);
    if (inventory.isEmpty()) {
      return null;
    }
    return inventory.get();
  }

}

