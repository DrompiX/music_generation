import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ChordSwarm {
    private ArrayList<Particle> swarm;
    private ArrayList<Chord> globalPos;
    private int gFitness;

    ChordSwarm() {
        swarm = new ArrayList<>();
        gFitness = Integer.MIN_VALUE;
        Pair<Integer, Integer> tonality = generateTonality();
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

    public void nextIteration() {
        for (int i = 0; i < Constants.C_SWARMSIZE; i++) {
            swarm.get(i).nextIteration(globalPos, gFitness);
            for (Chord c: globalPos)
                System.out.print(c + " ");
            System.out.println(" | Fitness: " + gFitness);
            // updateGlobal();
        }
    }

    private void updateGlobal() {
        int index = -1;
        for (int i = 0; i < Constants.C_SWARMSIZE; i++) {
            if (gFitness < swarm.get(i).getFitness()) {
                gFitness = swarm.get(i).getFitness();
                index = i;
            }
        }
        if (index != -1) {
            globalPos = new ArrayList<>(swarm.get(index).getBestPos());
        }
    }

    private Pair<Integer, Integer> generateTonality() {
        return new Pair<>(getRand(Constants.LOW_VAL, Constants.LOW_VAL + 12), getRand(0, 1));
    }

    private ArrayList<Integer> getTonalityNotes(int tonic, int mode) {
        int upperBorder = Constants.UP_VAL;
        ArrayList<Integer> result = new ArrayList<>();

        int[] octavePositions;
        if (mode == 0) /* Mode is major */
            octavePositions = new int[]{2, 2, 1, 2, 2, 2};
        else /* Mode is minor */
            octavePositions = new int[]{2, 1, 2, 2, 1, 2};

        for (int i = tonic; i <= upperBorder; i += 12) {
            int note = i;
            result.add(note);
            for (int octavePosition : octavePositions) {
                note += octavePosition;
                if (note <= upperBorder && !result.contains(note))
                    result.add(note);
            }
        }
        return result;
    }

    private int getRand(int low, int high) {
        return ThreadLocalRandom.current().nextInt(low,high + 1);
    }

    public ArrayList<Chord> getGlobalPos() {
        return globalPos;
    }

    public void setGlobalPos(ArrayList<Chord> globalPos) {
        this.globalPos = globalPos;
    }

    public ArrayList<Particle> getSwarm() {
        return swarm;
    }

    public void setSwarm(ArrayList<Particle> swarm) {
        this.swarm = swarm;
    }

    public int getgFitness() {
        return gFitness;
    }

    public void setgFitness(int gFitness) {
        this.gFitness = gFitness;
    }
}
