import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

abstract public class WindowModel extends Application
{

    abstract void initializeComponents(Parent root);

    abstract void initilizeAttributes(Stage stage);
}
