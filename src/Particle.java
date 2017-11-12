import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Particle {
    private ArrayList<Chord> curPos;
    private ArrayList<Chord> bestPos;
    private double[][] velocity;
    private ArrayList<Integer> tonalityNotes;
    private ArrayList<Integer> startingNotes;
    private int fitness;
    private int mode; /* 0 = Major; 1 = Minor*/

    /* Mode - major / minor */
    Particle(ArrayList<Integer> tonalityNotes, int mode) {
        this.curPos = new ArrayList<>();
        this.bestPos = new ArrayList<>();
        this.velocity = new double[8][3];
        this.tonalityNotes = new ArrayList<>(tonalityNotes);
        this.startingNotes = getStartingNotes(tonalityNotes);
        this.mode = mode;
//        for (int it: startingNotes)
//            System.out.print(it + " ");
//        System.out.println();

        init();
        fitness = findFitness();
//        for (Chord c: curPos)
//            System.out.print(c);
//        System.out.println(" | FITNESS: " + fitness);
    }

    /* TODO: TEST Particle initialization */
    private void init() {
        for (int i = 0; i < Constants.C_DIMENSIONS; i++) {
            curPos.add(new Chord(getRandomChord()));
//            System.out.println(curPos.get(0));
            int min_v = -(Constants.UP_VAL - Constants.LOW_VAL);
            int max_v = -min_v;
            for (int j = 0; j < 3; j++)
                velocity[i][j] = getRand(min_v, max_v);
//            System.out.println(curPos.get(i));
        }
        bestPos = new ArrayList<>(curPos);
    }

    public void nextIteration(ArrayList<Chord> globalPos, int globalFitness) {
        for (int i = 0; i < velocity.length; i++) {
            for (int j = 0; j < velocity[i].length; j++) {
                velocity[i][j] = Constants.C_W * velocity[i][j] +
                        Constants.C_C1 * getRand(0, 1) * (bestPos.get(i).notes[j] - curPos.get(i).notes[j]) *
                                Constants.C_C2 * getRand(0, 1) * (globalPos.get(i).notes[j] - curPos.get(i).notes[j]);
            }

            updateChords();
        }

        int newFitness = findFitness();
        if (newFitness > fitness) {
            fitness = newFitness;
            bestPos = new ArrayList<>(curPos);
            if (newFitness > globalFitness) {
                globalFitness = newFitness;
                globalPos = new ArrayList<>(bestPos);
                System.out.println("JHHAHAHAHAHAHA");
            }
        }
    }

    private void updateChords() {
        for (int i = 0; i < velocity.length; i++) {
            int[] chord = curPos.get(i).notes;
            for (int j = 0; j < velocity[i].length; j++) {
                chord[j] += (int) velocity[i][j];
            }
            curPos.set(i, new Chord(chord));
            Arrays.sort(curPos.get(i).notes); //TODO
        }
    }

    /* TODO: Write correct implementation */
    private int findFitness() {
        int fitness = 0;
        for (int i = 0; i < curPos.size(); i++) {
            int[] chord = curPos.get(i).notes;

            fitness += tonalityNotes.contains(chord[0]) ? 250 : -1000;
            fitness += tonalityNotes.contains(chord[1]) ? 250 : -1000;
            fitness += tonalityNotes.contains(chord[2]) ? 250 : -1000;
            /*if (tonalityNotes.contains(chord[0])) fitness += 250;
            else fitness -= 1000;
            if (tonalityNotes.contains(chord[1])) fitness += 250;
            else fitness -= 1000;
            if (tonalityNotes.contains(chord[2])) fitness += 250;
            else fitness -= 1000;*/

            /* Check if the first note is tonic or dominant or subdominant */
            if (startingNotes.contains(chord[0])) {
                fitness += 250;
                /* Check if melody starts with tonic (to give more pleasure) */
                if ((i == 0 || i == curPos.size() - 1) && isTonic(chord[0]))
                    fitness += 100;
            } else fitness -= 500;

            if (mode == 0) { /* Major */
                fitness += (chord[1] == chord[0] + 4) ? 100 : -50;
//                if (chord[1] == chord[0] + 4) fitness += 100;
//                else fitness -= 50;

                fitness += (chord[2] == chord[0] + 7) ? 100 : -50;
//                if (chord[2] == chord[0] + 7) fitness += 100;
//                else fitness -= 50;

                /* Cool chord */
                if (isTonic(chord[0]) && chord[1] == chord[0] + 4 && chord[2] == chord[0] + 7)
                    fitness += 1000;
                else
                    fitness -= 50;

            } else { /* Minor */
                fitness += (chord[1] == chord[0] + 3) ? 100 : -50;
//                if (chord[1] == chord[0] + 3) fitness += 100;
//                else fitness -= 50;

                fitness += (chord[2] == chord[0] + 7) ? 100 : -50;
//                if (chord[2] == chord[0] + 7) fitness += 100;
//                else fitness -= 50;

                if (isTonic(chord[0]) && chord[1] == chord[0] + 3 && chord[2] == chord[0] + 7)
                    fitness += 1000;
                else
                    fitness -= 50;
            }

            if (i < curPos.size() - 1) {
                if (Math.abs(chord[0] - curPos.get(i + 1).notes[0]) < 12)
                    fitness += 500;
                else
                    fitness -= 500;
            }
        }
        return fitness;
    }

    private boolean isTonic(int note) {
        return startingNotes.indexOf(note) % 3 == 0;
    }

    private boolean isSubdominant(int note) {
        return startingNotes.indexOf(note) % 3 == 1;
    }

    private boolean isDominant(int note) {
        return startingNotes.indexOf(note) % 3 == 2;
    }

    private ArrayList<Integer> getStartingNotes(ArrayList<Integer> notes) {
        ArrayList<Integer> result = new ArrayList<>();
        int tonic = notes.get(0);
        int subdom = notes.get(3);
        int dom = notes.get(4);

        for (int it: notes) {
            if ((it - tonic) % 12 == 0 || (it - subdom) % 12 == 0 || (it - dom) % 12 == 0)
                result.add(it);
        }
        return result;
    }

    private int[] getRandomChord() {
        int[] chord = new int[3];
        for (int i = 0; i < 3; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, tonalityNotes.size());
            chord[i] = tonalityNotes.get(randomIndex);
        }
        Arrays.sort(chord);
        return chord;
    }

    private double getRand(int low, int high) {
        return ThreadLocalRandom.current().nextDouble(low,high + 1);
    }

    public ArrayList<Chord> getCurPos() {
        return curPos;
    }

    public void setCurPos(ArrayList<Chord> curPos) {
        this.curPos = curPos;
    }

    public ArrayList<Chord> getBestPos() {
        return bestPos;
    }

    public void setBestPos(ArrayList<Chord> bestPos) {
        this.bestPos = bestPos;
    }

    public double[][] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[][] velocity) {
        this.velocity = velocity;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void setFintess(int fintess) {
        this.fitness = fintess;
    }

}
