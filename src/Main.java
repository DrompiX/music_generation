import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Tonality tonality = new Tonality();
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        MidiChannel[] channels = synth.getChannels();

        ChordSwarm cSwarm = new ChordSwarm(tonality);
        ArrayList<Chord> chordsResult = generateAllChords(cSwarm, tonality);
        ArrayList<ArrayList<Chord>> chordBars = getBars(chordsResult);

        System.out.println("FITNESS: " + cSwarm.getgFitness());
        for (Chord c: chordsResult)
            System.out.print(c);
        System.out.println();

        MelodySwarm mSwarm = new MelodySwarm(tonality, chordBars.get(0));
        ArrayList<Integer> melodyResult = generateAllNotes(mSwarm, tonality, chordBars);
        System.out.println("FITNESS: " + mSwarm.getgFitness());
        for (Integer c: melodyResult)
            System.out.print(c + " ");

        writeToMidi(chordsResult, melodyResult);

        /*for (int i = 0; i < melodyResult.size(); i += 2) {
            if (i > 0) channels[0].noteOff(melodyResult.get(i - 1));

            for (int c : chordsResult.get(i / 2).notes)
                channels[0].noteOn(c, 75);

            channels[0].noteOn(melodyResult.get(i), 75);

            Thread.sleep(1000);

            for (int c : chordsResult.get(i / 2).notes)
                channels[0].noteOff(c);
            channels[0].noteOff(melodyResult.get(i));

            channels[0].noteOn(melodyResult.get(i + 1), 75);
            Thread.sleep(1000);
        }*/
    }

    private static void writeToMidi(ArrayList<Chord> chords, ArrayList<Integer> melody) {
        StringBuilder musicString = new StringBuilder();
        for (int i = 0; i < melody.size(); i+=2) {
            for (int j = 0; j < 3; j++) {
                musicString.append(chords.get(i / 2).notes[j]);
                musicString.append("h+");
            }
            musicString.append(melody.get(i));
            musicString.append("h ");
            musicString.append(melody.get(i + 1));
            musicString.append("h ");
        }
        Pattern pattern = new Pattern(musicString.toString()).setVoice(0).setInstrument("Piano").setTempo(140);
        File midi = new File("gen_music.mid");
        try {
            MidiFileManager.savePatternToMidi(pattern, midi);
        } catch (Exception ex) {
            System.out.println("Exception in midi output: " + ex.getMessage());
        }
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
        // TODO: check condition to eliminate 3 chords in a row || DOESN'T WORK
        if (curSize > 0 && (result.get(curSize - 2).equals(result.get(curSize - 1)) && result.get(curSize - 1).equals(bar.get(0))
                || result.get(curSize - 1).equals(bar.get(0)) && result.get(0).equals(bar.get(1))))
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

        while (swarm.getgFitness() < Constants.M_NICE_FITNESS && it < Constants.M_MAX_IT) {
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

    private static ArrayList<Integer> generateAllNotes(MelodySwarm swarm, Tonality tonality,
                                                       ArrayList<ArrayList<Chord>> chords) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) { /* Add 4 bards of 4 chords = 16 chords*/
            if (i > 0) swarm = new MelodySwarm(tonality, chords.get(i));
            if (!appendNoteBar(result, generateNoteBar(swarm))) i--;
        }
        return result;
    }

    private static ArrayList<ArrayList<Chord>> getBars(ArrayList<Chord> chords) {
        ArrayList<ArrayList<Chord>> bars = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bars.add(new ArrayList<>());
            for (int j = i * 4; j < i * 4 + 4; j++)
                bars.get(i).add(chords.get(j).cloneIt());
        }
        return bars;
    }

}
