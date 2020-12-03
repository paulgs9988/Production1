/**
 * This class is for creating MoviePlayer Objects which are types of Products.
 *
 * <p>A Movie Player extends the Product Class and Implements Multimedia Control Interface to
 * make use of the associated methods.</p>
 *
 * @author Paul Sullivan
 */

public class MoviePlayer extends Product implements MultimediaControl {

  public Screen screen;
  public MonitorType monitorType;

  /**
   * Constructor for a MoviePlayer Object.
   *
   * @param name         Product's Name
   * @param manufacturer Manufacturer's name
   * @param monitorType  type of monitor
   * @param screen       screen object
   */
  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.screen = screen;
    this.monitorType = monitorType;
    this.type = ItemType.VISUAL;
  }

  @Override
  public String toString() {
    return "Name: " + this.name + "\nManufacturer: " + this.manufacturer + "\nType: " + this.type
        + "\n" + this.screen + "\nMonitor Type: " + this.monitorType;
  }

  @Override
  public void play() {
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopping");

  }

  @Override
  public void previous() {
    System.out.println("Previous");

  }

  @Override
  public void next() {
    System.out.println("Next");

  }
}
