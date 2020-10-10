abstract public class Product implements Item {
    public int id;
    public ItemType type;
    public String manufacturer;
    public String name;

    public Product(){

    }
    public Product(String name, String manufacturer, ItemType type){
        this.name = name;
        this.manufacturer = manufacturer;
        this.type = type;
    }



    @Override
    public String toString(){
        return "Name: "+this.name+"\nManufacturer: "+this.manufacturer+"\nType: "+this.type;
    }

    @Override
    public int getInt() {
        return this.id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getManufacturer() {
        return this.manufacturer;
    }
}
