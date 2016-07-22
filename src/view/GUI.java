package view;

public class GUI {

    private Group gridDisplay;
	private Scene scene;
	private BorderPane borderPane;
    private Toolbar toolbar;

	public GUI(double screenWidth, double screenHeight) {
        gridDisplay = new Group();
        toolbar = new Toolbar();
        borderPane = new BorderPane();
        borderPane.setLeft(gridDisplay);
        borderPane.setRight(toolbar);
		scene = new Scene(borderPane, screenWidth, screenHeight);

	}

	public Scene getScene() {return new Scene();}
	
}
