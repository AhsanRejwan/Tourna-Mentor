package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    static Stage matchStage = new Stage();
    static Stage standingStage = new Stage();

    Database database = new Database();

    @Override
    public void start(Stage primaryStage) throws Exception{

        database.InitGlobal();

        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("APP 1");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        //Scene scene = primaryStage.getScene();

        primaryStage.setOnCloseRequest(e->{
            e.consume();
            Scene scene = primaryStage.getScene();
            boolean result = popup.display("Exit menu", "are you sure?");
            if (result == true)
                primaryStage.close();
            else
                primaryStage.setScene(scene);
        });


    }



    public static void main(String[] args) {
        launch(args);
    }
}
