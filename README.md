# Cellular Automata Simulations

###Description

----------------------------------------------------------------------------------

Simulations of the following Cellular Automata: Conway's Game of Life, Spreading Fire, Segregation, and Sugarscape. 

Designed to support any cell shape (i.e. square, triangle, etc.) and any grid type (i.e. standard (no wrapping), toroidal (wrapping), infinite, etc.). 

Design considerations are shown below 

### Design Considerations 

-----------------------------------------------------------------------------------

####Simulation

All simulations consist of a grid of cells, and a set of rules for updating the states of those cells. Since these rules differ from one simulation to another, separate classes were created to represent each simulation. However, these simulations also share a lot of common features, so an abstract base class was created from which all simulations could extend from. 

####Grid

All grids consist of a matrix of cells, but different grids may have different types of edges. For example, a grid with toroidal edges considers the neighbor of a cell on the edge of the grid to have a neighbor on the opposite side of the grid. On the other hand, a cell on the edge of a standard grid would just have 1 (or more) fewer neighbors. 

To accommodate differences in the definition of a cell's neighbor, separate grid classes were created to support each grid edge type. However, just with the Simulation class, the grids also share common features (such as holding a matrix of cells), so an abstract Grid class was created. 

The base Grid class provides an API for retrieving a list of a given cell's neighbors. When all of the given cell's adjacent points are within the grid, the Grid class simply returns the list of cells at those adjacent points. However, when the cell's neighbor does not exist, the Grid class calls the abstract method resolveMissingNeighbor(int row, int column) which all subclasses must implement. The method returns an "alternate neighbor" cell as defined by the subclass, and this alternate neighbor can then be added to the list of all neighbors for a cell. 

Using this framework, any type of grid edge can be supported simply by implementing the resolveMissingNeighbor method in a class that extends Grid. For instance, GridStandard returns null to indicate that no alternate neighbor exists, while GridToroidal identifies a Cell on the opposite of the Grid to act as an alternate neighbor. While not currently implemented, an infinite Grid could easily be created under this design by having the resolveMissingNeighbor method add a new cell to the Grid and return that new cell as a neighbor. 

####Cell

A cell is simply a visual element in the simulation that changes color based on its current state. As with simulations and grids, a class hierarchy for cells was developed because cells share many common features (i.e. to ability to hold state) but may also differ (i.e. in terms of shape or positioning). 

To support cells of different shapes, the base Cell class requires all subclasses to implement the methods intializeCellDimensions() and setCellPosition(), because a Cell's list of points that make up the cell and the cell's position in the grid display are determined by its shape. For example, to support triangular cells, the CellTriangle class implements the initializeCellDimensions() method by checking whether the cell should be pointing up or pointing down based on its row and column in the grid and setting the cell's points according to its proper orientation. 





