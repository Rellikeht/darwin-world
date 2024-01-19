package world;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import world.model.AbstractWorldMap;
import world.model.MapChangeListener;

public class GamePresenter implements MapChangeListener {
    @FXML
    private GridPane mapGrid;
    private Simulation simulation;
    public void drawMap(String message) {
        Platform.runLater(() -> {
            GridMapDrawer drawer = new GridMapDrawer(mapGrid, simulation);
            drawer.draw();
        });
    };
    @Override
    public void mapChanged(AbstractWorldMap worldMap, String message) {
        drawMap(message);
    }
    public void setSimulation(Simulation simulation){
        this.simulation=simulation;
    }
}
