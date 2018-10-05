package sample;
import com.sun.org.apache.xml.internal.security.Init;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;

public class MainScreen implements Initializable {
    @FXML
    private Button AboutButton;

    @FXML
    private Button ExitButton;

    @FXML
    private Button LoadButton;

    @FXML
    private Button CreateButton;

    @FXML
    private VBox MainScreen;



    @FXML
    void CreateTourn(ActionEvent event) throws IOException {
        Parent Screen = FXMLLoader.load(getClass().getResource("CreateScreen.fxml"));
        Scene Scene = new Scene(Screen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }


    @FXML
    void About(ActionEvent event) throws IOException {
        Parent Screen = FXMLLoader.load(getClass().getResource("AboutScreen.fxml"));
        Scene Scene = new Scene(Screen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    @FXML
    void Exit(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent Screen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));

        boolean result = popup.display("Exit menu", "are you sure?");
        if (result == true)
            window.close();
        else
            window.setScene(new Scene(Screen,600,400));


    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database database = new Database();
    }


}



