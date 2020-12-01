import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.scene.control.cell.PropertyValueFactory;





/* **********************************************************************************************
Controller Class:
Summary: This class is responsible for enacting the code that handles the actions and initialization
         of the Production program
 ********************************************************************************************** */
public class Controller<choiceItemType> implements Item {

    //ObservableLists and ArrayLists used throughout program:

    private ObservableList<Product> productLine = FXCollections.observableArrayList();
    private ObservableList<Product> productArray = FXCollections.observableArrayList();
    //private ArrayList<ProductionRecord> productionRun = new ArrayList<ProductionRecord>();
    //private ArrayList<ProductionRecord> productionLog = new ArrayList<ProductionRecord>();

    //The following four variables are used in determining the correct serial number:
    public int auMax;
    public int viMax;
    public int amMax;
    public int vmMax;
    public int maxProductionNumber;


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

    /* *************************************************************************************************
    Production Log Tab FXML Items below:
    Summary: The following area holds code for the various FXML items found on the "Production Log" tab
    ************************************************************************************************* */

    @FXML
    private TextArea productionLogTxt;

    /* *************************************************************************************************
    Product Line Tab FXML Items below:
    Summary: The following area holds code for the various FXML items found on the "Product Line" tab
    ************************************************************************************************* */

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtManufacturer;

    @FXML
    private ChoiceBox<Enum> choiceItemType;

    @FXML
    private TableView<Product> existingProductTableView;

    @FXML
    private TableColumn<?, ?> productNameColumn;

    @FXML
    private TableColumn<?, ?> productManufacturerColumn;

    @FXML
    private TableColumn<?, ?> productTypeColumn;

    //The following tag was used for the deleted "Product ID" column on Product Line's TableView:
    //@FXML
    //private TableColumn<?,?> productIdColumn;

    @FXML
    private void addProductButtonPush(ActionEvent event) {
        // ADD PRODUCT Button was clicked, do something...

        /* Use the following values for printing new item added to Console.
           Items with same names are used in connectToDB for adding to
           the database
         */

        if (txtProductName.getText() == null || txtProductName.getText().trim().isEmpty()) {
            System.out.println("ERROR: You must enter a value for Product Name.");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO PRODUCT NAME ENTERED");
            alert.setContentText("Please enter the Product's name.");
            alert.showAndWait();
        } else if (txtManufacturer.getText() == null || txtManufacturer.getText().trim().isEmpty()) {
            System.out.println("ERROR: You must enter a value for Manufacturer Name.");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO MANUFACTURER NAME ENTERED");
            alert.setContentText("Please enter the Product Manufacturer's name.");
            alert.showAndWait();
        } else if (choiceItemType.getValue() == null) {
            System.out.println("ERROR: Please select an Item Type.");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO ITEM TYPE SELECTED");
            alert.setContentText("Please select an Item Type from the drop menu.");
            alert.showAndWait();
        } else {
            String productName = txtProductName.getText();
            ItemType itemType = (ItemType) choiceItemType.getValue();
            String manufacturer = txtManufacturer.getText();

            Product product = new Widget(productName, manufacturer, itemType);

            connectToDb();

            //Adding "Product" to ObservableList and populate ListView with productArray:
            productLine.add(product);
            productArray.add(product);
            produceListView.setItems(productArray);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("PRODUCT SUCCESSFULLY ADDED");
            alert.setContentText("You've successfully added the following product to the database:\n\nName: " + productName + "\nType: " + itemType + "\nManufacturer: " + manufacturer);
            alert.showAndWait();
        }
    }

    /* *************************************************************************************************
    Produce Tab FXML Items below:
    Summary: The following area holds code for the various FXML items found on the "Produce" tab
    ************************************************************************************************* */

    @FXML
    private ListView<Product> produceListView;

    @FXML
    private ComboBox<String> cmbQuantity;

