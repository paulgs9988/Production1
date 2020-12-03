/**
 * This class is for creating various types of Products.
 *
 * <p>A Product in this program is any sort of device of the default "ItemTypes"</p>
 *
 * @author Paul Sullivan
 */


abstract class Product implements Item {

  public int id;
  public String name;
  public ItemType type;
  public String manufacturer;

  public Product() {

  }

  /**
   * Constructor for a Product Object.
   *
   * @param name         Product's Name
   * @param manufacturer Manufacturer's name
   * @param type         the type of Product this is
   */
  public Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Method to get a Product's type.
   *
   * @return Returns' the Product's Item Type.
   */
  public ItemType getItemType() {
    return this.type;
  }

  /**
   * This is a method to return a String that gives information about the Product.
   *
   * @return A string with the following formatting: Name: Product's Name Manufacturer: Product's
             Manufacturer Type: Product Type
   */
  @Override
  public String toString() {
    return "Name: " + this.name + "\nManufacturer: " + this.manufacturer + "\nType: " + this.type;
  }

  /**
   * Accessor for Product's ID.
   *
   * @return this Product's ID.
   */
  @Override
  public int getID() {
    return this.id;
  }

  /**
   * Sets the Product's name.
   *
   * @param name Product's name.
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Accessor for getting a Product's name.
   *
   * @return this Product's Name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Sets the Product's Manufacturer.
   *
   * @param manufacturer manufacturer's name.
   */
  @Override
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Accessor for getting a Product's manufacturer.
   *
   * @return this Product's manufacturer.
   */
  @Override
  public String getManufacturer() {
    return this.manufacturer;
  }
}
