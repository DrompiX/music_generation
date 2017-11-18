import java.util.ArrayList;

/***
 * This class represents swarm for generating melody.
 */
public class MelodySwarm {
    private ArrayList<MParticle> swarm;
    private int[] globalPos; // Global best position
    private int gFitness;    // Global best fitness

    MelodySwarm(Tonality tonality, ArrayList<Chord> chords) {
        swarm = new ArrayList<>();
        gFitness = Integer.MIN_VALUE;

        for (int i = 0; i < Constants.M_SWARMSIZE; i++)
            swarm.add(new MParticle(tonality, chords));

        updateGlobal();
    }

    public void nextIteration() {
        for (int i = 0; i < Constants.M_SWARMSIZE; i++)
            swarm.get(i).nextIteration(globalPos);
        updateGlobal();
    }

    protected void updateGlobal() {
        int index = -1;
        for (int i = 0; i < Constants.M_SWARMSIZE; i++) {
            if (gFitness < swarm.get(i).getFitness()) {
                gFitness = swarm.get(i).getFitness();
                index = i;
            }
        }
        if (index != -1) {
            globalPos = swarm.get(index).getBestPos().clone();
        }
    }

    public int[] getGlobalPos() {
        return globalPos;
    }

    public int getgFitness() {
        return gFitness;
    }

}
