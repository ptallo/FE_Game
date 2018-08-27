package model;

import components.RenderComponent;
import javafx.scene.canvas.GraphicsContext;

public class Cursor {

    private Point selectionPoint;
    private RenderComponent renderComponent;

    public Cursor() {
        selectionPoint = new Point(0, 0);
        renderComponent = new RenderComponent("selection_cursor.png", 32, 32, 1000);
    }

    public void draw(GraphicsContext gc){
        renderComponent.draw(gc, selectionPoint);
    }
}
