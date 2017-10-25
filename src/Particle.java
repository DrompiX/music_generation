public class Particle {
    private Chord[] curPos;
    private Chord[] bestPos;
    private double[] velocity;
    private int fintess;

    Particle(int dim) {
        curPos = new Chord[dim];
        bestPos = new Chord[dim];
        velocity = new double[dim];
        fintess = -1;
    }

    public int getFintess() {
        findFitness();
        return fintess;
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
        this.fintess = fintess;
    }

}
