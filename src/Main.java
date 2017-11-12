import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ChordSwarm swarm = new ChordSwarm();
        for (int i = 0; i < Constants.C_MAX_IT; i++) {
            swarm.nextIteration();
        }
        ArrayList<Chord> ans = swarm.getGlobalPos();
        System.out.println("FITNESS: " + swarm.getgFitness());
        for (Chord c: ans)
            System.out.print(c);
        System.out.println();
    }

}
