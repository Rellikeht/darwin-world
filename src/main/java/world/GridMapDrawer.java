package world;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import world.model.AbstractWorldMap;
import world.model.Animal;
import world.model.Vector2d;

public class GridMapDrawer {
    private static final int CELL_WIDTH = 40;
    private static final int CELL_HEIGHT = 40;

    private final GridPane mapGrid;
    private final AbstractWorldMap map;
    private final SimulationSettings settings;
    private final Vector2d lowerLeft, upperRight;
    private final int width, height;

    public GridMapDrawer(GridPane mapGrid, Simulation simulation) {
        this.mapGrid = mapGrid;
        map = simulation.getMap();
        settings = simulation.getSettings();
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

    private void drawWorldElements() {
        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++) {
                Vector2d position = new Vector2d(1 - lowerLeft.getX() + x, 1 + upperRight.getY() -  y);
                Animal animal = map.getAnimalAt(position);

                if (animal != null) {
                    //System.out.printf(
                    //        "%s - %s - %d\n",
                    //        position.toString(),
                    //        animal.toString(),
                    //        animal.getEnergy()
                    //);

                    // TODO nie wiem, czy energia jest dobrze
                    ImageView view = ImageLoader.getAnimalView(
                            animal.getDirection(),
                            Math.max(0, animal.getEnergy()/settings.get("energyColorStep") - 1)
                    );
                    GridPane.setHalignment(view, HPos.CENTER);
                    mapGrid.add(view, position.getX(), position.getY());

                } else if (map.isGrassAt(position)) {
                    ImageView view = ImageLoader.getGrassView();
                    GridPane.setHalignment(view, HPos.CENTER);
                    mapGrid.add(view, position.getX(), position.getY());
                }
            }
        }
    }

}