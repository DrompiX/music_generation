import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Particle {
    private ArrayList<Chord> curPos;
    private ArrayList<Chord> bestPos;
    private ArrayList<Double> velocity;
    private int fitness;

    Particle() {
        curPos = new ArrayList<>();
        bestPos = new ArrayList<>();
        velocity = new ArrayList<>();
        fitness = -1;
        init();
    }

    /* TODO: Particle initialization */
    private void init() {
        for (int i = 0; i < Constants.C_DIMENSIONS; i++) {
            curPos.add(new Chord(getRandomChord()));
            System.out.println(curPos.get(0));
            bestPos.add(curPos.get(i));
            int min_v = -(Constants.UP_VAL - Constants.LOW_VAL);
            int max_v = -min_v;
            velocity.add(getRand(min_v, max_v));
            System.out.println(curPos.get(i).toString());
        }
    }

    /* TODO: Write correct implementation */
    private void findFitness() {

    }

    private int[] getRandomChord() {
        int[] chord = new int[3];
        for (int i = 0; i < 3; i++)
            chord[i] = ThreadLocalRandom.current().nextInt(Constants.LOW_VAL,
                            Constants.UP_VAL + 1);
        Arrays.sort(chord);
        return chord;
    }

    private double getRand(int low, int high) {
        return ThreadLocalRandom.current().nextDouble(low,high + 1);
    }

    public int getFintess() {
        findFitness();
        return fitness;
    }


    public ArrayList<Chord> getCurPos() {
        return curPos;
    }

    public void setCurPos(ArrayList<Chord> curPos) {
        this.curPos = curPos;
    }

    public ArrayList<Chord> getBestPos() {
        return bestPos;
    }

    public void setBestPos(ArrayList<Chord> bestPos) {
        this.bestPos = bestPos;
    }

    public ArrayList<Double> getVelocity() {
        return velocity;
    }

    public void setVelocity(ArrayList<Double> velocity) {
        this.velocity = velocity;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void setFintess(int fintess) {
        this.fitness = fintess;
    }

}
