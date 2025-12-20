import CellularAutomaton.CellularAutomaton;

public class Main
{
    public static void main(String[] args)
    {
        GenerateMelody();
        //GenerateCA();
    }

    public static void GenerateMelody()
    {
        CellularAutomaton ca = new CellularAutomaton(0,0,0);

        ca.setColors(16);   //Max 24
        ca.setDimension(150);
        ca.setThreshold(1); //Max 8
        ca.setCooldownBetweenStates(0);
        ca.Start();
    }

    public static void GenerateCA()
    {
        CellularAutomaton ca = new CellularAutomaton(0,0,0);

        ca.setColors(8);   //Max 24
        ca.setDimension(150);
        ca.setThreshold(2); //Max 8
        ca.setCooldownBetweenStates(50);
        ca.Start();
    }
}
