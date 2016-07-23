package model.cells;

/**
 * A Cell in the shape of a square
 */
public class CellSquare extends Cell {

	/**
	 * Instantiates square-shaped Cell
     * @param initialState: the initial state of the Cell
     * @param height: the height of the Cell
     * @param width: the width of the Cell
     * @param row: the Cell's row
     * @param column: the Cell's column
	 */
    public CellSquare(int initialState, double height, double width, int row, int column) {
        super(initialState, height, width, row, column);
    }

    /**
     * Initializes the Cell's Dimensions to represent a square
     * 
     * @return an array of doubles representing the Cell's dimensions
     */
    @Override
    protected double[] initializeCellDimensions() {
        double[] points = new double[] {0.0, 0.0,
                                        0.0, getHeight(),
                                        getWidth(), getHeight(),
                                        getWidth(), 0.0};
        return points;
    }

    /**
     * Sets the Cell's position (Layout X and Y in the Grid display) according to the Cell's
     * width, height, row, column, and shape
     */
    @Override
    protected void setCellPosition() {
        setLayoutX(getColumn() * getWidth());
        setLayoutY(getRow() * getHeight());
    }


}
