/**
 * A Widget is a Concrete Instantiation of an Abstract Object. It can be used
 *
 * <p>A Product in this program is any sort of device of the default "ItemTypes"</p>
 *
 * @author Paul Sullivan
 */

public class Widget extends Product {

  /**
   * Constructor for a Widget Object.
   *
   * @param name         Product's Name
   * @param manufacturer Manufacturer's name
   * @param type         the type of Product this is
   */
  public Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);

  }
  /**
   * Constructor for a Widget Object.
   *
   * @param name         Product's Name
   * @param manufacturer Manufacturer's name
   * @param type         the type of Product this is
   *
   */

  public Widget(int id, String name, String manufacturer, ItemType type) {
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  public ItemType getType() {
    return this.type;
  }

  public void setType(ItemType type) {
    this.type = type;
  }
}
