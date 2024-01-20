package world;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import world.model.Direction;

public class ImageLoader {
    private static final String nameBase = "Capi";
    private static final String extension = "png";
    private static final int directions = Direction.values().length;
    private static final int variants = 4;
    private static String selectAnimalImage(int direction, int variant) {
        return "%s%d%d.%s".formatted(nameBase, direction, variant, extension);
    }

    private static final Image grassImage = new Image("grass.png");
    public static ImageView getGrassView() {
        return new ImageView(grassImage);
    }
    public static Image getGrassImage(){return grassImage;}
    private static final Image[][] animalImages = new Image[directions][variants];
    public static ImageView getAnimalView(Direction direction, int variant) {
        return new ImageView(animalImages[direction.getNumber()][Math.min(variant, variants-1)]);
    }
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
