import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //Initial scene settings:
        //Scene scene = new Scene(root, 300, 275);

        //Updated scene dimensions:
        Scene scene = new Scene(root, 382, 515);


        primaryStage.setTitle("Production Program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
