package MusicalUtilities;

public class MusicLibrary {
    //Teniamo in considerazione la scala maggiore di Do, la possiamo modificare volendo
    public static String [] notes = {"B3", "C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5", "D5", "E5", "F5", "G5", "A5", "B5", "C6", "D6"};
    //Note con semitoni usate per ora con l'arpeggiatore
    public static String [] notesWithHalves = {"E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3", "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4", "C5", "C#5", "D5", "D#5", "E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5", "C6", "C#6", "D6", "D#6", "E6", "F6", "F#6", "G6", "G#6", "A6"};
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
