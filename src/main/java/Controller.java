import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;
import java.sql.Timestamp;

import java.sql.*;
import java.util.ArrayList;

/* *************************************************************************************************
Controller Class:
Summary: This class is responsible for enacting the code that handles the actions and initialization of the Production program
 ************************************************************************************************* */

public class Controller<choiceItemType> implements Item{

    //ObservableLists and ArrayLists used throughout program:

    private ObservableList<Product> productLine = FXCollections.observableArrayList();
    private ObservableList<Product> productArray = FXCollections.observableArrayList();
    //private ArrayList<ProductionRecord> productionRun = new ArrayList<ProductionRecord>();
    //private ArrayList<ProductionRecord> productionLog = new ArrayList<ProductionRecord>();

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setManufacturer(String manufacturer) {

    }

    @Override
    public String getManufacturer() {
        return null;
    }
    //end overriding of interface Item's methods

    /* *************************************************************************************************
    FXML ITEMS:
    Summary: The following area holds the code that refers to FXML actions/items (i.e. items in the GUI
             like buttons, text areas, combo boxes, etc)
    ************************************************************************************************* */

    @FXML
    private void addProductButtonPush(ActionEvent event) {
        // ADD PRODUCT Button was clicked, do something...

        /* Use the following values for printing new item added to Console.
           Items with same names are used in connectToDB for adding to
           the database
         */

        String productName = txtProductName.getText();
        ItemType itemType = (ItemType) choiceItemType.getValue();
        String manufacturer = txtManufacturer.getText();

        Product product = new Widget(productName,manufacturer,itemType);

        connectToDb();

        //Adding "Product" to ObservableList and populate ListView with productArray:
        productLine.add(product);
        productArray.add(product);
        produceListView.setItems(productArray);
    }

    //Produce Tab's "Choose Quantity" drop menu
    @FXML
    private ComboBox<String> cmbQuantity;

    //Product Line Tab's "Product Name" text box
    @FXML
    private TextField txtProductName;

    //Product Line Tab's "manufacturer" text box
    @FXML
    private TextField txtManufacturer;

    //this refers to the Product Line Tab's "Item Type" drop menu
    @FXML
    private ChoiceBox<Enum> choiceItemType;

    //this refers to the Production Log Tab's Text Area
    @FXML
    private TextArea productionLogTxt;

    @FXML
    private TableView<Product> existingProductTableView;
    //The following tagged was used for the deleted "Product ID" column on Product Line's TableView
    //@FXML
    //private TableColumn<?,?> productIdColumn;

    @FXML
    private TableColumn<?, ?> productNameColumn;

    @FXML
    private TableColumn<?, ?> productManufacturerColumn;

    @FXML
    private TableColumn<?, ?> productTypeColumn;

    @FXML
    private ListView<Product> produceListView;

    @FXML
    private void recordProductionButton(ActionEvent event){

        ArrayList<ProductionRecord> productionRun = new ArrayList<ProductionRecord>();
        /*the following line of code garners the selected item from the Produce Tab's ListView and generates a production record
        of quantity relating to the number chosen from the dropdown menu and then a new Production Record is created when the "record
        production" button is clicked
        */

        //The Product's index at the user's selection in the GUI's ListView:
        int listViewSelection = produceListView.getSelectionModel().getSelectedIndex();

        //drop menu chosen value or entered value:
        int quantitySelection = Integer.valueOf(cmbQuantity.getValue());


        //loadProductionLog();
        //showProduction();

        ProductionRecord productionRecord = new ProductionRecord(productArray.get(listViewSelection),quantitySelection);
        //loadProductionLog(productionRecord);
        productionRun.add(productionRecord);
        //productionLogTxt.appendText(productionRecord.toString()+"\n");
        addToProductionDB(productionRun);
        loadProductionLog();
        //showProduction(productionLog);


    }

    /* *************************************************************************************************
    Methods Not Related to FXML:
    Summary: The following area holds code for the various logical/"backend" methods that do things
             like populate arrays, make database queries, code for displays, etc
    ************************************************************************************************* */

