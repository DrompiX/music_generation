import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Main {

    public static void main(String[] args) throws Exception {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        MidiChannel[] channels = synth.getChannels();

//        double phi = Constants.C_C1 + Constants.C_C2;
//        double t = 2.0 / (Math.abs(2 - phi - Math.sqrt(phi * phi - 4 * phi)));
//        System.out.println(t);
        ChordSwarm swarm = new ChordSwarm();
//        for (int i = 0; i < Constants.C_MAX_IT; i++) {
//            swarm.nextIteration();
//        }
        ArrayList<Chord> result = new ArrayList<>();
        int it = 0;
        while (swarm.getgFitness() < 10000 && it < Constants.C_MAX_IT) {
            swarm.nextIteration();
            it++;
        }
        for (Chord c: swarm.getGlobalPos()) result.add(c.cloneIt());

        swarm.dropSwarm();
        it = 0;
        while (swarm.getgFitness() < 10000 && it < Constants.C_MAX_IT) {
            swarm.nextIteration();
            it++;
        }
        for (Chord c: swarm.getGlobalPos()) result.add(c.cloneIt());

        swarm.dropSwarm();
        it = 0;
        while (swarm.getgFitness() < 10000 && it < Constants.C_MAX_IT) {
            swarm.nextIteration();
            it++;
        }
        for (Chord c: swarm.getGlobalPos()) result.add(c.cloneIt());

        swarm.dropSwarm();
        it = 0;
        while (swarm.getgFitness() < 10000 && it < Constants.C_MAX_IT) {
            swarm.nextIteration();
            it++;
        }
        for (Chord c: swarm.getGlobalPos()) result.add(c.cloneIt());
//        while (swarm.getgFitness() < 50000 && it < Constants.C_MAX_IT) {
//            swarm.nextIteration();
//            it++;
//            if (it % 1000 == 0) System.out.println(it);
//        }

//        ArrayList<Chord> ans = swarm.getGlobalPos();
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

        synth.close();
//        Thread.sleep(1000);
//        for (Particle p: swarm.getSwarm()) {
//            for (Chord c: p.getBestPos())
//                System.out.print(c);
//            System.out.println(" | f(x): " + p.getFitness());
//        }
//        System.out.println();
    }

}
