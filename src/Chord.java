public class Chord {
    public int[] notes;

    Chord() {
        notes = new int[3];
    }

    Chord(int[] notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "(" + notes[0] + ", " + notes[1] + ", " + notes[2] + ")";
    }

    public Chord cloneIt() {
        Chord c = new Chord();
        System.arraycopy(notes, 0, c.notes, 0, 3);
        return c;
    }

    public boolean isSuitable(int note) {
        for (int note1 : notes)
            if (note % 12 == note1 % 12 && note > note1 && Math.abs(note - note1) <= 12)
                return true;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() == obj.getClass()) {
            int count = 0;
            for (int i = 0; i < 3; i++)
                count += (notes[i] == ((Chord) obj).notes[i]) ? 1 : 0;
            return count == 3;
        }
        return false;
    }
}
