package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class MapTile {

    private Image image;

    public MapTile(String path) {
        image = new Image(new File("resources/" + path).toURI().toString());
    }

    public void draw(GraphicsContext gc, Point mapCoordinates) {
        gc.drawImage(
                image,
                mapCoordinates.getX() * Map.Tile_Width,
                mapCoordinates.getY() * Map.Tile_Height,
                Map.Tile_Width,
                Map.Tile_Height
        );
    }
}
