import javafx.util.Pair;
import java.util.ArrayList;

public class ChordSwarm extends Swarm {
    private ArrayList<Particle> swarm;
    private ArrayList<Chord> globalPos;
    private int gFitness;
    private Pair<Integer, Integer> tonality;

    ChordSwarm() {
        swarm = new ArrayList<>();
        gFitness = Integer.MIN_VALUE;
        tonality = generateTonality();
        int mode = tonality.getValue();
        System.out.println(tonality); // TODO: Remove

        ArrayList<Integer> tonalityNotes = getTonalityNotes(tonality.getKey(), tonality.getValue());
        for (Integer ton: tonalityNotes) { // TODO: Remove
            System.out.print(ton + " ");
        }
        System.out.println();

        for (int i = 0; i < Constants.C_SWARMSIZE; i++)
            swarm.add(new Particle(tonalityNotes, mode));

        updateGlobal();
    }

    @Override
    public void dropSwarm() {
        swarm = new ArrayList<>();
        gFitness = Integer.MIN_VALUE;
        int mode = tonality.getValue();
        ArrayList<Integer> tonalityNotes = getTonalityNotes(tonality.getKey(), tonality.getValue());
        for (int i = 0; i < Constants.C_SWARMSIZE; i++)
            swarm.add(new Particle(tonalityNotes, mode));

        updateGlobal();
    }

    @Override
    public void nextIteration() {
        for (int i = 0; i < Constants.C_SWARMSIZE; i++)
            swarm.get(i).nextIteration(globalPos);
        updateGlobal();
    }

    @Override
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
