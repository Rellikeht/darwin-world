package world;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import world.model.*;

import java.util.Objects;

public class GamePresenter implements MapChangeListener {

    @FXML
    public NumberTextField animalGenome;
    @FXML
    public NumberTextField animalPart;
    @FXML
    public NumberTextField animalEnergy;
    @FXML
    public NumberTextField grassEaten;
    @FXML
    public NumberTextField animalChildren;
    @FXML
    public NumberTextField animalAscendants;
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
    private SimulationStats stats;
    private  AbstractWorldMap map;
    private  SimulationSettings settings;
    private  Vector2d lowerLeft, upperRight;
    private  int width, height;
    private Animal chosenAnimal;
    private static int CELL_WIDTH = 40;
    private static int CELL_HEIGHT = 40;
    private boolean jungleCells=false;
    private boolean popularAnimal=false;
    private boolean buttonFlag=true;

    public void drawMap(String message) {
        Platform.runLater(this::draw);
    }

    @Override
    public void mapChanged(AbstractWorldMap worldMap, String message) {
        drawMap(message);
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
        stats = simulation.getStats();
        map = simulation.getMap();
        settings = simulation.getSettings();
        lowerLeft = map.getLowerLeft();
        upperRight = map.getUpperRight();
        width = upperRight.getX() - lowerLeft.getX() + 1;
        height = upperRight.getY() - lowerLeft.getY() + 1;
    }

    private void setCells(){
        CELL_WIDTH=400 / upperRight.getX();
        CELL_HEIGHT=400 / upperRight.getX();
    }

    public void draw() {
        setCells();
        clearGrid();
        setCellsSizes();
        drawAxis();
        if(buttonFlag) {
            drawButtons();
            buttonFlag=false;
        }
        if(jungleCells) drawJungleCels();
        drawWorldElements();
        if(popularAnimal) drawPopularAnimal();
        if(chosenAnimal!=null) animalShow(chosenAnimal);
        drawStatistics();
    }

    private void clearGrid() {
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
        Image startImage = ImageLoader.getStartImage();
        Image stopImage = ImageLoader.getStopImage();
        Canvas canvas = createButtonCanvas(stopImage);
        canvas.setOnMouseClicked(e -> stopButton());
        controller.add(canvas,10, 0);
        canvas = createButtonCanvas(startImage);
        canvas.setOnMouseClicked(e -> startButton());
        controller.add(canvas,0, 0);
    }

    private void stopButton(){
        simulation.stop();
        jungleCells=true;
        popularAnimal=true;
        draw();
    }
    private void startButton(){
        simulation.start();
        jungleCells=false;
        popularAnimal=false;
        draw();
    }

    private void drawJungleCels(){
        Vector2d lowerLeft=map.getLowerLeft();
        Vector2d upperRight=map.getUpperRight();
        int jungleStart = (upperRight.getY() - lowerLeft.getY() - settings.get("JungleSize")) / 2;
        int jungleEnd = jungleStart + settings.get("JungleSize");
        for(int y=jungleStart;y<jungleEnd+1;y++){
            for(int x=lowerLeft.getX();x<upperRight.getX()+1;x++){
                Vector2d position = new Vector2d(1 - lowerLeft.getX() + x, 1 + upperRight.getY() - y);
                Image animalImage = ImageLoader.getAnimalImage();
                Canvas canvas = createCanvas(animalImage);
                canvas.setOnMouseClicked(e -> animalPressed(position));
                mapGrid.add(canvas, position.getX(), upperRight.getY()-position.getY()+3);
            }
        }
    }

    private void drawPopularAnimal(){
        Image popularImage = ImageLoader.getPopularImage();
        Canvas canvas = createCanvas(popularImage);
        Genome popularGenome = stats.getMostPopularGenome();

        map.allAnimals().forEach(animal -> {
            if(animal.getGenes().equals(popularGenome)) {
                Vector2d position = animal.getPosition();
                canvas.setOnMouseClicked(e -> animalPressed(position));
                mapGrid.add(canvas, position.getX() + 1, upperRight.getY() - position.getY() + 1);
            }
        });
    }

    private void drawStatistics(){
        animalsNumber.setText(String.valueOf(stats.getAnimalsAmount()));
        plantsNumber.setText(String.valueOf(stats.getGrassAmount()));
        freeZones.setText(String.valueOf(stats.getFreeSquares()));
        popularGenome.setText(String.valueOf(stats.getMostPopularGenome()));
        avgEnergy.setText(String.valueOf(stats.getAvgAnimalEnergy()));
        avgLife.setText(String.valueOf(stats.getAvgLifespan()));
        avgChildren.setText(String.valueOf(stats.getAvgChildrenAmount()));
    }

    private void animalShow(Animal animal){
        chosenAnimal = animal;
        animalGenome.setText(String.valueOf(animal.getGenes()));
        animalPart.setText(String.valueOf(animal.getCurrentGeneNumber()));
        animalEnergy.setText(String.valueOf(animal.getEnergy()));
        grassEaten.setText(String.valueOf(animal.getGrassEaten()));
        animalChildren.setText(String.valueOf(animal.getChildrenAmount()));
        animalAscendants.setText(String.valueOf(animal.getOffspringsAmount()));
        animalLife.setText(String.valueOf(
                Objects.requireNonNullElse(
                        animal.getDayOfDeath(),
                        simulation.getDay()
                )-animal.getDayOfBirth())
        );
        animalDeath.setText(String.valueOf(animal.getDayOfDeath()));
    }

    private void animalPressed(Vector2d position){
        Animal chosenAnimal = map.getAnimalAt(position);
        animalShow(chosenAnimal);
    }

    private void drawWorldElements() {
        for (int x = lowerLeft.getX()-1; x <= upperRight.getX(); x++) {
            for (int y = lowerLeft.getY(); y <= upperRight.getY()+1; y++) {
                Vector2d position = new Vector2d(1 - lowerLeft.getX() + x, 1 + upperRight.getY() - y);
                Animal animal = map.getAnimalAt(position);

                if (animal != null) {
                    Image animalImage = ImageLoader.getAnimalImage(
                            animal.getDirection(),
                            Math.max(0, animal.getEnergy() / settings.get("energyColorStep") - 1)
                    );
                    Canvas canvas = createCanvas(animalImage);
                    canvas.setOnMouseClicked(e -> animalPressed(position));
                    mapGrid.add(canvas, position.getX()+1, upperRight.getY()-position.getY()+1);

                } else if (map.isGrassAt(position)) {
                    Image grassImage = ImageLoader.getGrassImage();
                    Canvas canvas = createCanvas(grassImage);
                    mapGrid.add(canvas, position.getX()+1, upperRight.getY()-position.getY()+1);
                }
            }
        }
    }

    private Canvas createButtonCanvas(Image image) {
        Canvas canvas = new Canvas(40, 40);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0, 40, 40);
        return canvas;
    }

    private Canvas createCanvas(Image image) {
        Canvas canvas = new Canvas(CELL_WIDTH, CELL_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0, CELL_WIDTH, CELL_HEIGHT);
        return canvas;
    }
}
