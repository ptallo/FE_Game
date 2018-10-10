import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Level;


public class GameController {

    private Level level;

    public GameController(Stage primaryStage) {
        level = new Level(1);
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
    }

    private void handleKeyEvent(KeyEvent event){
        level.handleEvent(event);
    }

    public Level getLevel() {
        return level;
    }
}
