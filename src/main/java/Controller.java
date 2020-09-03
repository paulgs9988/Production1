import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // ADD PRODUCT Button was clicked, do something...
        System.out.print("The ADD PRODUCT Button Works \n");
    }
    @FXML
    private void recordProductionButton(ActionEvent event){
        System.out.print("the RECORD PRODUCTION Button Works \n");
    }


}