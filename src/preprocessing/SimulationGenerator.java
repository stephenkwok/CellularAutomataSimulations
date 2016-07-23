package preprocessing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import model.cells.Cell;
import model.grid.Grid;
import model.simulations.GameOfLife;
import model.simulations.Segregation;
import model.simulations.Simulation;
import model.simulations.SpreadingFire;
import model.simulations.Sugarscape;

/**
 * 
 * Generates Simulation with parameters given in selected XML file
 * 
 * @author Stephen
 *
 */
public class SimulationGenerator {

	private Document document;
	private double gridWidth;
	private double gridHeight;
	private double cellWidth;
	private double cellHeight;
	private List<String> rows;
	private int numberOfRows;
	private int numberOfColumns;
	private String simulationType;
	private String gridType;
	private String cellType;
	private Grid grid;

	/**
	 * Instantiates a Simulation Generator
	 * @param document: document containing data pertaining to the Simu;ation, Grid, and Cells
	 * @param gridWidth: width of the Grid
	 * @param gridHeight: height of the Grid
	 */
	public SimulationGenerator(Document document, double gridWidth, double gridHeight) {
		this.document = document;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		retrieveData();
	}

	/**
	 * Retrieves data necessary to generate Grid and Simulation
	 */
	private void retrieveData() {
		rows = getRows();
		numberOfRows = Integer.parseInt(getValue("numberOfRows"));
		numberOfColumns = Integer.parseInt(getValue("numberOfColumns"));
		simulationType = getValue("simulationType");
		gridType = getValue("gridType");
		cellType = getValue("cellType");
		cellWidth = gridWidth / numberOfRows;
		cellHeight = gridHeight / numberOfColumns;
	}

	/**
	 * 
	 * @return Simulation of the type specified in XML file
	 * 
	 */
	public Simulation getSimulation() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
		grid = getGrid();
		populateGrid();
		Method methodToCall = this.getClass().getDeclaredMethod("get" + simulationType + "Simulation");
		return (Simulation) methodToCall.invoke(this);
	}

	/**
	 * 
	 * @param key: XML tag containing data to be read
	 * @return data from XML node as String
	 */
	private String getValue(String key) {
		return document.getElementsByTagName(key).item(0).getTextContent();
	}

	/**
	 * 
	 * @return a List of Strings representing the rows of the Grid, where each character in the 
	 * Strings represents the initial state of a cell
	 */
	private List<String> getRows() {
		List<String> rows = new ArrayList<>();
		NodeList nodes = document.getElementsByTagName("row");
		for (int i = 0; i < nodes.getLength(); i++) {
			String row = nodes.item(i).getTextContent();
			rows.add(row);
		}
		return rows;
	}

	/**
	 * 
	 * @param cellType: the cell type i.e. square, triangle, etc.
	 * @param initialState: the initial state of the cell
	 * @param row: the cell's row in the Grid
	 * @param column: the cell's column in the Grid
	 * @return a Cell as specified by the XML file
	 */
	private Cell getCell(String cellType, int initialState, int row, int column)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName("model.cells." + cellType);
		Constructor<?> constructor = myClass.getConstructor(int.class, double.class, double.class, int.class,
				int.class);
		Cell cell = (Cell) constructor.newInstance(initialState, cellHeight, cellWidth, row, column);
		return cell;
	}

	/**
	 * 
	 * @return a Grid of the type specified in the XML file
	 */
	private Grid getGrid() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName("model.grid." + gridType);
		Constructor<?> constructor = myClass.getConstructor(int.class, int.class);
		Grid grid = (Grid) constructor.newInstance(numberOfRows, numberOfColumns);
		return grid;
	}

	/**
	 * Fills Grid with Cells
	 */
	private void populateGrid() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				int cellInitialState = Integer.parseInt(rows.get(row).charAt(column) + "");
				Cell cell = getCell(cellType, cellInitialState, row, column);
				grid.setCell(row, column, cell);
			}
		}
	}

	/**
	 * @return a Simulation of Game of Life
	 */
	private GameOfLife getGameOfLifeSimulation() {
		return new GameOfLife(grid);
	}

	/**
	 * @return a Simulation of Spreading Fire
	 */
	private SpreadingFire getSpreadingFireSimulation() {
		double probabilityCatch = Double.parseDouble(getValue("probabilityCatch"));
		return new SpreadingFire(grid, probabilityCatch);
	}

	/**
	 * @return a Simulation of Segregation
	 */
	private Segregation getSegregationSimulation() {
		double threshold = Double.parseDouble(getValue("threshold"));
		return new Segregation(grid, threshold);
	}
	
	/**
	 * @return a Simulation of Sugarscape
	 */
	private Sugarscape getSugarscapeSimulation() {
		return new Sugarscape(grid);
	}

}