    @FXML
    private void recordProductionButton(ActionEvent event) {
        if (produceListView.getSelectionModel().getSelectedItem() == null && !cmbQuantity.getValue().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO PRODUCT SELECTED + INVALID QUANTITY");
            alert.setContentText("You must select a Product from the List View and enter \na number for Production Quantity.");
            alert.showAndWait();
        } else if (produceListView.getSelectionModel().getSelectedItem() == null) {
            System.out.println("ERROR: Please make a selection from the List View.");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO PRODUCT SELECTED");
            alert.setContentText("You must select a Product from the List View.");
            alert.showAndWait();
        } else if (cmbQuantity.getValue() == null) {
            System.out.println("ERROR: Please select or enter a quantity for this Production.");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO QUANTITY ENTERED");
            alert.setContentText("You must enter a number for the production quantity.");
            alert.showAndWait();
        } else if (!cmbQuantity.getValue().matches("\\d+")) {
            //Use REGEX to make sure user enters a number in Combo Quantity
            System.out.println("ERROR: You must enter a number.");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID QUANTITY ENTERED");
            alert.setContentText("You must enter a number for the production quantity.");
            alert.showAndWait();
        } else {
            ArrayList<ProductionRecord> productionRun = new ArrayList<ProductionRecord>();
        /*the following line of code garners the selected item from the Produce Tab's ListView and generates a production record
        of quantity relating to the number chosen from the dropdown menu and then a new Production Record is created when the "record
        production" button is clicked
        */

            //The Product's index at the user's selection in the GUI's ListView:
            int listViewSelection = produceListView.getSelectionModel().getSelectedIndex();

            //drop menu chosen value or entered value:
            int quantitySelection = Integer.valueOf(cmbQuantity.getValue());

            //The following lines separate the selected item based on itemType so that the proper serial number can be
            //generated based on the correlating itemType. auMax,viMax, etc correspond to the current Max value of the
            //serial number corresponding to a particular itemType in the PRODUCTIONRECORD database
            if (productArray.get(listViewSelection).getItemType() == ItemType.AUDIO) {
                for (int i = auMax + 1; i < quantitySelection + auMax + 1; i++) {
                    //productionRun.add(new ProductionRecord(productArray.get(listViewSelection), i));
                    ProductionRecord tempProductionRecord = new ProductionRecord(productArray.get(listViewSelection), i);
                    tempProductionRecord.setProductionNumber(maxProductionNumber + 1);
                    maxProductionNumber++;
                    productionRun.add(tempProductionRecord);
                }
            }
            if (productArray.get(listViewSelection).getItemType() == ItemType.VISUAL) {
                for (int i = viMax + 1; i < quantitySelection + viMax + 1; i++) {
                    //productionRun.add(new ProductionRecord(productArray.get(listViewSelection), i));
                    ProductionRecord tempProductionRecord = new ProductionRecord(productArray.get(listViewSelection), i);
                    tempProductionRecord.setProductionNumber(maxProductionNumber + 1);
                    maxProductionNumber++;
                    productionRun.add(tempProductionRecord);
                }
            }
            if (productArray.get(listViewSelection).getItemType() == ItemType.AUDIOMOBILE) {
                for (int i = amMax + 1; i < quantitySelection + amMax + 1; i++) {
                    //productionRun.add(new ProductionRecord(productArray.get(listViewSelection), i));
                    ProductionRecord tempProductionRecord = new ProductionRecord(productArray.get(listViewSelection), i);
                    tempProductionRecord.setProductionNumber(maxProductionNumber + 1);
                    maxProductionNumber++;
                    productionRun.add(tempProductionRecord);
                }
            }
            if (productArray.get(listViewSelection).getItemType() == ItemType.VISUALMOBILE) {
                for (int i = vmMax + 1; i < quantitySelection + vmMax + 1; i++) {
                    //productionRun.add(new ProductionRecord(productArray.get(listViewSelection), i));
                    ProductionRecord tempProductionRecord = new ProductionRecord(productArray.get(listViewSelection), i);
                    tempProductionRecord.setProductionNumber(maxProductionNumber + 1);
                    maxProductionNumber++;
                    productionRun.add(tempProductionRecord);
                }
            }

            addToProductionDB(productionRun);
            loadProductionLog();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("PRODUCTION SUCCESSFULLY RECORDED");
            alert.setContentText("You successfully added the following production:\n\nQuantity: " + quantitySelection + "\nProduct Name: " + productArray.get(listViewSelection).getName() + "\nProduct Manufacturer: " + productArray.get(listViewSelection).getManufacturer() + "\n\nSee 'Production Log' Tab for more information.");
            alert.showAndWait();
        }

    }

