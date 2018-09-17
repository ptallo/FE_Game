package components.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import util.Point;
import model.map.Map;

public class RenderSystem {
    public void draw(RenderComponent component, GraphicsContext gc, Point point) {
        this.draw(component, gc, point, false);
    }

    public void draw(RenderComponent component, GraphicsContext gc, Point point, boolean drawGrey){
        if (component.getFrameCount() > 1) {
            animate(component);
        }

        gc.drawImage(
                drawGrey ? getGreyImage(component.getSelectionImage()) : component.getSelectionImage(),
                component.getCurrentFrame() * component.getFrameWidth(),
                0,
                component.getFrameWidth(),
                component.getFrameHeight(),
                point.getX() * Map.Tile_Width,
                point.getY() * Map.Tile_Height,
                Map.Tile_Width,
                Map.Tile_Height
        );
    }

    private void animate(RenderComponent component) {
        long currentTime = System.currentTimeMillis();
        if (Math.abs(currentTime - component.getLastAnimationTime()) > component.getAnimationDuration()) {
            if (component.getCurrentFrame() + 1 >= component.getFrameCount()) {
                component.setCurrentFrame(0);
            } else {
                component.setCurrentFrame(component.getCurrentFrame() + 1);
            }
            component.setAnimationDuration(currentTime);
        }
    }

    private Image getGreyImage(Image image) {
        WritableImage writableImage = new WritableImage(
                (int) image.getWidth(),
                (int) image.getHeight()
        );

        PixelWriter writer = writableImage.getPixelWriter();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = image.getPixelReader().getColor(x, y);
                writer.setColor(x, y, color.grayscale());
            }
        }

        return writableImage;
    }
}
