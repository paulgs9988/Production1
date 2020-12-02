/**

 * This is an Enum class that holds the information relating to the possible item types.

 * A Product can be one of the following: AUDIO, VISUAL, AUDIOMOBILE, or VISUALMOBILE.
 * Each type has an associated code: AU, VI, AM, VM.

 * @author Paul Sullivan

 */
public enum ItemType {

    //Default values in choice/drop menu:

    AUDIO("AU"),
    VISUAL("VI"),
    AUDIOMOBILE("AM"),
    VISUALMOBILE("VM");

    //enum's associated values:
    private final String code;
    /**

     * Sets the Item Type's Code.

     * @param code the desired code.

     */
    ItemType(String code) {
        this.code = code;
    }

    /**

     * Accessor to get a certain Type's code.

     * @return the Item Type's code.

     */
    public String getCode() {
        return code;
    }


}