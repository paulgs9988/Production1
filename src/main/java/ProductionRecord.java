import java.util.Date;

public class ProductionRecord {

    //fields
    private int productionNumber;
    private int productID;
    private String serialNumber;
    private Date dateProduced;

    //constructor
    public ProductionRecord(int productID){
        this.productID = productID;
        this.serialNumber = "0";
        this.dateProduced = new Date();
    }

    //overloaded constructor with parameters for all fields
    public ProductionRecord(int productionNumber, int productID, String serialNumber, Date dateProduced){
        this.productionNumber = productionNumber;
        this.productID = productID;
        this.serialNumber = serialNumber;
        this.dateProduced = dateProduced;
    }

    //Accessors and mutators for all fields below
    public void setProductionNumber(int productionNumber){
        this.productionNumber = productionNumber;
    }

    public int getProductionNumber(){
        return this.productionNumber;
    }

    public void setProductID(int productID){
        this.productID = productID;
    }

    public int getProductID(){
        return this.productID;
    }

    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber(){
        return this.serialNumber;
    }

    public void setDateProduced(Date dateProduced){
        this.dateProduced = dateProduced;
    }

    public Date getDateProduced(){
        return this.dateProduced;
    }

    public String toString(){
        //"Prod. Num: 0 Product ID: 0 Serial Num: 0 Date: Mon Oct 14 10:29:48 UTC 2019"
        return "Prod. Num: "+productionNumber+" Product ID: "+productID+" Serial Num: "+serialNumber+" Date: "+dateProduced;
    }
}
