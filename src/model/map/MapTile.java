package model.map;

import components.RenderComponent;

public class MapTile {

    private RenderComponent renderComponent;

    public MapTile(String path) {
        renderComponent = new RenderComponent(path, 32, 32, 1000);
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }
}
