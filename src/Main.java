import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import controller.CustomerScreenController;

public class Main extends Application {

    public static void main(String[] args) {
      //  System.out.println("wasd");
        launch(args);
      //  System.out.println("qwerty");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       // System.out.println("abc");

        Stage secondStage = new Stage();
        //System.out.println("def");

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/CustomerScreen.fxml")));
        primaryStage.setTitle("Customer");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX();
        double y = bounds.getMinY();
        primaryStage.setX(x);
        primaryStage.setY(y);

        //System.out.println("ghi");

        Scene secondScene = new Scene(FXMLLoader.load(getClass().getResource("view/CashierScreen.fxml")));
        secondStage.setTitle("Cashier");
        secondStage.setScene(secondScene);
        secondStage.show();
        Rectangle2D bounds2 = Screen.getPrimary().getVisualBounds();
        double xx = bounds2.getMinX() + (bounds2.getWidth() - scene.getWidth()) * 1;
        double yy = bounds2.getMinY();
        secondStage.setX(xx);
        secondStage.setY(yy);

        //System.out.println("jkl");
    }
}
