package model.cells;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Base class for Cells. Provides framework for setting Cell's dimensions and position in the Grid 
 * based on the Cell's height, width, row, and column. Holds state that determines color of the cell
 * within each frame of the animation of the Simulation
 */
public abstract class Cell extends Polygon {

	private double[] points;
    private double height;
    private double width;
    private int row;
    private int column;
    private int currentState;
    private int previousState;
    private boolean hasChanged;

    /**
     * Instantiates a new Cell
     * @param initialState: the initial state of the Cell
     * @param height: the height of the Cell
     * @param width: the width of the Cell
     * @param row: the Cell's row
     * @param column: the Cell's column
     */
    public Cell(int initialState, double height, double width, int row, int column) {
        currentState = initialState;
        this.height = height;
        this.width = width;
        this.row = row;
        this.column = column;
        points = initializeCellDimensions();
        setCellDimensions();
        setCellPosition();
        setStroke(Color.BLACK);
    }

    /**
     * Initializes the Cell's Dimensions according to the Cell's width, height, and shape
     * 
     * @return an array of doubles representing the Cell's dimensions
     */
    protected abstract double[] initializeCellDimensions();

    /**
     * Sets the Cell's position (Layout X and Y in the Grid display) according to the Cell's
     * width, height, row, column, and shape
     */
    protected abstract void setCellPosition();
    
    /**
     * Sets the Cell's dimensions
     */
    private void setCellDimensions() {
    	for (double point : points) {
    		getPoints().add(point);
    	}
    }

    /**
     * Indicates whether the Cell has had its state changed within the current frame of the animation
     * @return true if Cell has been changed in the current frame; false otherwise
     */
    public boolean hasChanged() {
        return hasChanged;
    }

    /**
     * Updates the Cell's hasChanged status
     * @param changed: true if the Cell has changed in the current frame; false otherwise;
     */
    public void setHasChanged(boolean changed) {
        hasChanged = changed;
    }

    /**
     * Updates the Cell's state to the given state. Saves the state from the previous frame
     * so that neighboring Cells checking this Cell's state can reference the correct state
     * @param state: the Cell's state for the current frame
     */
    public void setState(int state) {
        previousState = currentState;
        currentState = state;
    }

    /**
     * 
     * Returns the previous state if the Cell has already been processed (has had its status updated) in the
     * current frame; Otherwise, returns the current state
     * 
     * @return the Cell's state 
     */
    public int getState() {
        return hasChanged ? previousState : currentState;
    }
    
    /**
     * @return the Cell's current state
     */
    public int getCurrentState() {
    	return currentState;
    }

    /**
     * @return the Cell's row in the Grid
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the Cell's column in the Grid
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return the Cell's width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the Cell's height
     */
    public double getHeight() {
        return height;
    }


}
