package model.cells;
/**
 * Created by Stephen.Kwok on 7/21/2016.
 */
public class CellSquare extends Cell {

    public CellSquare(int initialState, int height, int width, int row, int column) {
        super(initialState, height, width, row, column);
    }

    @Override
    protected double[] initializeCellDimensions() {
        double[] points = new double[] {0.0, 0.0,
                                        0.0, getHeight(),
                                        getWidth(), getHeight(),
                                        getWidth(), 0.0};
        return points;
    }

    @Override
    protected void setCellPosition() {
        setLayoutX(getColumn() * getWidth());
        setLayoutY(getRow() * getHeight());
    }


}
