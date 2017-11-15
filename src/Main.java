import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Main {

    static int counter;

    public static void main(String[] args) throws Exception {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        MidiChannel[] channels = synth.getChannels();

        ChordSwarm swarm = new ChordSwarm();
        ArrayList<Chord> result = generateAllChords(swarm);

        System.out.println("FITNESS: " + swarm.getgFitness());
        for (Chord c: result)
            System.out.print(c);

        for (Chord c: result) {
            for (int i: c.notes)
                channels[0].noteOn(i, 75);
            Thread.sleep(1000);
            for (int i: c.notes)
                channels[0].noteOff(i);
        }
    }

    private static ArrayList<Chord> generateBar(ChordSwarm swarm) {
        ArrayList<Chord> bar = new ArrayList<>();
        int it = 0;

        while (swarm.getgFitness() < Constants.C_NICE_FITNESS && it < Constants.C_MAX_IT) {
            swarm.nextIteration();
            it++;
        }
        for (Chord c: swarm.getGlobalPos()) bar.add(c.cloneIt());
        return bar;
    }

    private static boolean appendBar(ArrayList<Chord> result, ArrayList<Chord> bar) {
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

    private static ArrayList<Chord> generateAllChords(ChordSwarm swarm) {
        ArrayList<Chord> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) { /* Add 4 bards of 4 chords = 16 chords*/
            if (i > 0) swarm.dropSwarm();
            if (!appendBar(result, generateBar(swarm))) i--;
        }
        return result;
    }

}
