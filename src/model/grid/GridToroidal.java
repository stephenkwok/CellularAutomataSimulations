package model.grid;
import model.cells.Cell;

/**
 * Created by Stephen.Kwok on 7/21/2016.
 */
public class GridToroidal extends Grid {

    public GridToroidal(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    protected Cell resolveMissingNeighbor(int row, int column) {
        int neighborRow = getNeighborPosition(row, getNumberOfRows());
        int neighborColumn = getNeighborPosition(column, getNumberOfColumns());
        return getCell(neighborRow, neighborColumn);
    }

    private int getNeighborPosition(int position, int totalPositions) {
        if (position >= totalPositions) return position % totalPositions;
        else if (position < 0) return position + totalPositions;
        else return position;
    }
}