    /* *************************************************************************************************
    Employee Tab FXML Items below:
    Summary: The following area holds code for the various FXML items found on the "Employee" tab
    ************************************************************************************************* */
    @FXML
    private TextField employeeNameText;

    @FXML
    private TextField employeePasswordText;

    @FXML
    private TextArea employeeTextArea;

    @FXML
    void submitEmployeeButton(ActionEvent event) {
        String employeeNameString = employeeNameText.getText();
        String employeePassword = employeePasswordText.getText();
        Employee newEmployee = new Employee(employeeNameString, employeePassword);

        if (employeeNameText.getText().equals(("")) && employeePasswordText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO NAME OR PASSWORD ENTERED");
            alert.setContentText("You must enter your First Name and Last Name (For Example: John Doe), and a desired password that contains the following:\n\n-At least one capital letter\n-At least one lowercase letter\n-At least one number\n-At least one special character (i.e. !@#$%^)");
            alert.showAndWait();
        } else if (employeeNameText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO NAME ENTERED");
            alert.setContentText("You must enter your First Name and Last Name (For Example: John Doe) ");
            alert.showAndWait();
        } else if (!(employeeNameString.indexOf(" ") >= 0)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Name Entry");
            alert.setContentText("You must enter your First Name and Last Name separated by a space (For Example: John Doe) ");
            alert.showAndWait();
        } else if (employeePasswordText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("NO PASSWORD ENTERED");
            alert.setContentText("You must enter a desired password that contains the following:\n\n-At least one capital letter\n-At least one lowercase letter\n-At least one number\n-At least one special character (i.e. !@#$%^)");
            alert.showAndWait();
        } else if (!newEmployee.isValidPassword()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID PASSWORD ENTERED");
            alert.setContentText("You must enter a desired password that contains the following:\n\n-At least one capital letter\n-At least one lowercase letter\n-At least one number\n-At least one special character (i.e. !@#$%^)");
            alert.showAndWait();
        } else {
            employeeTextArea.clear();
            employeeTextArea.appendText(newEmployee.toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("EMPLOYEE INFO SUCCESSFULLY ENTERED");
            alert.setContentText("You've successfully created a new Username and Email Address.\nSee text box below for details.");
            alert.showAndWait();
        }
    }

    /* *************************************************************************************************
    Methods Not Related to FXML:
    Summary: The following area holds code for the various logical/"backend" methods that do things
             like populate arrays, make database queries, code for display of text in the GUI, etc
    ************************************************************************************************* */

    public void loadProductionLog() {
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
                productionLog.add(new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4)));
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

