import java.util.Date;

/**
 * This class represents a Production Record which corresponds to Products that have been Produced.
 *
 * <p>A Production Record includes various information including Prod. Number, ID, Serial Number,
 * Production Date, Number of items produced, and the Product being produced</p>
 *
 * @author Paul Sullivan
 */
public class ProductionRecord {

  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;
  private int itemCount;
  private Product product;

  /**
   * Constructor for a Production Record.
   *
   * @param productID the Product ID of a Product in the Product Database
   */
  public ProductionRecord(int productID) {
    this.productID = productID;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  /**
   * Overloaded Constructor for Production Record.
   *
   * @param product   refers to a Product object
   * @param itemCount holds the count of the number of items of its type "product" that have been
   *                  created
   */
  public ProductionRecord(Product product, int itemCount) {
    this.product = product;
    this.itemCount = itemCount;
    this.productID = product.getID();
    String serialGeneration = "";

    //set first three characters of serial number to first 3 of manufacturer:
    if (product.getManufacturer().length() > 3) {
      serialGeneration = product.getManufacturer().substring(0, 3);
    } else {
      serialGeneration = product.getManufacturer();
    }
    serialGeneration = serialGeneration + product.getItemType().getCode();

    //appending formatted itemCount to end of serial number:
    String countString = "";
    if (itemCount < 10) {
      countString = "0000" + Integer.toString(itemCount);
    } else if (itemCount >= 10 && itemCount < 100) {
      countString = "000" + Integer.toString(itemCount);
    } else if (itemCount >= 100 && itemCount < 1000) {
      countString = "00" + Integer.toString(itemCount);
    } else if (itemCount >= 1000 && itemCount < 10000) {
      countString = "0" + Integer.toString(itemCount);
    } else if (itemCount >= 10000 && itemCount < 100000) {
      countString = Integer.toString(itemCount);
    } else {
      //this cumbersome bit just returns the last 5 characters of the String if it is a
      //huge number for whatever reason
      countString = Integer.toString(itemCount).substring(
          Integer.toString(itemCount).length() - 5);
    }
    serialGeneration = serialGeneration + countString;
    this.serialNumber = serialGeneration;
    this.dateProduced = new Date();
  }

  /**
   * Overloaded Constructor for a Production Record.
   *
   * @param productionNumber refers to the order in which this run is being made in referenced to
   *                         others
   * @param productID        the Product ID of a Product in the Product Database
   * @param serialNumber     refers to the serial number of a production run
   * @param dateProduced     the date in which this record is made
   */
  public ProductionRecord(int productionNumber, int productID, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * Accessor for Production Run's Product.
   *
   * @return this Production Run's Product.
   */
  public Product getProduct() {
    return this.product;
  }

  /**
   * Sets the Product for a Production Run.
   *
   * @param product Specific Product for a Production Run.
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * Changes the Production Run's Production Number.
   *
   * @param productionNumber desired Production Number.
   */
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Accessor for Production Run's Production Number.
   *
   * @return this Production Run's Production Number.
   */
  public int getProductionNumber() {
    return this.productionNumber;
  }

  /**
   * Changes the Production Run's Product's Associated ID.
   *
   * @param productID desired new Product ID.
   */
  public void setProductID(int productID) {
    this.productID = productID;
  }

  /**
   * Accessor for Production Run's Product's ID.
   *
   * @return this Production Run's Product's ID.
   */
  public int getProductID() {
    return this.productID;
  }

  /**
   * Changes the Production Run's Serial Number.
   *
   * @param serialNumber desired new Serial Number.
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Accessor for Production Run's Product's Serial Number.
   *
   * @return this Production Run's Product's Serial Number.
   */
  public String getSerialNumber() {
    return this.serialNumber;
  }

  /**
   * Changes the Production Run's production date.
   *
   * @param dateProduced desired new Date.
   */
  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  /**
   * Accessor for Production Run's Date of Production.
   *
   * @return this Production Run's Production Date.
   */
  public Date getDateProduced() {
    return this.dateProduced;
  }

  /**
   * Accessor for Production Run's Product.
   *
   * @return A string with the following formatting: Prod Number: Production Number Product Name:
   *         Product's Name Serial Number: Serial Number Date: Date Produced
   */
  public String toString() {
    //"Prod. Num: 0 Product ID: 0 Serial Num: 0 Date: Mon Oct 14 10:29:48 UTC 2019"
    return "Prod. Num: " + productionNumber + " Product ID: " + productID + " "
        + "Serial Num: " + serialNumber + " Date: " + dateProduced;
  }

  /**
   * Method for returning a Formatted String that displays a Production Run's attributes.
   *
   * @param productName name of a Product for associated Production Run
   * @return A string with the following formatting: Prod Number: Production Number Product Name:
   *         Product's Name Serial Number: Serial Number Date: Date Produced
   */
  public String showProduction(String productName) {
    return "Prod. Num: " + productionNumber + " Product Name: " + productName
        + " Serial Num: " + serialNumber + " Date: " + dateProduced;
  }

}
