package world;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import world.model.Direction;
import world.model.MapChangeListener;
import world.model.Vector2d;
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

    //public void setWorldMap(WorldMap map) { this.map = map; }
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
        List<Direction> moves = OptionsParser.parse(
                textField.getText().split(" ")
        );
        List<Vector2d> startingPositions = List.of(
                new Vector2d(2, 2), new Vector2d(3, 4)
        );

        Simulation simulation = new Simulation(10);
        map = simulation.getMap();
        simulation.addListener(this);
        //map.addListener(this);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
        startButton.setDisable(true);
    }
}
