import org.jfugue.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MarkovMusic {
    static final int MELODY_LEN = 20;
    static final int MIN_OCTAVE = 4;
    static final int MAX_OCTAVE = 6;

    static Player player = new Player();
    static Random random = new Random();

    static final String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    static final String[] durations = {"w", "h", "q", "i"};

    //Questo serve per modificare le probabilità che una nota compaia rispetto ad un'altra
    static double[][] probabilities = {
            // C     C#    D     D#    E     F    F#    G     G#    A     A#    B
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From C
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From C#
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From D
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From D#
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From E
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From F
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From F#
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From G
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From G#
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From A
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10},    //From A#
            {0.10, 0.00, 0.20, 0.00, 0.20, 0.10, 0.00, 0.20, 0.00, 0.10, 0.00, 0.10}     //From B
    };

    static final Map<String, Integer> noteToSemitone = new HashMap<>();

    static {
        noteToSemitone.put("C", 0);
        noteToSemitone.put("C#", 1);
        noteToSemitone.put("D", 2);
        noteToSemitone.put("D#", 3);
        noteToSemitone.put("E", 4);
        noteToSemitone.put("F", 5);
        noteToSemitone.put("F#", 6);
        noteToSemitone.put("G", 7);
        noteToSemitone.put("G#", 8);
        noteToSemitone.put("A", 9);
        noteToSemitone.put("A#", 10);
        noteToSemitone.put("B", 11);
    }

    public static void main(String[] args)
    {
        System.out.println("Hello");
        String music = "V0 I[Flute] ";

        for (int i = 0; i < probabilities.length; i++) {
            for (int j = 0; j < probabilities[i].length; j++) {
                System.out.printf("%.3f", probabilities[i][j]);
            }
            System.out.println();
        }

        int currentIndex = random.nextInt(notes.length);
        String currentNote = notes[currentIndex];
        String currentDuration = durations[random.nextInt(durations.length)];
        int currentOctave = (MAX_OCTAVE + MIN_OCTAVE) /2;

        music = music + currentNote + currentOctave + currentDuration ;

        for(int i = 0; i < MELODY_LEN; i++)
        {
            String nextNote = notes[nextWithP(probabilities[currentIndex])];
            String nextDuration = durations[random.nextInt(durations.length)];

            int bestOctave = 0;
            int bestDistance = 1000;
            for(int o = MIN_OCTAVE; o <= MAX_OCTAVE; o++) {
                int currentMidi = noteToSemitone.get(currentNote) + currentOctave * 12;
                int candidateMidi = noteToSemitone.get(nextNote) + o * 12;
                int distance = Math.abs(candidateMidi - currentMidi);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestOctave = o;
                }
            }

            music += " " + nextNote + bestOctave + nextDuration;

            currentNote = nextNote;
            currentOctave = bestOctave;
        }

        music = music + " Rq";



        System.out.println("Music is: " + music);
        player.play(music);
    }

    static private int nextWithP(double[] p){
        int i = 0;
        double r = random.nextDouble();
        double somma = 0;
        for(int j = 0; j < p.length; j++){
            somma = somma + p[j];
            if (somma > r) return j;
        }
        return i;
    }
}
