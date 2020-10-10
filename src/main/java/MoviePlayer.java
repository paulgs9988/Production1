public class MoviePlayer extends Product implements MultimediaControl{
    public Screen screen;
    public MonitorType monitorType;

    public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType){
        this.name = name;
        this.manufacturer = manufacturer;
        this.screen = screen;
        this.monitorType = monitorType;
        this.type = ItemType.VISUAL;
    }


    @Override
    public String toString(){
        return "Name: "+this.name+"\nManufacturer: "+this.manufacturer+"\nType: "+this.type+"\n"+this.screen+"\nMonitor Type: "+this.monitorType;
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
