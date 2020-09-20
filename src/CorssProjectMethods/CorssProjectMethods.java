package CorssProjectMethods;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public interface CorssProjectMethods {

    public static File openFile(Stage stage){
        FileChooser fileChooser =  new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }
}


