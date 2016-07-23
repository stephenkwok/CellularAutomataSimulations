package model.agents;

import java.util.Random;

public class SugarscapePatch {

    private final int MAX_SUGAR = 3;
    private final int SUGAR_GROW_BACK_RATE = 1;
    private final int SUGAR_GROW_RATE_INTERVAL = 1;
    private final double PROBABILITY_START_WITH_AGENT = 0.5;
    private int sugarGrowBackCounter;
    private int currentSugar;
    private SugarscapeAgent agent;
    private Random random;

    public SugarscapePatch() {
        random = new Random();
        sugarGrowBackCounter = 0;
        currentSugar = random.nextInt(MAX_SUGAR + 1);
        initializeAgent();
    }

    private void initializeAgent() {
        if (Math.random() < PROBABILITY_START_WITH_AGENT) {
            agent = new SugarscapeAgent();
        }
    }

    public void growSugar() {
        if (++sugarGrowBackCounter == SUGAR_GROW_RATE_INTERVAL) {
            currentSugar = Math.min(currentSugar + SUGAR_GROW_BACK_RATE, MAX_SUGAR);
            sugarGrowBackCounter = 0;
        }
    }

    public boolean hasAgent() {
        return agent != null;
    }

    public void removeAgent() {agent = null;}

    public SugarscapeAgent getAgent() {return agent;}

    public int getSugar() {
        return currentSugar;
    }

    public void depleteSugar() {
        currentSugar = 0;
    }

}
