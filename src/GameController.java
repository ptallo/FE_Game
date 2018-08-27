import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Game;


public class GameController {

    private Game game;

    public GameController(Stage primaryStage) {
        game = new Game();
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
    }

    private void handleKeyEvent(KeyEvent event){
        game.handleKeyEvent(event);
    }

    public Game getGame() {
        return game;
    }
}
