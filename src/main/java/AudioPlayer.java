/**
 * This class allows us to capture the details of an audio player.
 * <p>
 * AudioPlayer is a subclass of Product and implements the Multimedia Control interface.</p>
 *
 * @author Paul Sullivan
 */

public class AudioPlayer extends Product implements MultimediaControl {

  /**
   * The Audio Formats supported by this AudioPlayer.
   */
  public String supportedAudioFormats;
  /**
   * The supported playlist formats this AudioPlayer.
   */
  public String supportedPlaylistFormats;

  /**
   * Gets the first and last name of this Student.
   *
   * @param manufacturer             This is the manufacturer
   * @param name                     This is the AudioPlayer's name
   * @param supportedAudioFormats    This is the supported Audio Formats
   * @param supportedPlaylistFormats This is the supported Playlist Formats
   */
  public AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super();
    this.type = ItemType.AUDIO;
    this.name = name;
    this.manufacturer = manufacturer;
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;

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

  /**
   * This is a method to return a String that gives information about the AudioPlayer.
   *
   * @return A string with the following formatting: Name: AudioPlayer Name Manufacturer:
             AudioPlayer Manufacturer Type: AudioPlayer Type Supported Audio Formats: AudioPlayer's
             Supported Formats Supported Playlist Formats: AudioPlayer's Supported Playlist Formats
   */
  public String toString() {
    return "Name: " + this.name + "\nManufacturer: " + this.manufacturer + "\nType: " + this.type
        + "\nSupported Audio Formats: " + this.supportedAudioFormats
        + "\nSupported Playlist Formats: " + this.supportedPlaylistFormats;

  }
}
