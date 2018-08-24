import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("FE Game");

        Group root = new Group();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight(), Color.LIGHTGRAY);
        primaryStage.setScene(scene);

        final Canvas canvas = new Canvas(250,250);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.fillRect(20,20,100,100);

        root.getChildren().add(canvas);

        primaryStage.show();
    }
}