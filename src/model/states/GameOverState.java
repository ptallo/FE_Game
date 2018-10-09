package model.states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameOverState implements StateInterface {
    private boolean won;
    private Font font = new Font("Monospaced", 32);

    public GameOverState(boolean won) {
        this.won = won;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        System.exit(0);
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        gc.setFill(Color.BLACK);
        gc.setFont(font);
        if (won) {
            String text = "You Won!";
            double width = getWidth(text);
            gc.fillText(text, -gc.getTransform().getTx() + (w / 2) - (width / 2), -gc.getTransform().getTy() + h/2, w);
        } else {
            String text = "You Lost!";
            double width = getWidth(text);
            gc.fillText(text, -gc.getTransform().getTx() + (w / 2) - (width / 2), -gc.getTransform().getTy() + h/2, w);
        }
    }

    private double getWidth(String string) {
        Text text = new Text(string);
        text.setFont(font);
        return text.getLayoutBounds().getWidth();
    }
}
