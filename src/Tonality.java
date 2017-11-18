import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Tonality {
    private int tonic;
    private int mode;
    private ArrayList<Integer> tonalityNotes;

    Tonality() {
        tonic = getRand(Constants.LOW_VAL, Constants.LOW_VAL + 12);
        mode = getRand(0, 1);
        tonalityNotes = produceTonalityNotes();
    }

    public boolean contains(int note) {
        return tonalityNotes.contains(note);
    }

    /***
     * This method produces an array of notes which are in current tonality.
     * @return array of corresponding notes.
     */
    private ArrayList<Integer> produceTonalityNotes() {
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

    public int getTonic() {
        return tonic;
    }

    public int getMode() {
        return mode;
    }

    public ArrayList<Integer> getTonalityNotes() {
        return tonalityNotes;
    }

}
