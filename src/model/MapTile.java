package model;

import components.RenderComponent;
import javafx.scene.canvas.GraphicsContext;

public class MapTile {

    private RenderComponent renderComponent;

    public MapTile(String path) {
        renderComponent = new RenderComponent(path, 32, 32, 1000);
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }
}
