package view;

import java.util.Observable;

import javafx.beans.binding.DoubleExpression;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Toolbar extends Observable {

	private static final double TOOLBAR_PERCENT_OF_SCREEN_HEIGHT = .10;
	private static final double BUTTON_PERCENT_OF_TOOLBAR_WIDTH = .70;
	private static final double CONTAINER_CHILD_PADDING = 20;
	private HBox container;
	
	public Toolbar(DoubleExpression parentWidth, DoubleExpression parentHeight) {
		container = new HBox(CONTAINER_CHILD_PADDING);
		container.prefWidthProperty().bind(parentWidth);
		container.prefHeightProperty().bind(parentHeight.multiply(TOOLBAR_PERCENT_OF_SCREEN_HEIGHT));
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(25.0));
		initializeButtons();

	}

    private void initializeButtons() {
        addButtonToToolbar("Select Simulation", "selectXMLFile");
        addPauseButtonToToolbar();
        addButtonToToolbar("Stop Animation", "stopAnimation");
        addButtonToToolbar("Speed Up", "speedUpAnimation");
        addButtonToToolbar("Slow Down", "slowDownAnimation");
        addButtonToToolbar("Step Animation", "stepAnimation");
    }

    private void addButtonToToolbar(String buttonText, String linkedCommand) {
        Button button = new Button(buttonText);
        button.setOnAction(e -> notifyController(linkedCommand));
        bindButtonWidthToContainerWidth(button);
        container.getChildren().add(button);
    }

    private void addPauseButtonToToolbar() {
        Button button = new Button("Pause");
        button.setOnAction(e -> pauseOrResumeSimulation(button));
        bindButtonWidthToContainerWidth(button);
        container.getChildren().add(button);
    }

    private void pauseOrResumeSimulation(Button button) {
        togglePauseResumeButtonText(button);
        notifyController("pauseOrResumeSimulation");
    }

    private void togglePauseResumeButtonText(Button button) {
        String currentText = button.getText();
        if (currentText.equals("Pause")) button.setText("Resume");
        else button.setText("Pause");
    }

    private void notifyController(String command) {
        setChanged();
        notifyObservers(command);
    }
    
    private void bindButtonWidthToContainerWidth(Button button) {
    	button.prefWidthProperty().bind(container.widthProperty().multiply(BUTTON_PERCENT_OF_TOOLBAR_WIDTH));
    }

    public HBox getContainer() {return container;}

}