        serialConfigure(productionLog);
    }

    public void serialConfigure(ArrayList<ProductionRecord> productionLog) {
        //This method is used to garner the production runs that exist in the Database. It then performs
        //operations until the highest value serial number of each itemType is gotten so that the system
        //can maintain proper serial number generation. Most of the variables here are local and are only
        //used here and may not have the most intuitive/meaningful names.

        //Updated Functionality: This method is also used to get the max "Production Number" value from
        //the production log so that the subsequent created ProductionRecord will continue sequentially.


        ArrayList<String> auSerials = new ArrayList<String>();
        ArrayList<String> viSerials = new ArrayList<String>();
        ArrayList<String> amSerials = new ArrayList<String>();
        ArrayList<String> vmSerials = new ArrayList<String>();

        for (ProductionRecord pr : productionLog) {
            if (pr.getSerialNumber().contains("AU")) {
                auSerials.add(pr.getSerialNumber());
            } else if (pr.getSerialNumber().contains("VI")) {
                viSerials.add(pr.getSerialNumber());
            } else if (pr.getSerialNumber().contains("AM")) {
                amSerials.add(pr.getSerialNumber());
            } else if (pr.getSerialNumber().contains("VM")) {
                vmSerials.add(pr.getSerialNumber());
            }
        }

        ArrayList<String> auSerialsNum = new ArrayList<String>();
        ArrayList<String> viSerialsNum = new ArrayList<String>();
        ArrayList<String> amSerialsNum = new ArrayList<String>();
        ArrayList<String> vmSerialsNum = new ArrayList<String>();

        for (String st : auSerials) {
            auSerialsNum.add(st.replaceAll("\\D+", ""));
        }

        for (String st : viSerials) {
            viSerialsNum.add(st.replaceAll("\\D+", ""));
        }

        for (String st : amSerials) {
            amSerialsNum.add(st.replaceAll("\\D+", ""));
        }

        for (String st : vmSerials) {
            vmSerialsNum.add(st.replaceAll("\\D+", ""));
        }

        try {
            ArrayList<Integer> auNumbers = new ArrayList<Integer>();

            for (int i = 0; i < auSerialsNum.size(); i++) {
                auNumbers.add(Integer.valueOf(auSerialsNum.get(i)));
            }

            ArrayList<Integer> viNumbers = new ArrayList<Integer>();

            for (int i = 0; i < viSerialsNum.size(); i++) {
                viNumbers.add(Integer.valueOf(viSerialsNum.get(i)));
            }

            ArrayList<Integer> amNumbers = new ArrayList<Integer>();

            for (int i = 0; i < amSerialsNum.size(); i++) {
                amNumbers.add(Integer.valueOf(amSerialsNum.get(i)));
            }

            ArrayList<Integer> vmNumbers = new ArrayList<Integer>();

            for (int i = 0; i < vmSerialsNum.size(); i++) {
                vmNumbers.add(Integer.valueOf(vmSerialsNum.get(i)));
            }
            auMax = Collections.max(auNumbers);
            viMax = Collections.max(viNumbers);
            amMax = Collections.max(amNumbers);
            vmMax = Collections.max(vmNumbers);

            //System.out.print("The AU max is: "+auMax+"\nThe VI max is: "+viMax+"\nThe AM max is: "+amMax+"\nThe VM max is: "+vmMax);

        } catch (NoSuchElementException e) {
            int amMax = 0;
            int vmMax = 0;

        }
        ArrayList<Integer> productionNumbers = new ArrayList<Integer>();

        for (ProductionRecord pr : productionLog) {
            productionNumbers.add(pr.getProductionNumber());
        }
        maxProductionNumber = Collections.max(productionNumbers);

    }

    public void addToProductionDB(ArrayList<ProductionRecord> productionRun) {

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

            for (ProductionRecord pr : productionRun) {

                int productionNumber = pr.getProductionNumber();
                int productionID = pr.getProductID();
                String serialNumber = pr.getSerialNumber();
                //Date dateProduced = (Timestamp) pr.getDateProduced();
                Date date = new Date();
                Timestamp ts = new Timestamp(date.getTime());

                String insertSql = "INSERT INTO PRODUCTIONRECORD(PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM,DATE_PRODUCED) VALUES('" + productionNumber + "','" + productionID + "','" + serialNumber + "','" + ts + "')";

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

    public void showProduction(ArrayList<ProductionRecord> productionLog) {
        productionLogTxt.clear();
        //String nameForPrint = "";
        for (ProductionRecord pr : productionLog) {
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

    public void populateArrays() {
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
                productArray.add(new Widget(rs.getInt(1), rs.getString(2), rs.getString(4), ItemType.valueOf(rs.getString(3))));
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
    public void setUpProductLineTable() {

        //productIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
        productManufacturerColumn.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        existingProductTableView.setItems(productLine);
    }

    public void loadProductList() {
        populateArrays();
        produceListView.setItems(productArray);
        existingProductTableView.setItems(productArray);

    }

    //Method used to "initialize" the FX features (i.e. populate drop menus, print tests to
    //text areas, etc)
    public void initialize() {

        setUpProductLineTable();
        loadProductList();
        loadProductionLog();

        for (ItemType itemMenu : ItemType.values()) {

            choiceItemType.getItems().addAll(itemMenu);
        }

        //Allow users to enter their own value into Produce Tab ComboBox:
        cmbQuantity.setEditable(true);

        //Populate Produce tab combo box with values 1 to 10:
        for (int count = 1; count <= 10; count++) {
            cmbQuantity.getItems().add(String.valueOf(count));
        }
        //Default Produce Tab ComboBox to 1:
        cmbQuantity.getSelectionModel().selectFirst();

        //call testMultiMedia message which currently prints to the Console
        testMultimedia();

        //test ProductionRecord printing to Product Log Text area with default Production Record object
        ProductionRecord productionRecordTest = new ProductionRecord(0);

    }

    public void connectToDb() {
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


        Widget iPad = new Widget(productName, manufacturer, itemType);

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
            ps.setString(3, manufacturer);


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
                for (int i = 1; i < columnsNumber; i++)
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


