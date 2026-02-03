package GeneticAlgorithm;

import MusicalUtilities.MusicLibrary;

import java.util.Random;

public class MusicalGA {
    String [] startingStrings;
    //La popolazione di individui
    String [] globalPopulation;
    //La selezione dei migliori individui
    String [] currentBestPopulation;
    //Valutazioni delle varie stringhe date dalla fitness function
    int [] stringValues;
    int currentGeneration = 0;
    int maxGenerations = 10;
    int populationSize;

    public void generateStartingPopulation(int generations, int populationSize) {
        //La sua dimensione è data dalle operazioni di crossover (psize*psize) e di mutazione (+ psize)
        globalPopulation = new String[populationSize * populationSize + populationSize];
        currentBestPopulation = new String[populationSize];
        stringValues = new int[populationSize];
        Random rand = new Random();
        //Se è nulla la genera random
        if (startingStrings == null) {
            startingStrings = new String[populationSize];
            for (int j = 0; j < populationSize; j++) {
                startingStrings[j] = "";
                for (int i = 0; i < 16; i++) {
                    startingStrings[j] += (MusicLibrary.notes[rand.nextInt(MusicLibrary.notes.length)] + " ");
                }
            }
        }

        currentBestPopulation = startingStrings;

        //Previene un numero di generazioni nullo o negativo
        if (generations < 1) {
            System.err.println("Generation number must be greater than 0");
        }
        else maxGenerations = generations;

        this.populationSize = populationSize;
        for (int i = 0; i < populationSize; i++) {
            System.out.println(i + " - " + startingStrings[i]);
        }
    }

    public String[] startGA() {
        //Simula il processo di evoluzione
        for(int i=0;i<maxGenerations;i++)
        {
            currentGeneration = i;
            geneticAlgorithm();
        }

        currentGeneration = 0;
        return currentBestPopulation;
    }

    private void geneticAlgorithm()
    {
        crossoverKins();
        mutateKins();
        applyFitnessFunction(globalPopulation);
    }

    private void crossoverKins()
    {
        int j = 0;
        int k = 0;
        for(int i = populationSize; i < globalPopulation.length; i++)
        {
            globalPopulation[i] = crossover(currentBestPopulation[k],currentBestPopulation[j]);

            if(j == populationSize - 1)
            {
                j = 0;
                k ++;
            }
            else
                j++;
        }
    }

    private String crossover(String first,  String second)
    {
        String [] temp1 = first.split(" ");
        String [] temp2 = second.split(" ");
        String result = "";

        //Prende la prima metà degli elementi di temp1 e la seconda di temp2 e li fonde
        for(int i = 0; i < temp1.length; i++)
        {
            result += (i < temp1.length / 2 ? temp1[i] : temp2[i]) + " ";
        }

        return result;
    }

    private void mutateKins()
    {
        //Stiamo trattando la singola nota come gene
        Random rand = new Random();
        String [] temp;
        for(int i = 0; i < populationSize; i++)
        {
            temp = currentBestPopulation[i].split(" ");
            temp[rand.nextInt(temp.length)] = MusicLibrary.notes[rand.nextInt(MusicLibrary.notes.length)];
            globalPopulation[i] = "";
            //Usiamo questa assegnazione manuale per assicurarci che rimangano le separazioni via spazi
            for (String s : temp) {
                globalPopulation[i] += s + " ";
            }
        }
    }

    private void applyFitnessFunction(String [] population)
    {
        String [] temp;
        int [] valutazioni = new int[population.length];

        //valuta ogni elemento nella popolazione globale
        //i geni sono le singole note, un accordo avviene ogni 4 geni
        for(int i = 0; i < population.length; i++)
        {
            temp = population[i].split(" ");
            valutazioni[i] =
                    noteConsonanti(temp) +
                    saltiVietati(temp) +
                    successioniTipiche(temp) +
                    tonicaODominante(temp) +
                    tonicaFinale(temp);
        }

        System.out.println("--Risultati popolazione generazione "+currentGeneration+"--");
        int i = 0;
        for(String s : globalPopulation)
        {
            System.out.println(s + " valutazione: " +valutazioni[i]);
            i++;
        }
        System.out.println("------------------------------------------------");

        int tempValore = Integer.MIN_VALUE;
        int tempElemento = 0;
        for(int j = 0; j < currentBestPopulation.length - 3; j++)
        {
            //Cerca l'elemento con valutazione massima e lo imposta nell'array della popolazione migliore attuale
            for(int k = 0; k < valutazioni.length; k++)
            {
                if (valutazioni[k] > tempValore) {
                    tempValore = valutazioni[k];
                    tempElemento = k;
                }
            }
            //Sceglie l'elemento meglio valutato e lo aggiunge
            currentBestPopulation[j] = globalPopulation[tempElemento];
            //Setta il valore della sua valutazione al minimo, così non viene riselezionato
            valutazioni[tempElemento] = Integer.MIN_VALUE;
            tempValore = Integer.MIN_VALUE;
        }
        //Ne aggiungiamo due casuali alla fine
        Random rand = new Random();
        for(int j = currentBestPopulation.length - 2; j < currentBestPopulation.length; j++)
        {
            currentBestPopulation[j] = globalPopulation[rand.nextInt(currentBestPopulation.length)];
        }

        System.out.println("----Migliore popolazione della generazione "+currentGeneration+"----");
        for(String s : currentBestPopulation)
        {
            System.out.println(s);
        }
        System.out.println("Con due casuali alla fine, per aggiungere varietà");
        System.out.println("--------------------------------------------------");
    }

