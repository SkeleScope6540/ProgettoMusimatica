package MusicalUtilities;

public class MusicLibrary {
    //Teniamo in considerazione la scala maggiore di Do, la possiamo modificare volendo
    public static String [] notes = {"B3", "C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5", "D5", "E5", "F5", "G5", "A5", "B5", "C6", "D6"};
    public static char tonic = 'c';
    public static char dominant = 'g';
    public static String gradiScalaMaggioreDo = "cdefgab";
    public static int successioniTipiche(char a, char b)
    {
        int gradoA = gradiScalaMaggioreDo.indexOf(a);
        int gradoB = gradiScalaMaggioreDo.indexOf(b);

        //Valori: spesso 3, a volte 2, raramente 0, mai -3
        //Riga = dal, Colonna = al
        int[][] valoreSuccessione = new  int[][]{
            {3,0,0,3,3,2,-3},
            {0,3,0,2,3,2,-3},
            {0,0,3,2,0,3,-3},
            {2,2,0,3,3,0,-3},
            {3,0,0,2,3,2,-3},
            {0,3,2,2,3,3,-3},
            {3,0,3,0,0,2,3}
        };

        return valoreSuccessione[gradoA][gradoB];
    }
}
