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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExitScreen implements Initializable {


    @FXML
    private Button ExitButton;

    @FXML
    private Button BackButton;

    @FXML
    void ExitButton(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void BackButton(ActionEvent event) throws IOException {
        Parent Screen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene Scene = new Scene(Screen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}