    private int noteConsonanti(String[] individuo)
    {
        //Per ogni accordo, se le note nella sua battuta sono appartenenti all'accordo,
        //+1 punto, se non lo sono -1 punto, a meno che non siano congiunte fra loro, in
        //tal caso +1 punto
        int valutazione = 0;
        char accordoAttuale;
        char [] noteDellAccordoAttuale = new char[4];

        for(int i = 0; i < 4; i++)
        {
            accordoAttuale = individuo[i*4].toLowerCase().toCharArray()[0];
            noteDellAccordoAttuale[0] = individuo[i*4].toLowerCase().toCharArray()[0];
            noteDellAccordoAttuale[1] = individuo[i*4+1].toLowerCase().toCharArray()[0];
            noteDellAccordoAttuale[2] = individuo[i*4+2].toLowerCase().toCharArray()[0];
            noteDellAccordoAttuale[3] = individuo[i*4+3].toLowerCase().toCharArray()[0];

            for(int j  = 0; j < 4; j++)
            {
                if(MusicLibrary.gradiScalaMaggioreDo.indexOf(noteDellAccordoAttuale[j]) == MusicLibrary.gradiScalaMaggioreDo.indexOf(accordoAttuale)
                ||  MusicLibrary.gradiScalaMaggioreDo.indexOf(noteDellAccordoAttuale[j]) == MusicLibrary.gradiScalaMaggioreDo.indexOf(accordoAttuale) + 3
                ||  MusicLibrary.gradiScalaMaggioreDo.indexOf(noteDellAccordoAttuale[j]) == MusicLibrary.gradiScalaMaggioreDo.indexOf(accordoAttuale) + 5)
                {
                    valutazione++;
                }
                else if(j > 0 && MusicLibrary.gradiScalaMaggioreDo.indexOf(noteDellAccordoAttuale[j]) == MusicLibrary.gradiScalaMaggioreDo.indexOf(noteDellAccordoAttuale[j-1]))
                {
                    valutazione++;
                }
                else if(j < 3 && MusicLibrary.gradiScalaMaggioreDo.indexOf(noteDellAccordoAttuale[j]) == MusicLibrary.gradiScalaMaggioreDo.indexOf(noteDellAccordoAttuale[j+1]))
                {
                    valutazione++;
                }
                else
                {
                    valutazione--;
                }
            }
        }

        return valutazione;
    }
    private int saltiVietati(String[] individuo)
    {
        //Se da una nota si passa alla successiva con un salto vietato, -3 punti
        int valutazione = 0;
        int gradoNotaAttuale;
        int gradoNotaSuccessiva;
        for(int i = 0; i < individuo.length-1; i++)
        {
            gradoNotaAttuale = MusicLibrary.gradiScalaMaggioreDo.indexOf(individuo[i].toLowerCase().toCharArray()[0]);
            gradoNotaSuccessiva = MusicLibrary.gradiScalaMaggioreDo.indexOf(individuo[i+1].toLowerCase().toCharArray()[0]);
            if(gradoNotaAttuale == gradoNotaSuccessiva -1 || gradoNotaAttuale == gradoNotaSuccessiva +1)
            {
                //Salto di settima!
                valutazione-=3;
            }
        }
        return valutazione;
    }
    private int successioniTipiche(String[] individuo)
    {
        //Se due o più accordi ricadono nelle successioni tipiche, + punti
        char [] gradiAccordi = new char[4];
        gradiAccordi[0] = individuo[0].toLowerCase().toCharArray()[0];
        gradiAccordi[1] = individuo[4].toLowerCase().toCharArray()[0];
        gradiAccordi[2] = individuo[8].toLowerCase().toCharArray()[0];
        gradiAccordi[3] = individuo[12].toLowerCase().toCharArray()[0];
        return MusicLibrary.successioniTipiche(gradiAccordi[0], gradiAccordi[1]) +
                MusicLibrary.successioniTipiche(gradiAccordi[1], gradiAccordi[2]) +
                MusicLibrary.successioniTipiche(gradiAccordi[2], gradiAccordi[3]) +
                MusicLibrary.successioniTipiche(gradiAccordi[3], gradiAccordi[0]);
    }
    private int tonicaODominante(String[] individuo)
    {
        //Se il primo elemento è la tonica o la dominante (scala di DO), +5
        int valutazione = 0;
        if(individuo[0].toLowerCase().toCharArray()[0] == MusicLibrary.tonic || individuo[0].toLowerCase().toCharArray()[0] == MusicLibrary.dominant)
        {
            valutazione = 5;
        }
        return valutazione;
    }
    private int tonicaFinale(String[] individuo)
    {
        //Se l'ultimo accordo è la tonica, +5 punti
        int valutazione = 0;
        if(individuo[12].toLowerCase().toCharArray()[0] == MusicLibrary.tonic)
        {
            valutazione = 5;
        }
        return valutazione;
    }

    /*
    //Template per le valutazioni
    private int valutazione6(String[] individuo)
    {
        int valutazione = 0;
        return valutazione;
    }
    */
    public void setStartingStrings(String [] startingStrings) {
        this.startingStrings = startingStrings;
    }
    public String[] getStartingStrings() {
        return startingStrings;
    }
}
