package model.states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import model.Level;

import java.util.ArrayList;

@Getter
@Setter
public class DialogState implements StateInterface {

    private double margin = 0.15;
    private int currentDialogIndex = 0;
    private StateInterface followingState;
    private ArrayList<String> dialog;
    private Level level;

    public DialogState(ArrayList<String> dialog, Level level, StateInterface followingState) {
        this.dialog = dialog;
        this.level = level;
        this.followingState = followingState;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        currentDialogIndex++;
        if (currentDialogIndex == dialog.size()) {
            if (followingState != null) {
                level.setCurrentState(followingState);
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        gc.setFill(Color.BLACK);
        gc.fillText(dialog.get(currentDialogIndex), w * margin, h * margin, w - (2 * w * margin));
    }
}
