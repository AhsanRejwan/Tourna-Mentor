package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class popup {

    static boolean answer;

    public  static boolean display (String title, String message ) {



        Stage pop = new Stage();
        pop. setTitle(title);
        pop.initModality(Modality.APPLICATION_MODAL);
        //pop.setMinWidth(250);

        Button b1 = new Button("yes");
        Button b2 = new Button("no");

        b1.setOnAction(e-> {
            answer = true;
            pop.close();
        });
        b2.setOnAction(e-> {
            answer = false;
            pop.close();
        });

        Label l = new Label(message);


        HBox h1 = new HBox(10);
        VBox v1 = new VBox (10);

        h1.getChildren().addAll(b1, b2);
        v1.getChildren().addAll(l,h1);
        v1.setAlignment(Pos.CENTER);
        h1.setAlignment(Pos.CENTER);

        Scene s1 = new Scene(v1,250, 100);
        pop.setScene(s1);
        pop.showAndWait();

        return answer;

    }
}
