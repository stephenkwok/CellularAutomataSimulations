package model.agents;

import java.util.Random;

public class SugarscapeAgent {

    private final int INITIAL_SUGAR = 4;
    private final int MINIMUM_SUGAR = 0;
    private final int MAX_VISION = 6;
    private final int MAX_METABOLISM = 6;

    private int currentSugar;
    private int metabolismRate;
    private int vision;
    private Random random;

    public SugarscapeAgent() {
        random = new Random();
        currentSugar = INITIAL_SUGAR;
        metabolismRate = random.nextInt(MAX_METABOLISM + 1);
        vision = random.nextInt(MAX_VISION + 1);
    }

    public int getVision() {
        return vision;
    }

    public void takeSugarFromPatch(int patchSugar) {
        currentSugar += patchSugar;
    }

    public void metabolize() {
        currentSugar = Math.max(MINIMUM_SUGAR, currentSugar - metabolismRate);
    }

    public boolean willDie() {
        return currentSugar == 0;
    }

}
