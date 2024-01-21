package world;

import javafx.scene.image.Image;
import world.model.Direction;

public class ImageLoader {
    private static final Image grassImage = new Image("grass.png");
    public static Image getGrassImage(){return grassImage;}

    private static final Image startImage = new Image("Start.png");
    public static Image getStartImage() { return startImage; }
    private static final Image stopImage = new Image("Stop.png");
    public static Image getStopImage() { return stopImage; }

    private static final Image animalImage = new Image("Jungle.png");
    public static Image getAnimalImage() { return animalImage; }
    private static final Image popularImage = new Image("PopularCapi.png");
    public static Image getPopularImage() { return popularImage; }

    private static final String nameBase = "Capi";
    private static final String extension = "png";
    private static final int directions = Direction.values().length;
    private static final int variants = 4;
    private static String selectAnimalImage(int direction, int variant) {
        return "%s%d%d.%s".formatted(nameBase, direction, variant, extension);
    }

    private static final Image[][] animalImages = new Image[directions][variants];
    public static Image getAnimalImage(Direction direction, int variant) {
        return animalImages[direction.getNumber()][Math.min(variant, variants-1)];
    }

    static {
        for (int i = 0; i < directions; i++) {
            Image[] images = animalImages[i];
            for (int j = 0; j < variants; j++) {
                images[j] = new Image(selectAnimalImage(i, j));
            }
        }
    }

}
