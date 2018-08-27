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
        if (event.getCode() == KeyCode.UP){
            System.out.println("up");
        } else if (event.getCode() == KeyCode.LEFT) {
            System.out.println("left");
        } else if (event.getCode() == KeyCode.RIGHT) {
            System.out.println("right");
        } else if (event.getCode() == KeyCode.DOWN) {
            System.out.println("down");
        } else if (event.getCode() == KeyCode.ENTER) {
            System.out.println("enter");
        }
    }

    public Game getGame() {
        return game;
    }
}
