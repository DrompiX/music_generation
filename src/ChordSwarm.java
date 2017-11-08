import java.util.ArrayList;

public class ChordSwarm {
    private ArrayList<Particle> swarm;
    private Chord[] globalPos;
    private int gFitness;

    ChordSwarm() {
        for (int i = 0; i < Constants.C_SWARMSIZE; i++) {
            swarm.add(new Particle());
        }
    }

    public ArrayList<Particle> getSwarm() {
        return swarm;
    }

    public void setSwarm(ArrayList<Particle> swarm) {
        this.swarm = swarm;
    }

    public Chord[] getGlobalPos() {
        return globalPos;
    }

    public void setGlobalPos(Chord[] globalPos) {
        this.globalPos = globalPos;
    }

    public int getgFitness() {
        return gFitness;
    }

    public void setgFitness(int gFitness) {
        this.gFitness = gFitness;
    }
}
