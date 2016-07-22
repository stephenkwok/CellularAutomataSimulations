package preprocessing;

public class SimulationGenerator {

    private Document document;
    private double gridWidth;
    private double gridHeight;
    private double cellWidth,
    private double cellHeight;
    private List<String> rows;
    private int numberOfRows;
    private int numberOfColumns;
    private String simulationType;
    private String gridType;
    private String cellType;
    private Grid grid;

    public SimulationGenerator(Document document, double gridWidth, double gridHeight) {
        this.document = document;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        retrieveData();
    }

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

    public Simulation getSimulation() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        grid = getGrid();
        populateGrid();
        Method methodToCall = this.getClass().getDeclaredMethod("get" + simulationType + "Simulation");
        methodToCall.invoke(this);
    }

    private String getValue(String key) {
        return document.getElementsByTagName(key).item(0).getTextContent();
    }

    private List<String> getRows() {
        List<String> rows = new ArrayList<>();
        NodeList nodes = document.getElementsByTagName("row");
        for (int i = 0; i < nodes.getLength(); i++) {
            String row = nodes.item(i).getTextContent();
            rows.add(row);
        }
        return rows;
    }

    private Cell getCell(String cellType, int initialState, int row, int column) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> myClass = Class.forName(cellType);
        Constructor<?> constructor = myClass.getConstructor(int.class, double.class, double.class, int.class, int.class);
        Cell cell = (Cell) constructor.newInstance(initialState, cellWidth, cellHeight, row, column);
        return cell;
    }

    private Grid getGrid() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> myClass = Class.forName(gridType);
        Constructor<?> constructor = myClass.getConstructor(int.class, int.class);
        Grid grid = (Grid) constructor.newInstance(numberOfRows, numberOfColumns);
        return grid;
    }

    private void populateGrid() {
        for (row = 0; row < numberOfRows; row++) {
            for (column = 0; column < numberOfColumns, column++) {
                int cellInitialState = Integer.parseInt(rows.get(row).charAt(column));
                Cell cell = getCell(cellType, cellInitialState, row, column);
                grid.setCell(cell, row, column);
            }
        }
    }

    private GameOfLife getGameOfLifeSimulation() {
        return new GameOfLife(grid);
    }

    private SpreadingFire getSpreadingFireSimulation() {
        double probabilityCatch = Double.parseDouble(getValue("probabilityCatch"));
        return new SpreadingFire(grid, probabilityCatch)
    }

    private Segregation getSegregationSimulation() {
        double threshold = Double.parseDouble(getValue("threshold"));
        return new Segregation(grid, threshold);
    }

}
