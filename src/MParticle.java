import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MParticle {
    private int[] curPos;
    private int[] bestPos;
    private double[] velocity;
    private Tonality tonality;
    private ArrayList<Chord> chords;
    private int fitness;

    MParticle(Tonality tonality, ArrayList<Chord> chords) {
        this.curPos = new int[Constants.M_DIMENSIONS];
        this.bestPos = new int[Constants.M_DIMENSIONS];
        this.velocity = new double[Constants.M_DIMENSIONS];
        this.tonality = tonality;
        this.chords = chords;

        init();
        fitness = findFitness();
    }

    private void init() {
        int min_v = -(Constants.UP_VAL - Constants.LOW_VAL);
        int max_v = -min_v;
        for (int i = 0; i < Constants.M_DIMENSIONS; i++) {
            curPos[i] = getRandInt(Constants.LOW_VAL, Constants.UP_VAL);
            velocity[i] = getRandDouble(min_v, max_v);
        }
        updateBest();
    }

    public void nextIteration(int[] globalPos) {
        for (int i = 0; i < velocity.length; i++) {
            velocity[i] = Constants.M_W * velocity[i] +
                    Constants.M_C1 * getRandDouble(0, 1) * (bestPos[i] - curPos[i]) +
                            Constants.M_C2 * getRandDouble(0, 1) * (globalPos[i] - curPos[i]);
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

    /***
     * This method adds velocity to the current position of the particle.
     */
    private void updateNotes() {
        for (int i = 0; i < velocity.length; i++) {
            curPos[i] += ((int) velocity[i]) % (Constants.UP_VAL + 1) - Constants.LOW_VAL;
            if (curPos[i] < Constants.LOW_VAL || curPos[i] > Constants.UP_VAL)
                curPos[i] = curPos[i] % (Constants.LOW_VAL + 1) + Constants.LOW_VAL;
        }
    }

    private int findFitness() {
        int fitness = 0;
        int chordTopNote = 0;
        for (int i = 0; i < curPos.length; i++) {
            if (i % 2 == 0) {
                fitness += chords.get(i / 2).isSuitable(curPos[i]) ? 1000 : -1000;
                chordTopNote = chords.get(i / 2).notes[2];
            } else {
                fitness += tonality.contains(curPos[i]) && curPos[i] > chordTopNote ? 1000 : -1000;
            }
            if (i > 0 && Math.abs(curPos[i - 1] - curPos[i]) <= 12) fitness += 500;
            else if (i != 0) fitness -= 1000;
        }

        boolean noRepetitions = true;
        for (int i = 0; i < curPos.length - 3; i++) {
            if (curPos[i] == curPos[i + 1] && curPos[i + 1] == curPos[i + 2]) {
                noRepetitions = false;
                break;
            }
        }

        if (!noRepetitions)
            if (fitness > 10000) fitness = 10000;
            else fitness = 5000;

        return fitness;
    }

    private int getRandInt(int low, int high) {
        return ThreadLocalRandom.current().nextInt(low, high + 1);
    }

    private double getRandDouble(int low, int high) {
        return ThreadLocalRandom.current().nextDouble(low,high + 1);
    }

    public int[] getBestPos() {
        return bestPos;
    }

    public int getFitness() {
        return fitness;
    }

}
