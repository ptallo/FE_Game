package model.states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import model.Game;

import java.util.ArrayList;

@Getter
@Setter
public class DialogState implements StateInterface {

    private double margin = 0.15;
    private int currentDialogIndex = 0;
    private StateInterface followingState;
    private ArrayList<String> dialog;
    private Game game;

    public DialogState(ArrayList<String> dialog, Game game, StateInterface followingState) {
        this.dialog = dialog;
        ArrayList<String> testDialog = new ArrayList<>();
        testDialog.add("I am testing");
        testDialog.add("1");
        testDialog.add("2");
        testDialog.add("3");
        this.dialog = testDialog;
        this.game = game;
        this.followingState = followingState;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        currentDialogIndex++;
        if (currentDialogIndex == dialog.size()) {
            if (followingState != null) {
                game.setCurrentState(followingState);
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
