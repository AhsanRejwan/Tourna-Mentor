package sample;

import com.sun.corba.se.impl.orb.DataCollectorBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class FormatScreen implements Initializable {


    Database database = new Database();

    String x,y,table;

    int id =0;

    @FXML
    private TextField Name;
    @FXML
    private Button ContinueButton;

    @FXML
    private Button ConfirmButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private ListView<String> FormatList;



    @FXML
    void DataTypeBox(ActionEvent event) {

    }

    @FXML
    void ConfirmButton(ActionEvent event) {
        x=Name.getText();
        FormatList.getItems().addAll(x);
        Name.clear();
        table = database.GetCurrentTable();
        database.InsertColumns(table,x);
    }





    @FXML
    void DeleteButton(ActionEvent event) {
        x = FormatList.getSelectionModel().getSelectedItem();
        //System.out.println("List: " + x);
        FormatList.getItems().remove(x);
        table = database.GetCurrentTable();
        database.DeleteColumn(table,x);
    }

    @FXML
    void ContinueButton(ActionEvent event) throws IOException {
        x = FormatList.getSelectionModel().getSelectedItem();
        database.SetAttribute(x);

        id=  database.GetCurrentID();
        String type = database.GetCurrentType(id);
        if(type.equals("League"))
        {
            //System.out.println("league has happoned");
            database.GenerateFixturesLeague();
        }
        else
        {
            //System.out.println("knockout has happoned");
            database.GenerateFixturesKnockout();
        }

        Parent Screen = FXMLLoader.load(getClass().getResource("FixtureScreen.fxml"));
        Scene Scene = new Scene(Screen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
