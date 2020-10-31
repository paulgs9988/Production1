import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;


public class Controller<choiceItemType> implements Item{

    @Override
    public int getInt() {
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

    //Originally a String array was used for the Product Tab's choice box, as follows:
    //public String[] itemTypeArray = {"AUDIO", "VIDEO", "COMPUTER", "WATCH"};

    //The array was replaced with an enum, ItemType

    @FXML
    private void addProductButtonPush(ActionEvent event) {
        /* Use these values for printing new item added to Console.
           Items with same names are used in connectToDB for adding to
           the database..UNCOMMENT BELOW ITEMS TO USE THIS FEATURE

         */

        String productName = txtProductName.getText();
        ItemType itemType = (ItemType) choiceItemType.getValue();
        String manufacturer = txtManufacturer.getText();

        Product product = new Widget(productName,manufacturer,itemType);
        // ADD PRODUCT Button was clicked, do something...
        //System.out.print("A Product has been added to the database \n");
        connectToDb();
        //System.out.print("The Product Name is: " +productName+ "\nThe Product Manufacturer is: "+manufacturer+"\nThe Product Type is: "+itemType+"\n\n");

        //Adding "Product" to ObservableList and populate ListView with productArray:
        productLine.add(product);
        productArray.add(product);
        produceListView.setItems(productArray);
    }

    public void populateArrays(){
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
                productArray.add(new Widget(rs.getString(2),rs.getString(4),ItemType.valueOf(rs.getString(3))));
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


    @FXML
    private void recordProductionButton(ActionEvent event){
        //System.out.print("the RECORD PRODUCTION Button Works \n");
        //the following line of code garners the selected item from the Produce Tab's ListView and generates a production record
        //of quantity relating to the number chosen from the dropdown menu.
        ProductionRecord productionRecord = new ProductionRecord(productArray.get(produceListView.getSelectionModel().getSelectedIndex()),Integer.valueOf(cmbQuantity.getValue()));
        productionLogTxt.appendText(productionRecord.toString()+"\n");

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

    //Observable Lists used throughout program:

    ObservableList<Product> productLine = FXCollections.observableArrayList();
    ObservableList<Product> productArray = FXCollections.observableArrayList();

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

    //Initialize the Product Line tab's table:
    public void setUpProductLineTable(){

        //productIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
        productManufacturerColumn.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        existingProductTableView.setItems(productLine);
    }

    //Method used to "initialize" the FX features (i.e. populate drop menus, print tests to
    //text areas, etc)
    public void initialize(){

        setUpProductLineTable();
        populateArrays();
        produceListView.setItems(productArray);

        //POPULATE THE PRODUCT TAB'S CHOICE BOX (DROP MENU) USING itemType ENUM:

                //(Originally I used an array with the following logic:

                    /*for(int i = 0; i < itemTypeArray.length; i++) {
                    //    choiceItemType.getItems().add(itemTypeArray[i]);}
                    */

        /*  choiceItemType refers to the Item Type Choice Box. This ENHANCED FOR LOOP
            allows us to add each enum item using .getItems().addAll().
        */
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

        //Below is the initial test to make sure text can print to the Production Log Text Area (using the
        //ProductionRecord class's toString method
        //productionLogTxt.setText(productionRecordTest.toString());

        //Now we will use the productLine ObservableList to populate the Production Log Text Area:
        //productionLogTxt.setText(String.valueOf(productLine));

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


