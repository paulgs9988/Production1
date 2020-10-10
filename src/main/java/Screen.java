public class Screen implements ScreenSpec{

    public String resolution;
    public int refreshRate;
    public int responseTime;

    public Screen(String resolution, int refreshRate, int responseTime){
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.responseTime = responseTime;
    }

    @Override
    public String toString(){
        return "Screen:\nResolution: "+this.resolution+"\nRefresh rate: "+this.refreshRate+"\nResponse time: "+this.responseTime;
    }

    @Override
    public String getResolution() {
        return resolution;
    }

    @Override
    public int getRefreshRate() {
        return refreshRate;
    }

    @Override
    public int getResponseTime() {
        return responseTime;
    }
}
