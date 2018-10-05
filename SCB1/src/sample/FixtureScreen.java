package sample;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class FixtureScreen implements Initializable {
    static Integer count =1,match =0;
    Integer i,attributeno, currentid,fixtureno;
    Connection conn;
    Statement statement;
    ResultSet result,rs;
    String list;
    String[] fixtures = new String[256];

    @FXML
    private ListView<String> FixtureTable;

    @FXML
    private Button FinishButton;

    @FXML
    private Button MatchButton;

    @FXML
    private Button StandingsButton;

    @FXML
    void MatchButton(ActionEvent event) throws IOException{
        match = FixtureTable.getSelectionModel().getSelectedIndex();
       // list = FixtureTable.getSelectionModel().getSelectedItem();
        //FixtureTable.getSelectionModel().getSelectedItems().remove(list);
        count++;
        if(count > fixtureno){
            MatchButton.setText("The End");
            MatchButton.setDisable(true);
            FinishButton.setDisable(false);
        }

        Parent Screen = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        Scene scene = new Scene(Screen,500,350);

        Main.matchStage.setScene(scene);
        Main.matchStage.show();
    }

    @FXML
    void StandingsButton(ActionEvent event) throws IOException {
        Parent Screen = FXMLLoader.load(getClass().getResource("StandingsScreen.fxml"));
        Scene Scene = new Scene(Screen,500,350);


        Main.standingStage.setScene(Scene);
        Main.standingStage.show();
    }

    @FXML
    void FinishButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        FinishButton.setDisable(true);
        Database database = new Database();
        currentid = database.GetCurrentID();
        fixtureno=database.GetFixtureSize();
        //System.out.println("fixnum in scree: " +fixtureno);
        MatchButton.setText("Go to Match");
        FixtureTable.getItems().addAll("\t\t\t\t\t  Fixtures");
        fixtures = database.GetFixture();
        for(int j =0;j<=(fixtureno*2)-1;j+=2)
        {
            FixtureTable.getItems().addAll("\t\t\t\t"+fixtures[j]+ "\t\tvs\t\t" + fixtures[j+1]);
        }
    }
}
