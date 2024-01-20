package world;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import world.model.*;

public class GamePresenter implements MapChangeListener {
    private static final int CELL_WIDTH = 40;
    private static final int CELL_HEIGHT = 40;
    @FXML
    public NumberTextField animalGenome;
    @FXML
    public NumberTextField animalPart;
    @FXML
    public NumberTextField animalEnergy;
    @FXML
    public NumberTextField animalEated;
    @FXML
    public NumberTextField animalChildren;
    @FXML
    public NumberTextField animalAscentands;
    @FXML
    public NumberTextField animalLife;
    @FXML
    public NumberTextField animalDeath;
    @FXML
    public NumberTextField animalsNumber;
    @FXML
    public NumberTextField plantsNumber;
    @FXML
    public NumberTextField freeZones;
    @FXML
    public NumberTextField popularGenome;
    @FXML
    public NumberTextField avgEnergy;
    @FXML
    public NumberTextField avgLife;
    @FXML
    public NumberTextField avgChildren;
    @FXML
    private GridPane mapGrid;
    @FXML
    private GridPane controller;
    private Simulation simulation;
    private  AbstractWorldMap map;
    private  SimulationSettings settings;
    private  Vector2d lowerLeft, upperRight;
    private  int width, height;
    private Animal chosenAnimal;


    public void drawMap(String message) {
        //                GridMapDrawer drawer = new GridMapDrawer(mapGrid,controller, simulation);
        Platform.runLater(this::draw);
        }
        @Override
    public void mapChanged(AbstractWorldMap worldMap, String message) {
        drawMap(message);
    }
    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
        map = simulation.getMap();
        settings = simulation.getSettings();
        lowerLeft = map.getLowerLeft();
        upperRight = map.getUpperRight();
        width = upperRight.getX() - lowerLeft.getX() + 1;
        height = upperRight.getY() - lowerLeft.getY() + 1;
    }
    public void draw() {
        clearGrid();
        setCellsSizes();
        drawAxis();
        drawWorldElements();
        drawButtons();
        if(chosenAnimal!=null){
            animalShow(chosenAnimal);
        }
        drawStatistics();
    }

    private void clearGrid() {
        System.out.println(mapGrid);
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void setCellsSizes() {
        int gridHeight = height + 1;
        int gridWidth = width + 1;
        for (int i = 0; i < gridHeight; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for (int i = 0; i < gridWidth; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
    }

    private void drawAxis() {
        Label label = new Label("y/x");
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, 0, 0 );
        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            label = new Label(Integer.toString(x));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 1-lowerLeft.getX() + x, 0 );
        }
        for (int y = upperRight.getY(); y >= lowerLeft.getY(); y--) {
            label = new Label(Integer.toString(y));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, upperRight.getY() - y + 1 );
        }

    }
    private void drawButtons(){
        Image stopImage = new Image("Stop.png");
        Image startImage = new Image("Start.png");
        Canvas canvas = createCanvas(stopImage);
        canvas.setOnMouseClicked(e -> stopButton());
        controller.add(canvas,10, 0);
        canvas = createCanvas(startImage);
        canvas.setOnMouseClicked(e -> startButton());
        controller.add(canvas,0, 0);
    }
    private void stopButton(){
        simulation.stop();
        System.out.println("stop");
    }
    private void startButton(){
        simulation.start();
        System.out.println("start");
    }
    private void drawStatistics(){
        animalsNumber.setText(String.valueOf(map.getAnimalsAmount()));
        plantsNumber.setText(String.valueOf(map.getGrassAmount()));
        freeZones.setText(String.valueOf(map.getFreeSquares()));
        popularGenome.setText(String.valueOf(map.getMostPopularGenomes().values()));
        avgEnergy.setText(String.valueOf(map.getAvgAnimalEnergy()));
        avgLife.setText(String.valueOf(map.getAvgLifespan()));
        avgChildren.setText(String.valueOf(map.getAvgChildrenAmount()));
    }

    private void animalShow(Animal animal){
        chosenAnimal = animal;
        animalGenome.setText(String.valueOf(animal.getGenes()));
        animalPart.setText(String.valueOf(animal.getCurrentGeneNumber()));
        animalEnergy.setText(String.valueOf(animal.getEnergy()));
        animalEated.setText(String.valueOf(animal.getGrassEaten()));
        animalChildren.setText(String.valueOf(animal.getChildrenAmount()));
        animalAscentands.setText(String.valueOf(animal.getOffspringsAmount()));
        animalLife.setText(String.valueOf(simulation.getDay()-animal.getDayOfBirth()));
        animalDeath.setText(String.valueOf(animal.getDayOfDeath()));
    }
    private void animalPressed(int x, int y){
        Animal chosenAnimal = map.getAnimalAt(new Vector2d(x,y));
        animalShow(chosenAnimal);
    }
    private void drawWorldElements() {
        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++) {
                Vector2d position = new Vector2d(1 - lowerLeft.getX() + x, 1 + upperRight.getY() -  y);
                Animal animal = map.getAnimalAt(position);
                if (animal != null) {
                    System.out.println(position);
                    System.out.println(animal);


                    Image animalImage = ImageLoader.getAnimalImage(animal.getDirection(), Math.max(0, animal.getEnergy() / settings.get("energyColorStep") - 1));
                    Canvas canvas = createCanvas(animalImage);
                    canvas.setOnMouseClicked(e -> animalPressed(position.getX(), position.getY()));
                    mapGrid.add(canvas, position.getX(), position.getY());
                } else if (map.isGrassAt(position)) {
                    Image grassImage = ImageLoader.getGrassImage();
                    Canvas canvas = createCanvas(grassImage);
                    mapGrid.add(canvas, position.getX(), position.getY());
                }
            }
        }
    }

    private Canvas createCanvas(Image image) {
        Canvas canvas = new Canvas(CELL_WIDTH, CELL_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0, CELL_WIDTH, CELL_HEIGHT);
        return canvas;
    }
}
