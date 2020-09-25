public class Widget extends Product{

    public Widget(String name, String manufacturer, String type){
        super(name, manufacturer, type);

    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
}
