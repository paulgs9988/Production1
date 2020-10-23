import java.util.Date;

public class ProductionRecord {

    //fields
    private int productionNumber;
    private int productID;
    private String serialNumber;
    private Date dateProduced;
    //private int typeCount;

    //constructor
    public ProductionRecord(int productID){
        this.productID = productID;
        this.serialNumber = "0";
        this.dateProduced = new Date();
    }
    //overloaded constructor. "itemCount" holds the count of the number of items
    // of its type "product" that have been created
    public ProductionRecord(Product product, int itemCount){
        String serialGeneration = "";

        //set first three characters of serial number to first 3 of manufacturer:
        if(product.getManufacturer().length() > 3) {
            serialGeneration = product.getManufacturer().substring(0, 3);
        }
        else{
            serialGeneration = product.getManufacturer();
        }
        serialGeneration = serialGeneration+product.getItemType().getCode();

        //appending formatted itemCount to end of serial number:
        String countString = "";
        if(itemCount<10){
            countString = "0000"+Integer.toString(itemCount);
        }
        else if(itemCount >=10 && itemCount <100){
            countString = "000"+Integer.toString(itemCount);
        }
        else if(itemCount >=100 && itemCount <1000){
            countString = "00"+Integer.toString(itemCount);
        }
        else if(itemCount >=1000 && itemCount <10000){
            countString = "0"+Integer.toString(itemCount);
        }
        else if(itemCount >=10000 && itemCount <100000){
            countString = Integer.toString(itemCount);
        }
        else{
            //this cumbersome bit just returns the last 5 characters of the String if it is a
            //huge number for whatever reason
            countString = Integer.toString(itemCount).substring(Integer.toString(itemCount).length()-5);
        }
        serialGeneration = serialGeneration+countString;
        this.serialNumber = serialGeneration;
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
