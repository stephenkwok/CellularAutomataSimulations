package model.cells;
/**
 * Created by Stephen.Kwok on 7/21/2016.
 */
public enum CellStates {

	GAME_OF_LIFE_DEAD (0),
	GAME_OF_LIFE_ALIVE (1),
	SPREADING_FIRE_EMPTY (0),
	SPREADING_FIRE_TREE (1),
	SPREADING_FIRE_BURNING (2),
	SEGREGATION_X (0),
	SEGREGATION_O (1),
	SEGREGATION_EMPTY (2),
	SUGARSCAPE_MAX_SUGAR (0),
	SUGARSCAPE_HIGH (1),
	SUGARSCAPE_LOW (2),
	SUGARSCAPE_NONE (3);
	
	private final int value;
	
	private CellStates(int value) {
		this.value = value;
	}

	public int value() {return value;}

}
