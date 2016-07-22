package controller;

public class Main {

    private final double SCREEN_WIDTH = 800;
    private final double SCREEN_HEIGHT = 800;
    private final String TITLE = "Cellular Automata Simulations";
    private Controller controller;
    private GUI view;

    @Override
    public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);
        view = new GUI(SREEEN_WIDTH, SCREEN_HEIGHT);
        myController = new Controller(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
