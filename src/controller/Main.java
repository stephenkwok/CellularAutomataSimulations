package controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class Cellular Automata Simulation application
 * 
 * @author Stephen
 *
 */
public class Main extends Application {

    private final double SCREEN_WIDTH = 975;
    private final double SCREEN_HEIGHT = 800;
    private final String TITLE = "Cellular Automata Simulations";
    private Controller controller;

    /**
     * Starts the application
     */
    @Override
    public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        stage.setTitle(TITLE);
        stage.setResizable(true);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);
        controller = new Controller(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
