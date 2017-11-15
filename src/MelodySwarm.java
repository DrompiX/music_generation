import java.util.ArrayList;

public class MelodySwarm {
    private ArrayList<MParticle> swarm;
    private int[] globalPos;
    private int gFitness;

    MelodySwarm(Tonality tonality) {
        swarm = new ArrayList<>();
        gFitness = Integer.MIN_VALUE;
        System.out.println("Melody: " + tonality.getTonic() + " " + tonality.getMode()); // TODO: Remove

        ArrayList<Integer> tonalityNotes = tonality.getTonalityNotes();
        for (Integer ton: tonalityNotes) { // TODO: Remove
            System.out.print(ton + " ");
        }
        System.out.println();

        for (int i = 0; i < Constants.C_SWARMSIZE; i++)
            swarm.add(new MParticle(tonality));

        updateGlobal();
    }

    public void nextIteration() {
        for (int i = 0; i < Constants.M_SWARMSIZE; i++)
            swarm.get(i).nextIteration(globalPos);
        updateGlobal();
    }

    protected void updateGlobal() {
        int index = -1;
        for (int i = 0; i < Constants.C_SWARMSIZE; i++) {
            if (gFitness < swarm.get(i).getFitness()) {
                gFitness = swarm.get(i).getFitness();
                index = i;
            }
        }
        if (index != -1) {
            System.out.println("new global best: " + gFitness);
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
