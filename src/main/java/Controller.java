import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;


public class Controller {

    //Array for populating drop down menu on Product Line tab:
    public String[] itemTypeArray = {"AUDIO", "VIDEO", "COMPUTER", "WATCH"};

    @FXML
    private void addProductButtonPush(ActionEvent event) {
        // ADD PRODUCT Button was clicked, do something...
        System.out.print("The ADD PRODUCT Button Works \n");
        connectToDb();
    }

    @FXML
    private void recordProductionButton(ActionEvent event){
        System.out.print("the RECORD PRODUCTION Button Works \n");
    }

    @FXML
    private ComboBox<String> cmbQuantity;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtManufacturer;

    @FXML
    private ChoiceBox<String> choiceItemType;

    public void initialize(){

        //Populate Product Tab drop menu using items in itemArrayType:
        for(int i = 0; i < itemTypeArray.length; i++) {
            choiceItemType.getItems().add(itemTypeArray[i]);
        }

        //Allow users to enter their own value into Produce Tab ComboBox:
        cmbQuantity.setEditable(true);

        //Populate Produce tab combo box with values 1 to 10:
        for(int count = 1; count<=10;count++){
            cmbQuantity.getItems().add(String.valueOf(count));
        }
        //Default Produce Tab ComboBox to 1:
        cmbQuantity.getSelectionModel().selectFirst();

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

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();

            String insertSql = "INSERT INTO Product(TYPE, MANUFACTURER, NAME ) VALUES('AUDIO','APPLE','iPod')";

            stmt.executeUpdate(insertSql);

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
