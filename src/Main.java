import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import controller.CustomerScreenController;
import model.ProductCatalog;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage secondStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/CustomerScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);


        primaryStage.setTitle("Customer");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX();
        double y = bounds.getMinY();
        primaryStage.setX(x);
        primaryStage.setY(y);

        FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("view/CashierScreen.fxml"));
        Parent secondRoot = secondLoader.load();
        Scene secondScene = new Scene(secondRoot);

        secondStage.setTitle("Cashier");
        secondStage.setScene(secondScene);
        secondStage.show();
        Rectangle2D bounds2 = Screen.getPrimary().getVisualBounds();
        double xx = bounds2.getMinX() + (bounds2.getWidth() - secondScene.getWidth()) * 1;
        double yy = bounds2.getMinY();
        secondStage.setX(xx);
        secondStage.setY(yy);

        Runtime.getRuntime().exec("java -jar ProductCatalog.jar");

        ProductCatalog productCatalog = ProductCatalog.getInstance();
        productCatalog.getAllProducts();

    }
}
