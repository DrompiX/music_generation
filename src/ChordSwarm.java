import java.util.ArrayList;

public class ChordSwarm {
    private ArrayList<Particle> swarm;
    private ArrayList<Chord> globalPos;
    private int gFitness;

    ChordSwarm(Tonality tonality) {
        swarm = new ArrayList<>();
        gFitness = Integer.MIN_VALUE;
        System.out.println("Chords: " + tonality.getTonic() + " " + tonality.getMode()); // TODO: Remove

        ArrayList<Integer> tonalityNotes = tonality.getTonalityNotes();
        for (Integer ton: tonalityNotes) { // TODO: Remove
            System.out.print(ton + " ");
        }
        System.out.println();

        for (int i = 0; i < Constants.C_SWARMSIZE; i++)
            swarm.add(new Particle(tonality));

        updateGlobal();
    }

    public void nextIteration() {
        for (int i = 0; i < Constants.C_SWARMSIZE; i++)
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
            globalPos = new ArrayList<>();
            ArrayList<Chord> best_pos = swarm.get(index).getBestPos();
            for (Chord best_p : best_pos) {
                globalPos.add(best_p.cloneIt());
                System.out.print(best_p);
            }
            System.out.println();
        }
    }

    public ArrayList<Chord> getGlobalPos() {
        return globalPos;
    }

    public int getgFitness() {
        return gFitness;
    }

    public ArrayList<Particle> getSwarm() {
        return swarm;
    }

}
