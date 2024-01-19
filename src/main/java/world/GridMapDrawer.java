package world;

import javafx.geometry.HPos;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import world.model.AbstractWorldMap;
import world.model.Vector2d;

public class GridMapDrawer {
    private static final int CELL_WIDTH = 40;
    private static final int CELL_HEIGHT = 40;

    private final GridPane mapGrid;
    private final Simulation simulation;
    private final Vector2d lowerLeft, upperRight;
    private final int width, height;

    public GridMapDrawer(GridPane mapGrid, Simulation simulation) {
        this.mapGrid = mapGrid;
        this.simulation = simulation;
        lowerLeft = simulation.getLowerLeft();
        upperRight = simulation.getUpperRight();
        width = upperRight.getX() - lowerLeft.getX() + 1;
        height = upperRight.getY() - lowerLeft.getY() + 1;

        System.out.println();
        System.out.printf("%s %s\n", lowerLeft, upperRight);
        System.out.println();
    }

    public void draw() {
        clearGrid();
        setCellsSizes();
        drawAxis();
        drawWorldElements();
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
        addToMapGrid("y/x", 0, 0);
        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            addToMapGrid(Integer.toString(x), 1-lowerLeft.getX() + x, 0);
        }
        for (int y = upperRight.getY(); y >= lowerLeft.getY(); y--) {
            addToMapGrid(Integer.toString(y), 0, upperRight.getY() - y + 1);
        }
    }

    private void drawWorldElements() {
        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++) {
                Vector2d position = new Vector2d(1 - lowerLeft.getX() + x, 1 + upperRight.getY() -  y);
                int element = simulation.getAt(position);
                switch(element) {
                    case -2 -> {
                    }
                    case -1 -> addGrass(position);
                    default -> addCapibara(position,element);

                }
            }
        }
    }
    private void addGrass(Vector2d position){
        Image image = new Image("grass.png");
        ImageView view = new ImageView(image);
        GridPane.setHalignment(view, HPos.CENTER);
        mapGrid.add(view, position.getX(), position.getY() );
    }
        private void addCapibara(Vector2d position,int element){
        Image image = new Image("Capi2.png");
        int direction = element%8;
        int type=element/8;
        System.out.println(direction);
        System.out.println(type);
        switch(type) {
            //Tutaj zamiast kolejnych kapibar w drugim switchu bedą ich obroty ale nie mam dostępu do photoshopa na laptopie xd
            case 0 -> {
                switch (direction) {
                    case 0 -> image = new Image("Capi0.png");
                    case 1 -> image = new Image("Capi0.png");
                    case 2 -> image = new Image("Capi0.png");
                    case 3 -> image = new Image("Capi0.png");
                    case 4 -> image = new Image("Capi0.png");
                    case 5 -> image = new Image("Capi0.png");
                    case 6 -> image = new Image("Capi0.png");
                    case 7 -> image = new Image("Capi0.png");
                }
            }
            case 1 ->{
                switch (direction) {
                    case 0 -> image = new Image("Capi1.png");
                    case 1 -> image = new Image("Capi1.png");
                    case 2 -> image = new Image("Capi1.png");
                    case 3 -> image = new Image("Capi1.png");
                    case 4 -> image = new Image("Capi1.png");
                    case 5 -> image = new Image("Capi1.png");
                    case 6 -> image = new Image("Capi1.png");
                    case 7 -> image = new Image("Capi1.png");
                }
            }
            case 2 ->{
                switch (direction) {
                    case 0 -> image = new Image("Capi2.png");
                    case 1 -> image = new Image("Capi2.png");
                    case 2 -> image = new Image("Capi2.png");
                    case 3 -> image = new Image("Capi2.png");
                    case 4 -> image = new Image("Capi2.png");
                    case 5 -> image = new Image("Capi2.png");
                    case 6 -> image = new Image("Capi2.png");
                    case 7 -> image = new Image("Capi2.png");
                }
            }
            case 3 ->{
                switch (direction) {
                    case 0 -> image = new Image("Capi3.png");
                    case 1 -> image = new Image("Capi3.png");
                    case 2 -> image = new Image("Capi3.png");
                    case 3 -> image = new Image("Capi3.png");
                    case 4 -> image = new Image("Capi3.png");
                    case 5 -> image = new Image("Capi3.png");
                    case 6 -> image = new Image("Capi3.png");
                    case 7 -> image = new Image("Capi3.png");
                }
            }
            default ->  {
                switch (direction) {
                    case 0 -> image = new Image("Capi4.png");
                    case 1 -> image = new Image("Capi4.png");
                    case 2 -> image = new Image("Capi4.png");
                    case 3 -> image = new Image("Capi4.png");
                    case 4 -> image = new Image("Capi4.png");
                    case 5 -> image = new Image("Capi4.png");
                    case 6 -> image = new Image("Capi4.png");
                    case 7 -> image = new Image("Capi4.png");
                }
            }
        }
        ImageView view = new ImageView(image);
        GridPane.setHalignment(view, HPos.CENTER);
        mapGrid.add(view, position.getX(), position.getY() );
    }
    private void addToMapGrid(String text, int columnIndex, int rowIndex) {
        Image image = new Image("Capi4.png");
        ImageView view = new ImageView(image);
        GridPane.setHalignment(view, HPos.CENTER);
        mapGrid.add(view, columnIndex, rowIndex );
    }
}