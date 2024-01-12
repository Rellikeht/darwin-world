package world;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import world.model.MapChangeListener;
import world.model.WorldMap;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private TextField textField;
    @FXML
    private Button startButton;
    @FXML
    private Label moveInfoLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private WorldMap map;

    public void drawMap(String message) {
        Platform.runLater(() -> {
            GridMapDrawer drawer = new GridMapDrawer(mapGrid, map);
            drawer.draw();
            moveInfoLabel.setText(message);
        });
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        drawMap(message);
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        Simulation simulation = new Simulation(10);
        map = simulation.getMap();
        simulation.addListener(this);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        startButton.setDisable(true);
        simulation.run();
    }
}
