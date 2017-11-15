import java.util.ArrayList;

public class MParticle {
    private int[] curPos;
    private int[] bestPos;
    private double[] velocity;
    private ArrayList<Integer> tonalityNotes;
    private ArrayList<Integer> startingNotes;
    private int fitness;
    private int mode; /* 0 = Major; 1 = Minor*/
    private int min_v, max_v;
}
