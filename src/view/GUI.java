package view;

import javafx.beans.binding.DoubleExpression;
import javafx.scene.layout.HBox;

public class Toolbar {

	private static final double PERCENT_OF_SCREEN_WIDTH = .30;
	private HBox container;
	
	public Toolbar(DoubleExpression parentWidth, DoubleExpression parentHeight) {
		container = new HBox();
		container.prefWidthProperty().bind(parentWidth.multiply(PERCENT_OF_SCREEN_WIDTH));
		container.prefHeightProperty().bind(parentHeight);
	}
	
}
