import CellularAutomaton.CellularAutomaton;
import GeneticAlgorithm.MusicalGA;
import MusicalUtilities.Instrumentinator;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class Main
{
    public static void main(String[] args)
    {
        //GenerateMelodyFromCA();
        //GenerateCA();
        GenerateMusicalGA(10);
        //GenerateMusicalGAFromCA(15);
    }

    public static void GenerateMelodyFromCA()
    {
        CellularAutomaton ca = new CellularAutomaton(0,0,0);

        ca.setColors(16);   //Max 24
        ca.setDimension(150);
        ca.setThreshold(1); //Max 8
        ca.setCooldownBetweenStates(0);
        ca.setStepsBetweenControl(11);
        ca.setMaxIterations(1000);
        ca.Start(true);
    }

    public static void GenerateCA()
    {
        CellularAutomaton ca = new CellularAutomaton(0,0,0);

        ca.setColors(14);   //Max 24
        ca.setDimension(150);
        ca.setThreshold(2); //Max 8
        ca.setCooldownBetweenStates(50);
        ca.setStepsBetweenControl(50);
        ca.Start(true);
    }

    public static void GenerateMusicalGA(int populationSize)
    {
        MusicalGA mga = new MusicalGA();
        String[] strings;
        mga.setStartingStrings(null);
        mga.generateStartingPopulation(1001, populationSize);
        String melodiaVecchia = "V0 "+mga.getStartingStrings()[0]+mga.getStartingStrings()[0];
        strings = mga.startGA();

        Player player = new Player();
        System.out.println("Melodia di partenza: "+melodiaVecchia);
        player.play(melodiaVecchia);

        Player player2 = new Player();
        String melodia = "V1 " +strings[0] + strings[0];
        System.out.println("Melodia del miglior cromosoma: "+melodia);
        String armonia = "V2 I[Flute] " + Instrumentinator.armonizator(strings[0]) + Instrumentinator.armonizator(strings[0]);
        System.out.println("Armonia dell'armonizator: "+armonia);
        String motivetto = "V3 I[Guitar] " + Instrumentinator.arpeggiator(strings[0]) + Instrumentinator.arpeggiator(strings[0]);
        System.out.println("Motivetto dell'arpeggiator: "+motivetto);
        Pattern pattern = new Pattern(melodia+" "+armonia+" "+motivetto);
        player2.play(pattern);
    }

    //Metodo che crea una stringa di 16 note a partire dall'output del cellular automaton e la da come input all'algoritmo genetico
    public static void GenerateMusicalGAFromCA(int populationSize)
    {
        CellularAutomaton ca = new CellularAutomaton(140,14,1);
        ca.setMaxIterations(5000);
        ca.setStepsBetweenControl(12);
        ca.setCooldownBetweenStates(0);
        String [] cAOutputNotes;
        cAOutputNotes = ca.Start(false);
        String [] cAOutputStrings = new String[populationSize];

        for(int j=0;j<populationSize;j++)
        {
            //Ripuliamo la stringa dal null, così non genera errore
            cAOutputStrings[j] = "";
            //16 è la dimensione dei singoli individui, 4 battute da 4 note ognuna
            for(int i=j;i<j+16;i++)
            {
                cAOutputStrings[j] += cAOutputNotes[i] + " ";
            }
            System.out.println("Melodia di partenza "+ j +": "+cAOutputStrings[j]);
        }

        MusicalGA mga = new MusicalGA();
        String[] mGAOutputStrings;
        mga.setStartingStrings(cAOutputStrings);
        mga.generateStartingPopulation(101, populationSize);
        String melodiaVecchia = "V0 "+mga.getStartingStrings()[0]+mga.getStartingStrings()[0];
        mGAOutputStrings = mga.startGA();

        Player player = new Player();
        System.out.println("Melodia di partenza: "+melodiaVecchia);
        player.play(melodiaVecchia);

        Player player2 = new Player();

        String melodia = "V1 I[Flute] " +mGAOutputStrings[0] + mGAOutputStrings[0];
        System.out.println("Melodia del miglior cromosoma: "+melodia);

        String armonia = "V2 " + Instrumentinator.armonizator(mGAOutputStrings[0]) + Instrumentinator.armonizator(mGAOutputStrings[0]);
        System.out.println("Armonia dell'armonizator: "+armonia);

        String motivetto = "V3 I[Guitar] " + Instrumentinator.arpeggiator(mGAOutputStrings[0]) + Instrumentinator.arpeggiator(mGAOutputStrings[0]);
        System.out.println("Motivetto dell'arpeggiator: "+motivetto);

        Pattern pattern = new Pattern(melodia+" "+armonia+" "+motivetto);

        player2.play(pattern);
    }
}
