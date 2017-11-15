import javafx.util.Pair;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Swarm {

    protected Pair<Integer, Integer> generateTonality() {
        return new Pair<>(getRand(Constants.LOW_VAL, Constants.LOW_VAL + 12), getRand(0, 1));
    }

    protected ArrayList<Integer> getTonalityNotes(int tonic, int mode) {
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

    public abstract void nextIteration();
    public abstract void dropSwarm();
    protected abstract void updateGlobal();

}
