import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.GameScreen;

public class GameApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("FE model.Game");

        Group root = new Group();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight(), Color.LIGHTGRAY);
        primaryStage.setScene(scene);

        primaryStage.show();

        GameController controller = new GameController(primaryStage);
        GameScreen screen = new GameScreen(controller.getGame(), root, primaryStage.getWidth(), primaryStage.getHeight());

        screen.initDrawLoop();
    }
}