/**

 * This class is for creating Screen Objects which usually refer to the Screen in a Product.

 * A Screen implements the Screen Spec Interface to utilize the associated methods

 * @author Paul Sullivan

 */

public class Screen implements ScreenSpec{

    public String resolution;
    public int refreshRate;
    public int responseTime;
    /**

     * Constructor for a MoviePlayer Object

     * @param resolution Screen's resolution
     * @param refreshRate Screen's refresh rate
     * @param responseTime Screen's response time

     */
    public Screen(String resolution, int refreshRate, int responseTime){
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.responseTime = responseTime;
    }

    @Override
    public String toString(){
        return "Screen:\nResolution: " + this.resolution + "\nRefresh rate: " + this.refreshRate + "\nResponse time: " + this.responseTime;
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
