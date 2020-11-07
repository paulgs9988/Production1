public class Widget extends Product{

    public Widget(String name, String manufacturer, ItemType type){
        super(name, manufacturer, type);

    }
    public Widget(int id, String name, String manufacturer, ItemType type){
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.type = type;
    }
    public ItemType getType(){
        return this.type;
    }
    public void setType(ItemType type){
        this.type = type;
    }
}
