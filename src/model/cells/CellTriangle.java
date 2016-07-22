package model.cells;
/**
 * Created by Stephen.Kwok on 7/21/2016.
 */
public class CellTriangle extends Cell {

    private boolean pointingUp;

    public CellTriangle(int initialState, double height, double width, int row, int column) {
        super(initialState, height, width, row, column);
        pointingUp = (row + column) % 2 == 0;
    }

    @Override
    protected double[] initializeCellDimensions() {
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

    @Override
    protected void setCellPosition() {
    	setLayoutX((getColumn() * getWidth()) / 2.0);
    	setLayoutY(getRow() * getHeight());
    }
}
