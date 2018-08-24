package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class Player {
    private Point selectionPoint = new Point(0, 0);
    private Image selectionImage = new Image(new File("resources/selection_cursor.png").toURI().toString());

    public void draw(GraphicsContext gc){
        gc.drawImage(
                selectionImage,
                selectionPoint.getX(),
                selectionPoint.getY(),
                selectionImage.getWidth(),
                selectionImage.getHeight()
        );
    }
}
