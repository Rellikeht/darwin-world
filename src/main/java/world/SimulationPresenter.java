package world;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import world.model.MapChangeListener;
import world.model.WorldMap;

import java.util.function.UnaryOperator;

public class SimulationPresenter extends Application implements MapChangeListener {

    public VBox textBox;
    public ToolBar mapBar;
    public ToolBar mutationBar;
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
    private WorldMap map;
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
    SimulationSettings settings = new SimulationSettings();
    boolean basic = false;
    public void drawMap(String message) {
        Platform.runLater(() -> {
            GridMapDrawer drawer = new GridMapDrawer(mapGrid, map);
            drawer.draw();
            moveInfoLabel.setText(message);
        });
    };
    private void uploadSettings(){
        int widthNum=Integer.parseInt(mapWidth.getText());
        if(widthNum>0){settings.setMapWidth(widthNum);}
        int heightNum=Integer.parseInt(mapHeight.getText());
        if(heightNum>0){settings.setMapHeight(heightNum);}
        settings.setInitialGrassAmount(Integer.parseInt(staringGrass.getText()));
        settings.setDailyGrassAmount(Integer.parseInt(dailyGrass.getText()));
        settings.setJungleSize(Integer.parseInt(jungleSize.getText()));
        settings.setInitialAnimalAmount(Integer.parseInt(animalNumber.getText()));
        settings.setInitialAnimalEnergy(Integer.parseInt(animalEnergy.getText()));
        settings.setGrassEnergy(Integer.parseInt(grassEnergy.getText()));
        settings.setEnergyNeededForProcreation(Integer.parseInt(readyEnergy.getText()));
        settings.setEnergyTakenByProcreation(Integer.parseInt(procreationEnergy.getText()));
        settings.setMinAmountOfMutations(Integer.parseInt(minMutation.getText()));
        settings.setMaxAmountOfMutations(Integer.parseInt(maxMutation.getText()));

    }
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        drawMap(message);
    }
    public void setHellMap(){settings.setMapBasic(false);}
    public void setEarthMap(){settings.setMapBasic(true);}
    public void setNormalMutation(){settings.setMutationRandom(false);}
    public void setSpecialMutation(){settings.setMutationRandom(true);}
    public void onSimulationStartClicked(ActionEvent actionEvent) {
        if(!basic) {
            uploadSettings();
        }
        Simulation simulation = new Simulation(settings);
        map = simulation.getMap();// TODO nie wiem, czy ta mapa tutaj to dobry pomysł
        simulation.addListener(this);
        mutationBar.setVisible(false);
        mapBar.setVisible(false);
        textBox.setVisible(false);
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
    public void custom(){
        mutationBar.setVisible(true);
        mapBar.setVisible(true);
        startButton.setDisable(false);
        textBox.setVisible(true);
        basic=false;
    }
    public void basic(){
        mutationBar.setVisible(false);
        mapBar.setVisible(false);
        startButton.setDisable(false);
        textBox.setVisible(false);
        basic=true;
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
