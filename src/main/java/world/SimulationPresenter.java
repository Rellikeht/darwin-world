package world;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import world.model.MapChangeListener;
import world.model.WorldMap;

public class SimulationPresenter extends Application implements MapChangeListener {
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
        // TODO ustawianie ustawień
        SimulationSettings settings = new SimulationSettings();
        Simulation simulation = new Simulation(settings);
        map = simulation.getMap();
        simulation.addListener(this);
        startButton.setDisable(true);
        simulation.run();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        configureStage(primaryStage, viewRoot);

        //SimulationPresenter presenter = loader.getController();
        primaryStage.show();
    }
}