    public void loadProductionLog(){
        /* This method creates and populates the productionLog ArrayList by going to the
        ProductRecord database and adding each ProductionRecord item to the ArrayList
         */

        ArrayList<ProductionRecord> productionLog = new ArrayList<ProductionRecord>();

        //Make sure to use correct database name from res folder (i.e. 'productiondb')
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/productiondb";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();

            //use code below to print the entire PRODUCT database's contents to the console
            ResultSet rs = stmt.executeQuery("select * from productionrecord");

            while (rs.next()) {
                productionLog.add(new ProductionRecord(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDate(4)));
            }


            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        showProduction(productionLog);

    }
    public void addToProductionDB(ArrayList<ProductionRecord> productionRun){

            //Make sure to use correct database name from res folder (i.e. 'productiondb')
            final String JDBC_DRIVER = "org.h2.Driver";
            final String DB_URL = "jdbc:h2:./res/productiondb";

            //  Database credentials
            final String USER = "";
            final String PASS = "";
            Connection conn = null;
            Statement stmt = null;

            try {
                // STEP 1: Register JDBC driver
                Class.forName(JDBC_DRIVER);

                //STEP 2: Open a connection
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                //STEP 3: Execute a query
                stmt = conn.createStatement();

                for(ProductionRecord pr : productionRun) {

                    int productionNumber = pr.getProductionNumber();
                    int productionID = pr.getProductID();
                    String serialNumber = pr.getSerialNumber();
                    //Date dateProduced = (Timestamp) pr.getDateProduced();
                    Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());

                    String insertSql = "INSERT INTO PRODUCTIONRECORD(PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM,DATE_PRODUCED) VALUES('"+productionNumber+"','"+productionID+"','"+serialNumber+"','"+ts+"')";

                    //Use the following commented out code if a Prepared Statement is preferable for accomplishing this insertion

                    //String insertSqlps = "INSERT INTO PRODUCTIONRECORD(PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM,DATE_PRODUCED) VALUES(?,?,?,?)";


                    //PreparedStatement ps = conn.prepareStatement(insertSqlps);

                    //ps.setString(1, String.valueOf(productionNumber));
                    //ps.setString(2, String.valueOf(productionID));
                    //ps.setString(3, serialNumber);
                    //ps.setString(4, dateProduced.toString());

                    //String sql = "INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER) VALUES('"+iPad.getName()+"','"+iPad.getType()+"','"+iPad.getManufacturer()+"')";

                    stmt.executeUpdate(insertSql);
                    //stmt.executeUpdate(sql);
                    //ps.executeUpdate();
                }

                // STEP 4: Clean-up environment
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
    public void showProduction(ArrayList<ProductionRecord> productionLog){
        productionLogTxt.clear();
        //String nameForPrint = "";
        for(ProductionRecord pr : productionLog) {
            //The logic in this nested loop is for garnering the Product's name from the
            //productArray ArrayList and printing it to the Production Record text area.

            String nameForPrint = null;
            for (int i = 0; i < productArray.size(); i++) {
                if (productArray.get(i).getID() == pr.getProductID()) {
                    nameForPrint = productArray.get(i).getName();
                }
            }
            productionLogTxt.appendText(pr.showProduction(nameForPrint) + "\n");

        }
    }


    //Use polymorphism to test multimedia devices. An ArrayList of type interface (MultimediaControl) is used to populate
    //an ArrayList of AudioPlayers and MoviePlayers. Currently this test just prints to the Console.
    public static void testMultimedia() {
        AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
                "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
        Screen newScreen = new Screen("720x480", 40, 22);
        MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
                MonitorType.LCD);
        ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
        productList.add(newAudioProduct);
        productList.add(newMovieProduct);
        for (MultimediaControl p : productList) {
            System.out.println(p);
            p.play();
            p.stop();
            p.next();
            p.previous();
        }
    }

    public void populateArrays(){
        /* This method populates the productArray ObservableList by going to the Product database and adding each
        product to the ObservableList
         */

        //Make sure to use correct database name from res folder (i.e. 'productiondb')
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/productiondb";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();

            //use code below to print the entire PRODUCT database's contents to the console
            ResultSet rs = stmt.executeQuery("select * from product");

            while (rs.next()) {
                productArray.add(new Widget(rs.getInt(1),rs.getString(2),rs.getString(4),ItemType.valueOf(rs.getString(3))));
            }


            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Initialize the Product Line tab's table:
    public void setUpProductLineTable(){

        //productIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
        productManufacturerColumn.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        existingProductTableView.setItems(productLine);
    }

    public void loadProductList(){
        populateArrays();
        produceListView.setItems(productArray);
        existingProductTableView.setItems(productArray);

    }

    //Method used to "initialize" the FX features (i.e. populate drop menus, print tests to
    //text areas, etc)
    public void initialize(){

        setUpProductLineTable();
        loadProductList();
        loadProductionLog();

        for(ItemType itemMenu: ItemType.values()){

            choiceItemType.getItems().addAll(itemMenu);
        }

        //Allow users to enter their own value into Produce Tab ComboBox:
        cmbQuantity.setEditable(true);

        //Populate Produce tab combo box with values 1 to 10:
        for(int count = 1; count<=10;count++){
            cmbQuantity.getItems().add(String.valueOf(count));
        }
        //Default Produce Tab ComboBox to 1:
        cmbQuantity.getSelectionModel().selectFirst();

        //call testMultiMedia message which currently prints to the Console
        testMultimedia();

        //test ProductionRecord printing to Product Log Text area with default Production Record object
        ProductionRecord productionRecordTest = new ProductionRecord(0);

    }

    public void connectToDb(){
        //Make sure to use correct database name from res folder (i.e. 'productiondb')
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/productiondb";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

        String productName = txtProductName.getText();
        ItemType itemType = (ItemType) choiceItemType.getValue();
        String manufacturer = txtManufacturer.getText();


        Widget iPad = new Widget(productName,manufacturer,itemType);

        ArrayList<Widget> widgets = new ArrayList<Widget>();
        widgets.add(iPad);

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();

            //String insertSql = "INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER) VALUES('"+productName+"','"+itemType+"','"+manufacturer+"')";

            String insertSqlps = "INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER) VALUES(?,?,?)";


            PreparedStatement ps = conn.prepareStatement(insertSqlps);

            ps.setString(1, productName);
            ps.setString(2, String.valueOf(itemType));
            ps.setString(3,manufacturer);


            //String sql = "INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER) VALUES('"+iPad.getName()+"','"+iPad.getType()+"','"+iPad.getManufacturer()+"')";

            //stmt.executeUpdate(insertSql);
            //stmt.executeUpdate(sql);
            ps.executeUpdate();

            //use code below to print the entire PRODUCT database's contents to the console
            ResultSet rs = stmt.executeQuery("select * from product");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            //iterate through ResultSet to get all columns' data:
            while (rs.next()) {
                for(int i = 1; i < columnsNumber; i++)
                    System.out.print(rs.getString(i) + " ");
                System.out.println();
            }

            System.out.println(widgets.get(0).toString());

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


