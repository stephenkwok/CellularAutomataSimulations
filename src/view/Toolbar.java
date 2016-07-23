package view;

import java.util.Observable;

import javafx.beans.binding.DoubleExpression;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Toolbar allowing user to select a simulation to animate or control a selected simulation (Pause, resume, stop, speed up, slow down);
 * 
 * @author Stephen
 *
 */
public class Toolbar extends Observable {

	private static final double TOOLBAR_PERCENT_OF_SCREEN_HEIGHT = .10;
	private static final double BUTTON_PERCENT_OF_TOOLBAR_WIDTH = .70;
	private static final double CONTAINER_CHILD_PADDING = 20;
	private HBox container;
	
	/**
	 * Instantiates a Toolbar
	 * @param parentWidth: width of the Toolbar's parent
	 * @param parentHeight: height of the Toolbar's parent
	 */
	public Toolbar(DoubleExpression parentWidth, DoubleExpression parentHeight) {
		container = new HBox(CONTAINER_CHILD_PADDING);
		container.prefWidthProperty().bind(parentWidth);
		container.prefHeightProperty().bind(parentHeight.multiply(TOOLBAR_PERCENT_OF_SCREEN_HEIGHT));
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(25.0));
		initializeButtons();
	}

	/**
	 * Sets up the Toolbar's buttons
	 */
    private void initializeButtons() {
        addButtonToToolbar("Select Simulation", "selectXMLFile");
        addPauseButtonToToolbar();
        addButtonToToolbar("Stop Animation", "stopAnimation");
        addButtonToToolbar("Speed Up", "speedUpAnimation");
        addButtonToToolbar("Slow Down", "slowDownAnimation");
        addButtonToToolbar("Step Animation", "stepAnimation");
    }

    /**
     * Adds a button the Toolbar 
     * 
     * @param buttonText: text to be displayed on button
     * @param linkedCommand: Controller command to be invokved when button is clicked
     */
    private void addButtonToToolbar(String buttonText, String linkedCommand) {
        Button button = new Button(buttonText);
        button.setOnAction(e -> notifyController(linkedCommand));
        bindButtonWidthToContainerWidth(button);
        container.getChildren().add(button);
    }

    /**
     * Adds pause button to Toolbar
     */
    private void addPauseButtonToToolbar() {
        Button button = new Button("Pause");
        button.setOnAction(e -> pauseOrResumeSimulation(button));
        bindButtonWidthToContainerWidth(button);
        container.getChildren().add(button);
    }

    /**
     * Notifies Controller to pause the animation if it is running, or resume
     * the animation if it has been paused; Toggles button's text as needed
     * 
     * @param button: the pause/resume button clicked
     */
    private void pauseOrResumeSimulation(Button button) {
        togglePauseResumeButtonText(button);
        notifyController("pauseOrResumeSimulation");
    }

    /**
     * Toggles text on Pause/Resume button
     * @param button: Pause/Resume button
     */
    private void togglePauseResumeButtonText(Button button) {
        String currentText = button.getText();
        if (currentText.equals("Pause")) button.setText("Resume");
        else button.setText("Pause");
    }

    /**
     * Notifies Controller of what action needs to be taken in response to button click
     * @param command: Controller command to be invoked
     */
    private void notifyController(String command) {
        setChanged();
        notifyObservers(command);
    }
    
    /**
     * Binds width of a Button to the width of its container
     * @param button: Button whose width is to be bound
     */
    private void bindButtonWidthToContainerWidth(Button button) {
    	button.prefWidthProperty().bind(container.widthProperty().multiply(BUTTON_PERCENT_OF_TOOLBAR_WIDTH));
    }

    /**
     * 
     * @return container holding Toolbar
     */
    public HBox getContainer() {return container;}

}
