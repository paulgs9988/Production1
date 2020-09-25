public enum ItemType{

    //Default values in choice/drop menu:

    AUDIO("AU"),
    VISUAL("VI"),
    AUDIOMOBILE("AM"),
    VISUALMOBILE("VM");

    //enum's associated values:
    private final String code;

    ItemType(String code) {
        this.code = code;
    }


    //enum's getter for returning the code:
    public String getCode(){
        return code;
    }



}