package model.cursor;

import components.render.RenderComponent;

public class SelectionIndicator {

    private RenderComponent renderComponent;

    public SelectionIndicator() {
        renderComponent = new RenderComponent("selection_indicator.png", 32,32, 1000);
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }
}
