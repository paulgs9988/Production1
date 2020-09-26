public class Widget extends Product{

    public Widget(String name, String manufacturer, ItemType type){
        super(name, manufacturer, type);

    }
    public ItemType getType(){
        return this.type;
    }
    public void setType(ItemType type){
        this.type = type;
    }
}
