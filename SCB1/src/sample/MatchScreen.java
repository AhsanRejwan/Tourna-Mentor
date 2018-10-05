package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class MatchScreen implements Initializable{

    Database database = new Database();

    TextField[] text1 = new TextField[20];
    TextField[] text2 = new TextField[20];
    int a,b;

    int atno;
    String[] atnames = new String[20];

    String primary = new String();
    String currenttable = new String();


    @FXML
    private VBox Team2;

    @FXML
    private VBox Team1;

    @FXML
    private Button BackButton;

    @FXML
    void BackButton(ActionEvent event) {
        for(int k =0;k<atno;k++)
        {
            a = Integer.parseInt(text1[k].getText());
            b = Integer.parseInt(text2[k].getText());
            if(atnames[k].equals(primary))
            {
                if(a>b)
               {
                    database.SetWInner(currenttable,database.GetTeam1(FixtureScreen.match));
                    database.SetLoser(currenttable,database.GetTeam2(FixtureScreen.match));
               }
               else if(b>a)
               {
                   database.SetLoser(currenttable,database.GetTeam1(FixtureScreen.match));
                   database.SetWInner(currenttable,database.GetTeam2(FixtureScreen.match));
               }
               else
               {
                   database.SetDraw(currenttable,database.GetTeam1(FixtureScreen.match));
                   database.SetDraw(currenttable,database.GetTeam2(FixtureScreen.match));

               }
            }
            database.UpdateStanding(currenttable,atnames[k],database.GetTeam1(FixtureScreen.match),a);
            database.UpdateStanding(currenttable,atnames[k],database.GetTeam2(FixtureScreen.match),b);
        }
        Main.matchStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String x,y;
        //System.out.println(FixtureScreen.match);
         Label teams1 = new Label("team1: " +database.GetTeam1(FixtureScreen.match));
         Label teams2 = new Label("team2: "+database.GetTeam2(FixtureScreen.match));
         teams1.setFont(Font.font(20));
         teams1.setAlignment(Pos.CENTER);
         teams2.setFont(Font.font(20));
         teams2.setAlignment(Pos.CENTER);
         Team1.getChildren().addAll(teams1);
         Team2.getChildren().addAll(teams2);

        primary = database.GetCurrentAttribute();


        Label[] label1 = new Label[20];
        Label[] label2 = new Label[20];
        currenttable = database.GetCurrentTable();
         atno = database.GetAttributesNumber(currenttable);

        atnames = database.GetAttributes(currenttable);


         for(int i =0;i<atno;i++)
         {
             x =atnames[i];
             label1[i] = new Label(x);
             //label1[i].setText(atnames[i]);
             label1[i].setFont(Font.font(18));
             label2[i] = new Label(x);
             //label2[i].setText(atnames[i]);
             label2[i].setFont(Font.font(18));

             text1[i] = new TextField();
             text2[i] = new TextField();
            Team1.getChildren().addAll(label1[i],text1[i]);
            Team2.getChildren().addAll(label2[i],text2[i]);
         }

    }
}
