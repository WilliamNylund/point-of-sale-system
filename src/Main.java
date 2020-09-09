import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.CustomerScreenController;
import view.CustomerMonitor;

public class Main extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/CustomerScreen.fxml")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
