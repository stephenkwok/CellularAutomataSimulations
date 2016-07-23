package model.simulations;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import model.agents.SugarscapeAgent;
import model.agents.SugarscapePatch;
import model.cells.Cell;
import model.cells.CellStates;
import model.grid.Grid;

/**
 * Simulation of Sugarscape
 * 
 * @author Stephen
 * 
 */
public class Sugarscape extends Simulation {

    private List<List<SugarscapePatch>> patches;
    private List<SugarscapePatch> processedPatches;

    /**
     * Instantiates Simulation of Sugarscape
     * @param grid: Simulation's Grid of Cells
     */
    public Sugarscape(Grid grid) {
        super(grid);
        processedPatches = new ArrayList<>();
        initializePatches();
    }

    /**
     * Updates grid by growing sugar on each patch, moving agents as necessary,
     * and updating Cell states and colors
     */
    @Override
    public void updateGrid() {
        growSugarOnPatches();
        moveAgents();
        super.updateGrid();
        processedPatches.clear();
    }
    
    /**
     * Updates given Cell's state according to the amount of sugar in its 
     * corresponding patch
     */
    @Override
    protected void applyRulesToCell(Cell cell) {
    	cell.setState(patches.get(cell.getRow()).get(cell.getColumn()).getSugar());
    }

    /**
     * Updates Cell's color according to its current state
     * Color changes to white if the cell's patch contains no sugar, peachpuff if the patch
     * contains a low amount of sugar, orange if the patch contains a high amount of sugar,
     * and darkorange if the patch contains the maximum amount of sugar
     */
    @Override
    protected void updateCellColor(Cell cell) {
        int currentState = cell.getCurrentState();
        if (currentState == CellStates.SUGARSCAPE_NONE.value()) cell.setFill(Color.WHITE);
        if (currentState == CellStates.SUGARSCAPE_LOW.value()) cell.setFill(Color.PEACHPUFF);
        if (currentState == CellStates.SUGARSCAPE_HIGH.value()) cell.setFill(Color.ORANGE);
        if (currentState == CellStates.SUGARSCAPE_MAX.value()) cell.setFill(Color.DARKORANGE);
    }

    /**
     * Initializes the patches for each cell
     */
    private void initializePatches() {
        patches = new ArrayList<List<SugarscapePatch>>();
        Grid grid = getGrid();
        for (int row = 0; row < grid.getNumberOfRows(); row++) {
            List<SugarscapePatch> rowOfPatches = new ArrayList<>();
            for (int column = 0; column < grid.getNumberOfColumns(); column++) {
                rowOfPatches.add(new SugarscapePatch());
            }
            patches.add(rowOfPatches);
        }
    }

    /**
     * Grows sugar on each patch
     */
    private void growSugarOnPatches() {
        for (int row = 0; row < getGrid().getNumberOfRows(); row++) {
            for (int column = 0; column < getGrid().getNumberOfColumns(); column++) {
                patches.get(row).get(column).growSugar();
            }
        }
    }

    /**
     * Moves agents 
     */
    private void moveAgents() {
        for (int row = 0; row < getGrid().getNumberOfRows(); row++) {
            for (int column = 0; column < getGrid().getNumberOfColumns(); column++) {
                SugarscapePatch patch = patches.get(row).get(column);
                if (patch.hasAgent() && !processedPatches.contains(patch)) {
                    moveAgent(row, column);
                }
            }
        }
    }
    
    /**
     * Moves agent at the given row and column to the neighboring sugar patch
     * with the maximum amount of sugar
     * @param row: row of the agent
     * @param column: column of the agent
     */
    private void moveAgent(int row, int column) {
    	SugarscapePatch currentPatch = patches.get(row).get(column);
    	SugarscapePatch maxSugarPatch = findMaxSugarPatch(row, column);
    	if (maxSugarPatch == null) return;
    	SugarscapeAgent agent = currentPatch.getAgent();
    	maxSugarPatch.setAgent(agent);
    	handleSugarTransfer(agent, maxSugarPatch);
    	if (agent.willDie()) maxSugarPatch.removeAgent();
    	currentPatch.removeAgent();
    	processedPatches.add(maxSugarPatch);
    }
    
    /**
     * Transfers sugar from a sugar patch to an agent
     * @param agent: agent receiving the sugar
     * @param patch: patch to take sugar from
     */
    private void handleSugarTransfer(SugarscapeAgent agent, SugarscapePatch patch) {
    	agent.takeSugarFromPatch(patch.getSugar());
    	agent.metabolize();
    	patch.depleteSugar();
    }
    
    /**
     * Finds the sugar patch near the given row and column with the maximum amount of sugar
     * Sugar patches searched depends on agent's vision
     * @param row: agent's row
     * @param column: agent's column
     * @return sugar patch near given row and column with max amount of sugar
     */
    private SugarscapePatch findMaxSugarPatch(int row, int column) {
    	int vision = patches.get(row).get(column).getAgent().getVision();
    	SugarscapePatch maxSugarPatch = null;
    	for (int multiplier = 1; multiplier <= vision; multiplier++) {
    		List<Cell> neighbors = getGrid().getExtendedCardinalNeighobors(row, column, multiplier);
    		List<SugarscapePatch> neighborPatches = getNeighborPatches(neighbors);
    		maxSugarPatch = updateMaxSugarPatch(maxSugarPatch, neighborPatches);
    	}
    	return maxSugarPatch;
    }
    
    /**
     * Updates max sugar patch if neighboring sugar patch has more sugar than current max sugar patch
     * 
     * @param maxSugarPatch: patch found with most sugar so far
     * @param contenders: patches that may have more sugar than current max sugar patch
     * @return the sugar patch with the maximum amount of sugar
     */
    private SugarscapePatch updateMaxSugarPatch(SugarscapePatch maxSugarPatch, List<SugarscapePatch> contenders) {
    	if (contenders.isEmpty()) return maxSugarPatch;
    	if (maxSugarPatch == null) maxSugarPatch = contenders.get(0);
    	for (SugarscapePatch patch : contenders) {
    		if (!patch.hasAgent())
    			maxSugarPatch = patch.getSugar() > maxSugarPatch.getSugar() ? patch : maxSugarPatch;
    	}
    	return maxSugarPatch;
    }
    
    /**
     * Given list of neighbor Cells, return List of those Cells' patches
     * @param neighbors: List of neighbor Cells
     * @return List of patches associated with those neighbor Cells
     */
    private List<SugarscapePatch> getNeighborPatches(List<Cell> neighbors) {
    	List<SugarscapePatch> neighborPatches = new ArrayList<>();
    	for (Cell neighbor : neighbors) {
    		neighborPatches.add(patches.get(neighbor.getRow()).get(neighbor.getColumn()));
    	}
    	return neighborPatches;
    }

}
