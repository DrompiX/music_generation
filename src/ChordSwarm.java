import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ChordSwarm {
    private ArrayList<Particle> swarm;
    private static ArrayList<Chord> globalPos;
    private static int gFitness;
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

    public void dropSwarm() {
        swarm = new ArrayList<>();
        gFitness = Integer.MIN_VALUE;
        int mode = tonality.getValue();
        ArrayList<Integer> tonalityNotes = getTonalityNotes(tonality.getKey(), tonality.getValue());
        for (int i = 0; i < Constants.C_SWARMSIZE; i++)
            swarm.add(new Particle(tonalityNotes, mode));

        updateGlobal();
    }

    public void nextIteration() {
        for (int i = 0; i < Constants.C_SWARMSIZE; i++)
            swarm.get(i).nextIteration(globalPos);
        updateGlobal();
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
