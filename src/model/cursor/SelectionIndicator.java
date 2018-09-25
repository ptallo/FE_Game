package model.cursor;

import components.render.RenderComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectionIndicator {

    private RenderComponent renderComponent;

    public SelectionIndicator() {
        renderComponent = new RenderComponent("tiles/selection_indicator.png", 32,32, 1000);
    }
}
