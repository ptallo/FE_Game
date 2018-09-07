import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Game;


public class GameController {

    private Game game;

    public GameController(Stage primaryStage) {
        game = new Game();
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
        primaryStage.addEventFilter(MouseEvent.MOUSE_CLICKED, this::handleMouseEvent);
    }

    private void handleKeyEvent(KeyEvent event){
        game.handleKeyEvent(event);
    }

    private void handleMouseEvent(MouseEvent event){
        game.handleMouseEvent(event);
    }

    public Game getGame() {
        return game;
    }
}
