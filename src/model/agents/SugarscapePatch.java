package model.agents;

import java.util.Random;

/**
 * 
 * Patch of sugar for Sugarscape Simulation
 * 
 * @author Stephen
 *
 */
public class SugarscapePatch {

    private final int MAX_SUGAR = 3;
    private final int SUGAR_GROW_BACK_RATE = 1;
    private final int SUGAR_GROW_RATE_INTERVAL = 1;
    private final double PROBABILITY_START_WITH_AGENT = 0.5;
    private int sugarGrowBackCounter;
    private int currentSugar;
    private SugarscapeAgent agent;
    private Random random;

    /**
     * Instanties a patch of sugar for the Sugarscape Simulation
     */
    public SugarscapePatch() {
        random = new Random();
        sugarGrowBackCounter = 0;
        currentSugar = random.nextInt(MAX_SUGAR + 1);
        initializeAgent();
    }

    /**
     * Places agent on patch of sugar PROBABILITY_START_WITH_AGENT percent of the time
     */
    private void initializeAgent() {
        if (Math.random() < PROBABILITY_START_WITH_AGENT) {
            agent = new SugarscapeAgent();
        }
    }

    /**
     * Grows sugar on the patch
     */
    public void growSugar() {
        if (++sugarGrowBackCounter == SUGAR_GROW_RATE_INTERVAL) {
            currentSugar = Math.min(currentSugar + SUGAR_GROW_BACK_RATE, MAX_SUGAR);
            sugarGrowBackCounter = 0;
        }
    }

    /**
     * @return true if there is an agent on the patch; false otherwise
     */
    public boolean hasAgent() {
        return agent != null;
    }

    /**
     * Removes agent from the patch
     */
    public void removeAgent() {agent = null;}

    /**
     * @return the agent on the patch
     */
    public SugarscapeAgent getAgent() {return agent;}
    
    /**
     * @param agent: agent to be placed on the patch
     */
    public void setAgent(SugarscapeAgent agent) {this.agent = agent;}

    /**
     * @return the amount of sugar on the current patch
     */
    public int getSugar() {
        return currentSugar;
    }

    /**
     * Sets the amount of sugar on the patch to 0
     */
    public void depleteSugar() {
        currentSugar = 0;
    }

}
