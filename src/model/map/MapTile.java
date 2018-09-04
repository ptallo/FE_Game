package model.map;

import components.render.RenderComponent;

public class MapTile {

    private RenderComponent renderComponent;
    private Boolean passable;

    public MapTile(String path, Boolean passable) {
        renderComponent = new RenderComponent(path, 32, 32, 1000);
        this.passable = passable;
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    public Boolean getPassable() {
        return passable;
    }
}
