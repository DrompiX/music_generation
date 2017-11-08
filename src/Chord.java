public class Chord {
    public int[] notes;

    Chord(int[] notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "(" + notes[0] + ", " + notes[1] + ", " + notes[2] + ")";
    }
}
