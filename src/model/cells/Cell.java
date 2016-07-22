package model.cells;
import javafx.scene.shape.*;

/**
 * Created by Stephen.Kwok on 7/21/2016.
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

    public Cell(int initialState, double height, double width, int row, int column) {
        currentState = initialState;
        this.height = height;
        this.width = width;
        this.row = row;
        this.column = column;
        initializeCellDimensions();
        setCellDimensions();
        setCellPosition();
    }

    protected abstract double[] initializeCellDimensions();

    protected abstract void setCellPosition();
    
    private void setCellDimensions() {
    	for (double point : points) {
    		getPoints().add(point);
    	}
    }

    public boolean hasChanged() {
        return hasChanged;
    }

    public void setHasChanged(boolean changed) {
        hasChanged = changed;
    }

    public void setState(int state) {
        previousState = currentState;
        currentState = state;
    }

    public int getState() {
        return hasChanged ? previousState : currentState;
    }
    
    public int getCurrentState() {
    	return currentState;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }


}
