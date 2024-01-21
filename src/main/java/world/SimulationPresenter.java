package world;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import world.model.AbstractWorldMap;
import world.model.MapChangeListener;

import java.io.IOException;

public class SimulationPresenter extends Application implements MapChangeListener {

    public VBox textBox;
    public ToolBar mapBar;
    public ToolBar mutationBar;
    public VBox saveBox;
    @FXML
    private Button Basic;
    @FXML
    private Button Custom;
    @FXML
    private Button startButton;
    @FXML
    private Label moveInfoLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private ToggleButton HellMap;
    @FXML
    private ToggleButton EarthMap;
    @FXML
    private ToggleButton SpecialMutation;
    @FXML
    private ToggleButton NormalMutation;
    @FXML
    private TextField mapWidth;
    @FXML
    private TextField mapHeight;
    @FXML
    private TextField staringGrass;
    @FXML
    private TextField dailyGrass;
    @FXML
    private TextField jungleSize;
    @FXML
    private TextField animalNumber;
    @FXML
    private TextField animalEnergy;
    @FXML
    private TextField grassEnergy;
    @FXML
    private TextField readyEnergy;
    @FXML
    private TextField procreationEnergy;
    @FXML
    private TextField minMutation;
    @FXML
    private TextField maxMutation;
    @FXML
    private VBox customBox;

    private final SimulationSettings settings = new SimulationSettings();
    private boolean isBasic = false;
    private GamePresenter presenter;

//    public void drawMap(String message) {
//        Platform.runLater(() -> {
//            GridMapDrawer drawer = new GridMapDrawer(mapGrid, simulation);
//            drawer.draw();
//            moveInfoLabel.setText(message);
//        });
//    };

    private void uploadSetting(TextField field, String name) {
        try {
            settings.set(name, Integer.parseInt(field.getText()));
        } catch (Exception ignored) {}
    }

    private void uploadSettings() {
        try {
            int widthNum = Integer.parseInt(mapWidth.getText());
            if (widthNum > 0) settings.set("MapWidth", widthNum);
        } catch (Exception ignored) {}

        try {
            int heightNum = Integer.parseInt(mapHeight.getText());
            if (heightNum > 0) settings.set("MapHeight", heightNum);
        } catch (Exception ignored) {}

        uploadSetting(staringGrass, "InitialGrassAmount");
        uploadSetting(dailyGrass, "DailyGrassAmount");
        uploadSetting(jungleSize, "JungleSize");
        uploadSetting(animalNumber, "InitialAnimalAmount");
        uploadSetting(animalEnergy, "InitialAnimalEnergy");
        uploadSetting(grassEnergy, "GrassEnergy");
        uploadSetting(readyEnergy, "EnergyNeededForProcreation");
        uploadSetting(procreationEnergy, "EnergyTakenByProcreation");
        uploadSetting(minMutation, "MinAmountOfMutations");
        uploadSetting(maxMutation, "MaxAmountOfMutations");
    }

    @Override
    public void mapChanged(AbstractWorldMap worldMap, String message) {
//        drawMap(message);
    }
    public void setHellMap(){settings.set("MapBasic", 0);}
    public void setEarthMap(){settings.set("MapBasic", 1);}
    public void setNormalMutation(){settings.set("MutationRandom", 0);}
    public void setSpecialMutation(){settings.set("MutationRandom", 1);}

    public void onSimulationStartClicked(ActionEvent actionEvent) throws IOException {
        Simulation simulation;

        if (!isBasic) {
            uploadSettings();
            simulation = new Simulation(new SimulationSettings(settings));
        } else {
            simulation = new Simulation(new SimulationSettings());
        }

        FXMLLoader loader = new FXMLLoader();
        Stage gameStage = new Stage();
        loader.setLocation(getClass().getClassLoader().getResource("game.fxml"));
        BorderPane viewRoot = loader.load();
        configureStage(gameStage, viewRoot);
        gameStage.show();
        gameStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent e){
                System.out.println("test");
                try {
                    stop();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        GamePresenter presenter = loader.getController();
        presenter.setSimulation(simulation);
        simulation.addListener(presenter);
        // meh
        Thread thread = new Thread(simulation);
        thread.start();
        //simulation.run();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void custom(){
        mutationBar.setVisible(true);
        mapBar.setVisible(true);
        startButton.setDisable(false);
        textBox.setVisible(true);
        saveBox.setVisible(true);
        isBasic = false;
    }

    public void basic(){
        mutationBar.setVisible(false);
        mapBar.setVisible(false);
        startButton.setDisable(false);
        textBox.setVisible(false);
        saveBox.setVisible(false);
        isBasic = true;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        configureStage(primaryStage, viewRoot);
        //SimulationPresenter presenter = loader.getController();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent e){
                System.out.println("test");
                try {
                    System.exit(0);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        primaryStage.show();

    }
}
