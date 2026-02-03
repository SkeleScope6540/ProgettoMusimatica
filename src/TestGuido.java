import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.pattern.Pattern;

public class TestGuido
{
    public static void main(String[] args) {
        TestGuido t = new TestGuido();
        //t.Guido();
        t.Arpeggiator();
    }        

    public void Guido()
    {
        Random random = new Random();
        String rawInUt;
        char[] inUt;
        float totQuartiAccordi = 0;
        float tempQuartiAccordi = 0;
        String melodia = "V0 I[Flute]";
        String armonia = "V1 I[Guitar]";
        String basso = "V2 I[ACOUSTIC_BASS]";
        String vocali = "abcdefghijklmnopqrstuvwxyz";
        //Di seguito tutte le note e le variazioni della quarta ottava + due pause (le R)
        String noteOrdinate[] = {"A", "A", "B", "C", "C", "D", "D", "E", "R", "F", "F", "G", "G", "A#", "A#", "B", "C#", "C#", "D#", "D#", "E", "R", "F#", "F#", "G#", "G#"};
        String note[] = noteOrdinate;
        Collections.shuffle(Arrays.asList(note));
        int terza = 4;
        int quinta = 7;
        int ottava = 4;
        int ottavaBasso = ottava -1;
        int ottavaMelodia = ottava +1;
        int cambioDurataAccordi = 4;

        System.err.println("Shuffled note list = ");
        for (String s : note) {
            System.err.print(s);
        }
        System.err.println("");

        String durate[]= {"h", "q", "q", "i"};
        float valDurate[]= {2f, 1f, 1f, 0.5f};
        String accordi[]= {"maj", "min"/* , "aug", "dim"*/};
        int c = random.nextInt(accordi.length);
        Scanner scanner = new Scanner(System.in);
        if (accordi[c].equals("maj")) {
            terza = 4;
            quinta = 7;
        }
        else if (accordi[c].equals("min")) {
            terza = 3;
            //Sceglie randomicamente l'intervallo fra quinta perfetta o diminuita
            if (random.nextBoolean()) quinta = 6; 
            else    quinta = 7;
        }

        System.out.println("Ciao, questo e' un algoritmo basato sul metodo di Guido D'Arezzo, converte frasi in musica!");
        System.out.println("Inseriscine una e ascolta!");

        rawInUt = scanner.nextLine();

        System.out.println("Convertendo il tuo input \""+rawInUt+"\" in musica!");

        inUt = rawInUt.toCharArray();

        //Questo è il primo accordo
        armonia = armonia + " " + note[vocali.indexOf(inUt[0])] + accordi[c] + "w";

        for(int i = 0; i < inUt.length; i++)
        {
            int a = vocali.indexOf(inUt[i]);
            if (a == -1) continue;
            if (totQuartiAccordi == cambioDurataAccordi) {
                totQuartiAccordi = 0;
                armonia = armonia + " " + note[a] + ottava + accordi[c] + "w";
                basso = basso + " " + note[a] + ottavaBasso + "w";
            }

            if (vocali.contains(inUt[i]+"")) 
            {
                if (random.nextInt(10)>8 && !(totQuartiAccordi + 1.5f > cambioDurataAccordi))
                {
                    totQuartiAccordi += 1.5f;
                    melodia = melodia + AutoArpeggiator(note[a] + ottavaMelodia, terza, quinta);
                }
                else
                {
                    int b = random.nextInt(durate.length);
                    tempQuartiAccordi = valDurate[b];
                    if (totQuartiAccordi + tempQuartiAccordi > cambioDurataAccordi) {
                        tempQuartiAccordi = 0.5f;
                        b = 3;
                    }
                    totQuartiAccordi += tempQuartiAccordi;
                    melodia = melodia + " " + note[a] + ottavaMelodia + durate[b];
                }
            }
        }

        Player player = new Player();
        System.out.println("Note generate melodia:" + melodia);
        System.out.println("Note generate armonia:" + armonia);
        System.out.println("Note generate basso:" + basso);

        Pattern pattern = new Pattern(armonia+" "+basso+" "+melodia);
        
        player.play(pattern);

        scanner.close();    
    }   

    public String AutoArpeggiator(String nota, int terza, int quinta)
    {
        String [] scala = {"C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4", "C5", "C#5", "D5", "D#5", "E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5", "C6", "C#6", "D6", "D#6", "E6", "F6", "F#6", "G6"};
        String arpeggio = "";
        int index = -1;
        
        for (int i = 0; i < scala.length; i++) {
            if(nota.equals(scala[i]))
            {
                index = i;
            }
        }

        if (index == -1) {
            System.err.println("Arpeggiator - Nota non trovata!");
            return " "+nota;
        }
        else if (index + 7 > scala.length) {
            System.err.println("Arpeggiator - Arpeggio fuori dal range!");
            return " "+nota;
        }
        else {
            arpeggio = " "+scala[index]+"i "+scala[index+terza]+"i "+scala[index+quinta]+"i";
        }
  
        return arpeggio;
    }

    public void Arpeggiator()
    {
        String [] scala = {"C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4", "C5", "C#5", "D5", "D#5", "E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5", "C6", "C#6", "D6", "D#6", "E6", "F6", "F#6", "G6"};
        ArrayList<String> noteList = new ArrayList<String>();
        Player player = new Player();
        String arpeggio = "";
        int index = -1;
        Scanner scanner = new Scanner(System.in);
        String inUt = "A4";

        System.out.println("Ciao, questo è un arpeggiatore che tiene in conto le note da C4 a C6, tenta di non romperlo per favore!");
        System.out.println("Inserisci le tue note, quando hai fatto digita -1");
        inUt = scanner.nextLine();

        while (!inUt.equals("-1"))
        {
            noteList.add(inUt);
            inUt = scanner.nextLine();
        }

        String [] note = noteList.toArray(new String[0]);
        
        for(int j = 0; j < note.length; j ++)
        {
            index =-1;
            for (int i = 0; i < scala.length; i++) {
                if(note[j].equals(scala[i]))
                {
                    index = i;
                }
            }

            if (index == -1) {
                System.err.println("Arpeggiator - Nota non trovata!");
            }
            else if (index + 7 > scala.length) {
                System.err.println("Arpeggiator - Arpeggio fuori dal range!");
            }
            else {
                arpeggio = arpeggio +" "+scala[index]+"i "+scala[index+4]+"i "+scala[index+7]+"i";
            }
        }

        player.play(arpeggio);
        scanner.close();
    }
}