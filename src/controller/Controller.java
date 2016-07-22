package controller;

public class Controller implements Observer {

    private final double MAX_ANIMATION_SPEED = 3.0;
    private final double MIN_ANIMATION_SPEED = 0.1;
    private final double ANIMATION_SPEED_INTERVAL = 0.1;
    private final double DELAY = 1000;
    private final double GRID_WIDTH = 500;
    private final double GRID_HEIGHT = 500;
    private GUI view;
    private Toolbar toolbar;
    private Timeline timeline;
    private XMLParser xmlParser;
    private Simulation simulation;

    private boolean simulationInProgress;
    private double animnationSpeed;


    public Controller(Stage stage){
        simulationInProgress = false;
        animnationSpeed = 1.0;
        xmlParser = new XMLParser();
        toolbar = new Toolbar(stage.getWidth(), stage.getHeight());
        GUI view = new GUI(stage.getWdith(), stage.getHeight(), toolbar);
        stage.setScene(view.getScene());
        toolbar.addObserver(this);
    }

    private void startNewSimulation() {
        File file = toolbar.getSelectedFile();
        Document document = xmlParser.parseXMLFile(file);
        SimulationGenerator generator = new SimulationGenerator(document, GRID_WIDTH, GRID_HEIGHT);
        simulation = generator.getSimulation();
        Grid grid = simulation.getGrid();
        view.setGridDisplay(grid);

        initializeTimeline();
        timeline.play();
        simulationInProgress = true;
    }

    private void initializeTimeline() {
        timeline = new TimeLine();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame animationLoop =
                new KeyFrame(Duration.millis(DELAY), e -> simulation.updateGrid());
        myTimeline.getKeyFrames().add(animationLoop);
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
        if (simulationInProgress) timeline.pause();
        else timeline.play();
        simulationInProgress = !simulationInProgress;
    }

    private void stepAnimation() {
        pauseSimulation();
        simulationInProgress = false;
        simulation.updateGrid();
    }

    private void stopAnimation() {
        timeline.stop();
        simulationInProgress = false;
    }

    @Override
    public void update(Observable o, Object arg) {
        String command = (String) arg;
        Method methodToCall = this.getClass().getDeclaredMethod(command);
        methodToCall.invoke(this);
    }



}
