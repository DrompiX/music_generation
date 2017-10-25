public class Particle {
    private Chord[] curPos;
    private Chord[] bestPos;
    private double[] velocity;
    private int fitness;

    Particle() {
        curPos = new Chord[Constants.C_DIMENSIONS];
        bestPos = new Chord[Constants.C_DIMENSIONS];
        velocity = new double[Constants.C_DIMENSIONS];
        fitness = -1;
    }

    // TODO: Particle initialization
    private void init() {

    }

    public int getFintess() {
        findFitness();
        return fitness;
    }

    //TODO: Write correct implementation
    private void findFitness() {

    }

    public Chord[] getBestPos() {
        return bestPos;
    }

    public Chord[] getCurPos() {
        return curPos;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setCurPos(Chord[] curPos) {
        this.curPos = curPos;
    }

    public void setBestPos(Chord[] bestPos) {
        this.bestPos = bestPos;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public void setFintess(int fintess) {
        this.fitness = fintess;
    }

}
