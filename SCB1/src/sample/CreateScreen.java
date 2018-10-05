package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.*;
import java.util.ResourceBundle;




public class CreateScreen implements Initializable{

    static Statement statement;
    static ResultSet result;
    String  name,type;
    String x;
    Integer number,id;


    String TypeChosen,NumChosen;

    @FXML
    private Button ContinueButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button CreateButton;

    @FXML
    private TextField TournName;

    @FXML
    private TextField TournId;

    @FXML
    private ComboBox<Integer> TournNo;

    @FXML
    private ComboBox<String> TournType ;

    @FXML
    private Label ConfirmLabel;


    @FXML
    void CreateButton(ActionEvent event) throws IOException   {


        String genid;
        name = TournName.getText();
        //id = Integer.parseInt(TournId.getText());
        type = TournType.getValue();
        number = TournNo.getValue();
        TournName.setDisable(true);
        //TournName.setVisible(false);
        CreateButton.setDisable(true);
        CreateButton.setVisible(false);
        TournNo.setDisable(true);
        TournType.setDisable(true);
        BackButton.setDisable(true);
        ContinueButton.setDisable(false);
        ContinueButton.setVisible(true);

        Database database = new Database();

            genid = database.CreateTournaments(name, type, number);
            TournId.setText(genid);
            ConfirmLabel.setText("Your Tournament has been created with ID: "+genid);
            ConfirmLabel.setVisible(true);
    }

    @FXML
    void ContinueButton(ActionEvent event) throws IOException{
        Parent Screen = FXMLLoader.load(getClass().getResource("AddTeamsScreen.fxml"));
        Scene Scene = new Scene(Screen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
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
        ConfirmLabel.setVisible(false);
        TournId.setDisable(true);
        TournType.getItems().addAll("League","KnockOut");
        TournNo.getItems().addAll(4,8,16);
        ContinueButton.setDisable(true);
        ContinueButton.setVisible(false);

    }
}
