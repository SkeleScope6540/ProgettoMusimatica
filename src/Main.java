import CellularAutomaton.CellularAutomaton;
import GeneticAlgorithm.MusicalGA;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class Main
{
    public static void main(String[] args)
    {
        //GenerateMelody();
        //GenerateCA();
        GenerateMusicalGA();
    }

    public static void GenerateMelody()
    {
        CellularAutomaton ca = new CellularAutomaton(0,0,0);

        ca.setColors(16);   //Max 24
        ca.setDimension(150);
        ca.setThreshold(1); //Max 8
        ca.setCooldownBetweenStates(0);
        ca.setStepsBetweenControl(20);
        ca.setMaxIterations(1000);
        ca.Start();
    }

    public static void GenerateCA()
    {
        CellularAutomaton ca = new CellularAutomaton(0,0,0);

        ca.setColors(14);   //Max 24
        ca.setDimension(150);
        ca.setThreshold(2); //Max 8
        ca.setCooldownBetweenStates(50);
        ca.setStepsBetweenControl(50);
        ca.Start();
    }

    public static void GenerateMusicalGA()
    {
        MusicalGA mga = new MusicalGA();
        String[] strings;
        mga.setStartingStrings(null);
        mga.generateStartingPopulation(1001, 10);
        String melodiaVecchia = "V0 I[Guitar] "+mga.getStartingStrings()[0]+mga.getStartingStrings()[0];
        strings = mga.startGA();

        Player player = new Player();
        System.out.println("Melodia di partenza: "+melodiaVecchia);
        player.play(melodiaVecchia);

        Player player2 = new Player();
        String melodia = "V1 I[Guitar] " +strings[0] + strings[0];
        System.out.println("Melodia del miglior cromosoma: "+melodia);
        String armonia = "V2 I[Flute] " + Instrumentinator.armonizator(strings[0]) + Instrumentinator.armonizator(strings[0]);
        System.out.println("Armonia del armonizator: "+armonia);
        Pattern pattern = new Pattern(melodia+" "+armonia);
        player2.play(pattern);
    }
}
