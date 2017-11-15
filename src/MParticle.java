import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MParticle {
    private int[] curPos;
    private int[] bestPos;
    private double[] velocity;
    private ArrayList<Integer> tonalityNotes;
    private int fitness;
    private int mode; /* 0 = Major; 1 = Minor*/

    MParticle(Tonality tonality) {
        this.curPos = new int[Constants.M_DIMENSIONS];
        this.bestPos = new int[Constants.M_DIMENSIONS];
        this.velocity = new double[Constants.M_DIMENSIONS];
        this.tonalityNotes = tonality.getTonalityNotes();
        this.mode = tonality.getMode();

        init();
        fitness = findFitness();
    }

    /* TODO: TEST Particle initialization */
    private void init() {
        int min_v = -(Constants.UP_VAL - Constants.LOW_VAL);
        int max_v = -min_v;
        for (int i = 0; i < Constants.C_DIMENSIONS; i++) {
            curPos[i] = getRandInt(Constants.LOW_VAL, Constants.UP_VAL);
            velocity[i] = getRandDouble(min_v, max_v);
        }
        updateBest();
    }

    public void nextIteration(int[] globalPos) {
        for (int i = 0; i < velocity.length; i++) {
            velocity[i] = Constants.C_W * velocity[i] +
                    Constants.C_C1 * getRandDouble(0, 1) * (bestPos[i] - curPos[i]) *
                            Constants.C_C2 * getRandDouble(0, 1) * (globalPos[i]- curPos[i]);
        }
        updateNotes();

        int freshFitness = findFitness();
        if (freshFitness > fitness) {
            fitness = freshFitness;
            updateBest();
        }
    }

    private void updateBest() {
        bestPos = curPos.clone();
    }

    private void updateNotes() {
        for (int i = 0; i < velocity.length; i++) {

        }
    }

    /* TODO: Write correct implementation */
    private int findFitness() {
        return 0;
    }

    private int getRandInt(int low, int high) {
        return ThreadLocalRandom.current().nextInt(low, high + 1);
    }

    private double getRandDouble(int low, int high) {
        return ThreadLocalRandom.current().nextDouble(low,high + 1);
    }

    public int[] getCurPos() {
        return curPos;
    }

    public int[] getBestPos() {
        return bestPos;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public int getFitness() {
        return fitness;
    }

}
