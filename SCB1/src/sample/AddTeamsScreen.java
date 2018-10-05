package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AddTeamsScreen implements Initializable{

    String currenttable;
    Integer n,id, count;
    String name,captain,contact,org,sl,table;

    Database database = new Database();


    @FXML
    private TableColumn<Teams, String> TableContact;

    @FXML
    private TableColumn<Teams, String> TableOrg;

    @FXML
    private TableColumn<Teams, String> TableName;

    @FXML
    private TableColumn<Teams , String> TableCaptain;

    @FXML
    private TextField NameField;

    @FXML
    private TextField CaptainField;

    @FXML
    private TextField ContactField;


    @FXML
    private TextField OrgField;

    @FXML
    private TableView<Teams> PlayerTable;

    @FXML
    private Button ContinueButton;

    @FXML
    private Button AddButton;

    @FXML
    void AddButton(ActionEvent event) {

        //System.out.println("add clicked :" + sl+ name +captain+contact+org);

        n--;
        name = NameField.getText();
        captain = CaptainField.getText();
        contact = ContactField.getText();
        org = OrgField.getText();

        NameField.clear();
        CaptainField.clear();
        ContactField.clear();
        OrgField.clear();
        if(n==0)
        {
            AddButton.setDisable(true);
            ContinueButton.setDisable(false);
        }

        Teams team = new Teams(name,captain,contact,org);
        PlayerTable.getItems().add(team);
        database.InsertTeam(table, name,captain,contact,org);
    }

    @FXML
    void ContinueButton(ActionEvent event) throws IOException{
        Parent Screen = FXMLLoader.load(getClass().getResource("FormatScreen.fxml"));
        Scene Scene = new Scene(Screen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableName.setCellValueFactory(new PropertyValueFactory<Teams, String>("name"));
        TableCaptain.setCellValueFactory(new PropertyValueFactory<Teams, String>("captain"));
        TableContact.setCellValueFactory(new PropertyValueFactory<Teams, String>("contact"));
        TableOrg.setCellValueFactory(new PropertyValueFactory<Teams, String>("organization"));
        Database database = new Database();
        table = database.GetCurrentTable();
        id = database.GetCurrentID();
        n = database.GetTeamNumber(id);
        ContinueButton.setDisable(true);
        count =0;
    }
}
