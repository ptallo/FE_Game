package components.render;

import javafx.scene.canvas.GraphicsContext;
import model.Point;
import model.map.Map;

public class RenderSystem {
    public void draw(RenderComponent component, GraphicsContext gc, Point point){
        if (component.getFrameCount() > 1) {
            animate(component);
        }

        gc.drawImage(
                component.getSelectionImage(),
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
}
