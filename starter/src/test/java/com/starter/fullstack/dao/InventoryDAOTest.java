package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Test Inventory DAO.
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @ClassRule
  public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String PRODUCT_TYPE = "hops";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test Find All method.
   */
  @Test
  public void findAll() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }

    /**
     *  Test Create method
     */
  @Test
  public void create() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId(null);
    Inventory inventoryTest = this.inventoryDAO.create(inventory);
    Assert.assertEquals(inventory, inventoryTest);
  }

  /**
   *  Test delete method
   */
  @Test
  public void deleteAndRemove() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId(null);
    Inventory inventoryTest = this.inventoryDAO.create(inventory);
    Assert.assertEquals(inventory, inventoryTest);

    Optional<Inventory> deletedInventory = this.inventoryDAO.delete(inventoryTest.getId());
    Assert.assertEquals(inventoryTest, deletedInventory.get());
  }

  /**
   *  Test delete method
   */
  @Test
  public void deleteEmpty() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("");

    Optional<Inventory> deletedInventory = this.inventoryDAO.delete(inventory.getId());
    Assert.assertEquals(Optional.empty(), deletedInventory);
  }

  /**
   *  Test retreive method
   */
  @Test
  public void getTest() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId(null);
    Inventory inventoryTest = this.inventoryDAO.create(inventory);
    Assert.assertEquals(inventory, inventoryTest);

    Optional<Inventory> getInventory = this.inventoryDAO.retrieve(inventory.getId());
    Assert.assertEquals(Optional.of(inventoryTest), getInventory);
  }

  /**
   *  Test retreive method
   */
  @Test
  public void getAndFailTest() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId(null);
    Inventory inventoryTest = this.inventoryDAO.create(inventory);
    Assert.assertEquals(inventory, inventoryTest);

    Optional<Inventory> getInventory = this.inventoryDAO.retrieve(inventory.getId() + "1");
    Assert.assertEquals(Optional.empty(), getInventory);
  }
}
