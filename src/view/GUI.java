package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import model.cells.Cell;
import model.grid.Grid;

/**
 * 
 * GUI displaying the Grid and Toolbar
 * 
 * @author Stephen
 *
 */
public class GUI extends BorderPane {

    private Group gridDisplay;
	private Scene scene;
	private BorderPane borderPane;
    private Toolbar toolbar;

    /**
     * Instantiates GUI
     * @param screenWidth: the width of the GUI
     * @param screenHeight: the height of the GUI
     * @param toolbar: Toolbar containing Buttons for controlling the Simulation
     */
	public GUI(double screenWidth, double screenHeight, Toolbar toolbar) {
        this.toolbar = toolbar;
        gridDisplay = new Group();
        setCenter(gridDisplay);
        setTop(toolbar.getContainer());
		scene = new Scene(this, screenWidth, screenHeight);
	}

	/**
	 * 
	 * Renders a Grid in the gridDisplay
	 * 
	 * @param grid: Grid to be rendered
	 */
    public void setGridDisplay(Grid grid) {
        gridDisplay.getChildren().clear();
        for (int row = 0; row < grid.getNumberOfRows(); row++) {
            for (int column = 0; column < grid.getNumberOfColumns(); column++) {
                Cell cell = grid.getCell(row, column);
                gridDisplay.getChildren().add(cell);
            }
        }
    }

    /**
     * 
     * @return the GUI's Scene
     */
	public Scene retrieveScene() {
		return scene;
	}
	
}
