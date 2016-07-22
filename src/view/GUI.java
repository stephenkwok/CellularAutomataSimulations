package view;

public class GUI extends BorderPane {

    private Group gridDisplay;
	private Scene scene;
	private BorderPane borderPane;
    private Toolbar toolbar;

	public GUI(double screenWidth, double screenHeight, Toolbar toolbar) {
        this.toolbar = toolbar;
        gridDisplay = new Group();
        setCenter(gridDisplay);
        setRight(toolbar);
		scene = new Scene(this, screenWidth, screenHeight);
	}

    public void setGridDisplay(Grid grid) {
        gridDisplay.getChildren().clear();
        for (int row = 0; row < grid.getNumberOfRows(); row++) {
            for (int column = 0; column < grid.getNumberOfColumns; column++) {
                Cell cell = grid.getCell(row, column);
                gridDisplay.getChildren().add(cell);
            }
        }
    }

	public Scene getScene() {return new Scene();}
	
}
