import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Scene;



public class ResultsPane extends WindowModel{
    String path = null;
    double I, V, P;
    ResultsPane(double I, double V, double P){
        this.I = I;
        this.V = V;
        this.P = P;
    }

    Label iHolder, vHolder, pHolder;
    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ui/resultsWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeComponents(root);
        initilizeAttributes(primaryStage);
        primaryStage.setTitle("ResultsPane");
        root.minHeight(800);
        primaryStage.setScene(new Scene(root));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    void initializeComponents(Parent root) {
        iHolder = (Label) root.lookup("#iHolder");
        vHolder = (Label) root.lookup("#vHolder");
        pHolder = (Label) root.lookup("#pHolder");
    }

    @Override
    void initilizeAttributes(Stage stage) {
        iHolder.setText(String.valueOf(this.I));
        vHolder.setText(String.valueOf(this.V));
        pHolder.setText(String.valueOf(this.P));

    }
}