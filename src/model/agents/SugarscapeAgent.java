package model.agents;

import java.util.Random;

/**
 * 
 * An agent for the Sugarscape Simulation
 * 
 * @author Stephen
 *
 */
public class SugarscapeAgent {

    private final int INITIAL_SUGAR = 4;
    private final int MINIMUM_SUGAR = 0;
    private final int MAX_VISION = 6;
    private final int MAX_METABOLISM = 6;

    private final Random random;
    private final int metabolismRate;
    private final int vision;
    private int currentSugar;

    /**
     * Instantiates an agent for the Sugarscape Simulation
     */
    public SugarscapeAgent() {
        random = new Random();
        currentSugar = INITIAL_SUGAR;
        metabolismRate = random.nextInt(MAX_METABOLISM + 1);
        vision = random.nextInt(MAX_VISION + 1);
    }

    /**
     * @return the agent's vision
     */
    public int getVision() {
        return vision;
    }

    /**
     * 
     * @param patchSugar: the amount of sugar to be transferred to the agent
     */
    public void takeSugarFromPatch(int patchSugar) {
        currentSugar += patchSugar;
    }

    /**
     * Decreases agent's amount of sugar depending on its metabolism rate
     */
    public void metabolize() {
        currentSugar = Math.max(MINIMUM_SUGAR, currentSugar - metabolismRate);
    }

    /**
     * Indicates whether agent's amount of sugar has been completely depleted
     * @return true if agent will die; false otherwise
     */
    public boolean willDie() {
        return currentSugar == 0;
    }

}
