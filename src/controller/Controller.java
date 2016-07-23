package controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

import org.w3c.dom.Document;

import errorHandling.ErrorHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import model.grid.Grid;
import model.simulations.Simulation;
import preprocessing.SimulationGenerator;
import preprocessing.XMLParser;
import view.GUI;
import view.Toolbar;

public class Controller implements Observer {

	private final String ERROR_MESSAGE_INVALID_XML_DATA = "Invalid Grid Type, Cell Type, or Simulation Entered";
	private final String ERROR_MESSAGE_INVALID_CONTROLLER_METHOD = "Controller Method Does Not Exist";	
	private final double MAX_ANIMATION_SPEED = 3.0;
	private final double MIN_ANIMATION_SPEED = 0.1;
	private final double ANIMATION_SPEED_INTERVAL = 0.1;
	private final double DELAY = 1000;
	private final double GRID_WIDTH = 500;
	private final double GRID_HEIGHT = 500;
	private Stage stage;
	private GUI view;
	private Toolbar toolbar;
	private Timeline timeline;
	private XMLParser xmlParser;
	private Simulation simulation;

	private boolean simulationInProgress;
	private double animnationSpeed;

	public Controller(Stage stage) {
		this.stage = stage;
		simulationInProgress = false;
		animnationSpeed = 1.0;
		xmlParser = new XMLParser();
		toolbar = new Toolbar(stage.widthProperty(), stage.heightProperty());
		view = new GUI(stage.getWidth(), stage.getHeight(), toolbar);
		stage.setScene(view.getScene());
		toolbar.addObserver(this);
	}

	private void startNewSimulation(Grid grid) {
		view.setGridDisplay(grid);
		initializeTimeline();
		timeline.play();
		simulationInProgress = true;
	}

	private void initializeTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame animationLoop = new KeyFrame(Duration.millis(DELAY), e -> simulation.updateGrid());
		timeline.getKeyFrames().add(animationLoop);
	}

	private void speedUpAnimation() {
		if (animnationSpeed <= MAX_ANIMATION_SPEED && simulationInProgress) {
			animnationSpeed += ANIMATION_SPEED_INTERVAL;
			timeline.setRate(animnationSpeed);
		}
	}

	private void slowDownAnimation() {
		if (animnationSpeed >= MIN_ANIMATION_SPEED && simulationInProgress) {
			animnationSpeed -= ANIMATION_SPEED_INTERVAL;
			timeline.setRate(animnationSpeed);
		}
	}

	private void pauseOrResumeSimulation() {
		if (simulationInProgress)
			timeline.pause();
		else
			timeline.play();
		simulationInProgress = !simulationInProgress;
	}

	private void stepAnimation() {
		timeline.pause();
		simulationInProgress = false;
		simulation.updateGrid();
	}

	private void stopAnimation() {
		timeline.stop();
		simulationInProgress = false;
	}

	private void selectXMLFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		initializeNewSimulation(selectedFile);
	}

	private void initializeNewSimulation(File file) {
		Document document = xmlParser.parseXMLFile(file);
		try {
			SimulationGenerator generator = new SimulationGenerator(document, GRID_WIDTH, GRID_HEIGHT);
			simulation = generator.getSimulation();
			Grid grid = simulation.getGrid();
			startNewSimulation(grid);
		} catch (Exception e) {
			ErrorHandler.handleError(ERROR_MESSAGE_INVALID_XML_DATA);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		String command = (String) arg;
		Method methodToCall = null;
		try {
			methodToCall = this.getClass().getDeclaredMethod(command);
			methodToCall.invoke(this);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			ErrorHandler.handleError(ERROR_MESSAGE_INVALID_CONTROLLER_METHOD);
		}
	}

}
