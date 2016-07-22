package model.simulations;

import java.util.List;

import javafx.scene.paint.Color;
import model.cells.Cell;
import model.cells.CellStates;
import model.grid.Grid;

/**
 * @author Stephen
 */
public class Sugarscape extends Simulation {

    private List<List<SugarscapePatch>> patches;
    private List<SugarscapePatch> processedPatches;

    public Sugarscape(Grid grid) {
        super(grid);
        processedPatches = new ArrayList<>();
        initializePatches();
    }

    @Override
    protected void applyRulesToCell(Cell cell) {
        growSugarOnPatches();
        moveAgents();
    }

    @Override
    protected void updateCellColor(Cell cell) {
        int currentState = cell.getCurrentState();
        if (currentState == CellStates.SUGARSCAPE_NONE.value()) cell.setFill(Color.WHITE);
        if (currentState == CellStates.SUGARSCAPE_LOW.value()) cell.setFill(Color.PEACHPUFF);
        if (currentState == CellStates.SUGARSCAPE_HIGH.value()) cell.setFill(Color.ORANGE);
        if (currentState == CellStates.SUGARSCAPE_MAX.value()) cell.setFill(Color.DARKORANGE);
    }

    private void initializePatches() {
        patches = new ArrayList<List<SugarscapePatch>>();
        Grid grid = getGrid();
        for (int row = 0; row < grid.getNumberOfRows(); row++) {
            List<SugarscapePatch> rowOfPatches = new ArrayList<>();
            for (int column = 0; column < grid.getNumberOfColumns(); column++) {
                rowOfPatches.add(new SugarscapePatch());
            }
        }
    }

    private void growSugarOnPatches() {
        for (int row = 0; row < getGrid().getNumberOfRows(); row++) {
            for (int column = 0; column < getGrid().getNumberOfColumns(); column++) {
                patches.get(row).get(column).growSugar();
            }
        }
    }

    private void moveAgents() {
        for (int row = 0; row < getGrid().getNumberOfRows(); row++) {
            for (int column = 0; column < getGrid().getNumberOfColumns(); column++) {
                SugarscapePatch patch = patches.get(row).get(column);
                if (patch.hasAgent() && !processedPatches.contains(patch)) {
                    // move agent
                }
            }
        }
    }

}
