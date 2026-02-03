package MusicalUtilities;

public class Instrumentinator {
    public static String armonizator(String brano)
    {
        //Prende un brano in semiminime e mette un accordo di 4/4 partendo da ogni inizio battuta
        String armonia = "";
        String [] branoDecostruito = brano.split(" ");

        for(int i = 0; i< branoDecostruito.length; i++)
        {
            if(i % 4 == 0)
            {
                armonia += branoDecostruito[i]+"majw ";
            }
        }

        return armonia;
    }

    public static String arpeggiator(String brano)
    {
        //Prende una nota (semiminima) in input e restituisce un accordo maggiore melodico su di essa
        String [] scala = MusicLibrary.notesWithHalves;
        String arpeggio = "";
        String [] note = brano.split(" ");

        for(int j = 0; j<note.length; j++)
        {
            int index = -1;
            for (int i = 0; i < scala.length; i++) {
                if(note[j].equals(scala[i]))
                {
                    index = i;
                }
            }

            if (index == -1) {
                System.err.println("Arpeggiator - Nota non trovata!");
                return " "+note[j];
            }
            else if (index + 7 > scala.length) {
                System.err.println("Arpeggiator - Arpeggio fuori dal range!");
                return " "+note[j];
            }
            else {
                //Costruisce un arpeggio maggiore a partire da quella nota
                //L'asterisco specifica le triple
                arpeggio += ""+scala[index]+"i* "+scala[index+4]+"i* " +scala[index+7]+"i* "/*+scala[index+4]+"i "*/;
            }
        }

        return arpeggio;
    }

}
