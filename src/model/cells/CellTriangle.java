package model.cells;
/**
 * A Cell in the shape of a triangle
 * 
 * @author Stephen
 */
public class CellTriangle extends Cell {

	/**
	 * Instantiates triangle-shaped Cell
     * @param initialState: the initial state of the Cell
     * @param height: the height of the Cell
     * @param width: the width of the Cell
     * @param row: the Cell's row
     * @param column: the Cell's column
	 */
    public CellTriangle(int initialState, double height, double width, int row, int column) {
        super(initialState, height, width, row, column);
    }
    
    /**
     * Initializes cell dimensions according to its row and column: Row and columns that add up
     * to an even number yield a triangle pointing up; Row and columns that sum to odd number yield
     * triangle pointing down;
     */
    @Override
    protected double[] initializeCellDimensions() {
    	boolean pointingUp = (getRow() + getColumn()) % 2 == 0;
    	double[] points;
    	if (pointingUp) {
    		points =  new double[] {0.0, getHeight(),
    							 	getWidth() / 2, 0.0,
    							 	getWidth(), getHeight()};
    	}
    	else {
    		points = new double[] {0.0, 0.0,
    							   getWidth(), 0.0,
    							   getWidth() / 2, getHeight()};
    	}
    	return points;
    }

    /**
     * Sets the Cell's position (Layout X and Y in the Grid display) according to the Cell's
     * width, height, row, column, and shape
     */
    @Override
    protected void setCellPosition() {
    	setLayoutX((getColumn() * getWidth()) / 2.0);
    	setLayoutY(getRow() * getHeight());
    }
}
