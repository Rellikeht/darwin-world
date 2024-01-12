package world;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulationApp {

    public void start() throws Exception {
        Application.launch(SimulationPresenter.class);
    }

}
