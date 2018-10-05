package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StandingsScreen implements Initializable {

    @FXML
    private Button BackButton;

    @FXML
    private TableView<Standings> StandingsTable;

    @FXML
    void BackButton(ActionEvent event) {
        Main.standingStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] attributes = new String[50];
        String tablename;
        int number = 0;
        int rownumber = 0;
        int id;
        Database database = new Database();

        String[] rows = new String[50];

        tablename = database.GetCurrentTable();
        //System.out.println(tablename);
        number = database.GetAttributesNumber(tablename);
       // System.out.println(number);
        attributes = database.GetAttributes(tablename);
        TableColumn[] att = new TableColumn[50];

        int i=0;
        for(i=0;i<number;i++)
        {
            //System.out.println(attributes[i]);
            att[i] = new TableColumn(attributes[i]);
            StandingsTable.getColumns().addAll(att[i]);
           // att[i].setCellValueFactory(new PropertyValueFactory<Standings,Integer>("name"));
        }



        int teamno;
        number = database.GetCurrentID();
        teamno = database.GetTeamNumber(number);

        String[] names = new String[16];
        names = database.GetTeamNames(tablename);


        int[] attvalues = new int[50];

        Standings[] stclass = new Standings[16];

        number = database.GetAttributesNumber(tablename);

        for(int k=0;k<teamno;k++)
        {
            attvalues = database.GetTeamStandings(names[k],tablename,number);
            stclass[k] = new Standings(names[k],number,attvalues);
            StandingsTable.getItems().addAll(stclass[k]);
        }
    }
}
