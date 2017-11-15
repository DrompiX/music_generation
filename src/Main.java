import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Main {

    public static void main(String[] args) throws Exception {
        Tonality tonality = new Tonality();
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        MidiChannel[] channels = synth.getChannels();

        ChordSwarm cSwarm = new ChordSwarm(tonality);
        ArrayList<Chord> result = generateAllChords(cSwarm, tonality);

        System.out.println("FITNESS: " + cSwarm.getgFitness());
        for (Chord c: result)
            System.out.print(c);

        for (Chord c: result) {
            for (int i: c.notes)
                channels[0].noteOn(i, 75);
            Thread.sleep(1000);
            for (int i: c.notes)
                channels[0].noteOff(i);
        }

        MelodySwarm mSwarm = new MelodySwarm(tonality);
    }

    private static ArrayList<Chord> generateChordBar(ChordSwarm swarm) {
        ArrayList<Chord> bar = new ArrayList<>();
        int it = 0;

        while (swarm.getgFitness() < Constants.C_NICE_FITNESS && it < Constants.C_MAX_IT) {
            swarm.nextIteration();
            it++;
        }
        for (Chord c: swarm.getGlobalPos()) bar.add(c.cloneIt());
        return bar;
    }

    private static boolean appendChordBar(ArrayList<Chord> result, ArrayList<Chord> bar) {
        int curSize = result.size();

        /*if (result.get(curSize - 2) == result.get(curSize - 1) &&
                (result.get(curSize - 1) == bar.get(0) || bar.get(0) == bar.get(1)))*/
        // TODO: check condition to eliminate 3 chords in a row
        if (curSize > 0 && (result.get(curSize - 2) == result.get(curSize - 1) && result.get(curSize - 1) == bar.get(0)
                || result.get(curSize - 1) == bar.get(0) && result.get(0) == bar.get(1)))
            return false;

        for (Chord c : bar) result.add(c.cloneIt());
        return true;
    }

    private static ArrayList<Chord> generateAllChords(ChordSwarm swarm,
                                                      Tonality tonality) {
        ArrayList<Chord> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) { /* Add 4 bards of 4 chords = 16 chords*/
            if (i > 0) swarm = new ChordSwarm(tonality);
            if (!appendChordBar(result, generateChordBar(swarm))) i--;
        }
        return result;
    }

    private static ArrayList<Integer> generateNoteBar(MelodySwarm swarm) {
        ArrayList<Integer> bar = new ArrayList<>();
        int it = 0;

        while (swarm.getgFitness() < Constants.C_NICE_FITNESS && it < Constants.C_MAX_IT) {
            swarm.nextIteration();
            it++;
        }
        for (Integer i: swarm.getGlobalPos()) bar.add(i);
        return bar;
    }

    private static boolean appendNoteBar(ArrayList<Integer> result, ArrayList<Integer> bar) {
        int curSize = result.size();

        /*if (result.get(curSize - 2) == result.get(curSize - 1) &&
                (result.get(curSize - 1) == bar.get(0) || bar.get(0) == bar.get(1)))*/
        /* TODO: check condition to eliminate 3 chords in a row ||| DOESN'T WORK!!! */
        if (curSize == 0) { result.addAll(bar); return true; }
        if ((result.get(curSize - 2).equals(result.get(curSize - 1))
                && result.get(curSize - 1).equals(bar.get(0)))
                || (result.get(curSize - 1).equals(bar.get(0))
                && result.get(0).equals(bar.get(1))))
            return false;

        result.addAll(bar);
        return true;
    }

    private static ArrayList<Integer> generateAllNotes(MelodySwarm swarm,
                                                      Tonality tonality) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) { /* Add 4 bards of 4 chords = 16 chords*/
            if (i > 0) swarm = new MelodySwarm(tonality);
            if (!appendNoteBar(result, generateNoteBar(swarm))) i--;
        }
        return result;
    }

}
