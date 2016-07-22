package view;

import javafx.beans.binding.DoubleExpression;
import javafx.scene.layout.HBox;

public class Toolbar extends Observable {

	private static final double PERCENT_OF_SCREEN_WIDTH = .30;
	private VBox container;
    private File selectedFile;
	
	public Toolbar(DoubleExpression parentWidth, DoubleExpression parentHeight) {
		container = new VBox();
		container.prefWidthProperty().bind(parentWidth.multiply(PERCENT_OF_SCREEN_WIDTH));
		container.prefHeightProperty().bind(parentHeight);
	}

    private void initializeButtons() {
        addButtonToToolbar("Speed Up", "speedUpAnimation");
        addButtonToToolbar("Slow Down", "slowDownAnimation");
        addButtonToToolbar("Step Animation", "stepAnimation");
        addButtonToToolbar("Stop Animation", "stopAnimation");
        addPauseButtonToToolbar();
    }

    private void addButtonToToolbar(String buttonText, String linkedCommand) {
        Button button = new Button(buttonText);
        button.setOnAction(e -> notifyController(linkedCommand));
        container.getChildren().add(button);
    }

    private void addPauseButtonToToolbar() {
        Button button = new Button("Pause");
        button.setOnAction(e -> pauseOrResumeSimulation(button));
        container.getChildren().add(button);
    }

    private void addSelectXMLFileButtonToToolbar() {
        Button button = new Button("Select XML File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("XML Files", "*.xml"));
        selectedFile = fileChooser.showOpenDialog(myStage);
        notifyController("startNewSimulation");
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

    private void notifyController(String commmand) {
        setChanged();
        notifyObservers(command);
    }

    private VBox getContainer() {return container;}

    private File getSelectedFile() {return selectedFile;}



}
