package world;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import world.model.Vector2d;
import world.model.WorldMap;

public class GridMapDrawer {
    private static final int CELL_WIDTH = 40;
    private static final int CELL_HEIGHT = 40;

    private final GridPane mapGrid;
    private final WorldMap map;
    private final Vector2d lowerLeft, upperRight;
    private final int width, height;

    public GridMapDrawer(GridPane mapGrid, WorldMap map) {
        this.mapGrid = mapGrid;
        this.map = map;
        lowerLeft = map.getLowerLeft();
        upperRight = map.getUpperRight();
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
                //WorldElement element = map.objectAt(new Vector2d(x, y));
                //String labelText = element != null ? element.toString() : "";

                String labelText = map.getAt(new Vector2d(x, y));
                addToMapGrid(labelText,
                        1 - lowerLeft.getX() + x,
                        1 + upperRight.getY() -  y
                );
            }
        }
    }

    private void addToMapGrid(String text, int columnIndex, int rowIndex) {
        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, columnIndex, rowIndex );
    }
}