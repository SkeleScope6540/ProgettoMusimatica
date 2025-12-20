package CellularAutomaton;

import org.jfugue.player.Player;
import GraphicalUtilities.GridMapVisualizer;

import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class CellularAutomaton {
    int COLORS;
    int PROGRESSIONTHRESHOLD;
    int DIMENSION;
    int TOLLERANCE = 0;
    int millisecondi = 500;
    //L'iterazione corrente
    int iteration = 0;
    int maxIterations = 100000;
    int[][] statoAttuale;
    int [][] mappaVariazioni = new int[DIMENSION][DIMENSION];
    String[] notes = {"C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5", "D5", "D5", "C5", "B4", "A4", "G4", "F4", "E4", "D4", "C4"};
    String[] durations = {"w", "h", "q", "i", "s", "t"};
    String melodia = "";
    String medie = "";

    public CellularAutomaton(int d, int c, int t) {
        setDimension(d);
        setThreshold(t);
        setColors(c);
    }

    public void Start() {
        Random rand = new Random();
        statoAttuale = GenerateRandomState(false);
        mappaVariazioni = SetStateToNull();

        //Frame per disegnare l'automa
        JFrame frame = new JFrame("Ciclic cellular automaton");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        //Frame per disegnare la mappa con le variazioni
        JFrame varFrame = new JFrame("Variations in the automaton");
        varFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        varFrame.setSize(600, 600);

        // Visualizzatore automa
        GridMapVisualizer visualizer = new GridMapVisualizer(statoAttuale, false);
        frame.add(visualizer);
        frame.setVisible(true);

        // Visualizzatore variazioni
        GridMapVisualizer varVisualizer = new GridMapVisualizer(mappaVariazioni, true);
        varFrame.add(varVisualizer);
        varFrame.setVisible(true);

        //Questo è un fattore nella melodia trovata
        int controllo = 46;
        int mediaZona;
        int dimKernel = 3;
        int selPixelX = rand.nextInt(1, DIMENSION - dimKernel);
        int selPixelY = rand.nextInt(1, DIMENSION  - dimKernel);

        while(iteration < maxIterations)
        {
            NextState();
            if(millisecondi != 0) {
                try {
                    Thread.sleep(millisecondi); // Pausa di tot millisecondi
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            visualizer.setGrid(statoAttuale);
            varVisualizer.setGrid(mappaVariazioni);
            iteration++;
            //Mutazione
            if(iteration % 5  == 0)
                statoAttuale[rand.nextInt(DIMENSION)][rand.nextInt(DIMENSION)] = rand.nextInt(COLORS);
            System.out.println("Iteration: " + iteration);
            if(iteration % controllo == 0)
            {
                controllo *= 1;

                mediaZona = KernelMedia(selPixelX, selPixelY, dimKernel);

                medie += " " + mediaZona;
                melodia += " " + notes[mediaZona] + "i";
            }
        }

        System.out.println("Medie: " + medie);
        System.out.println("Melodia: " + melodia);
        Player player = new Player();
        player.play(melodia);
    }

    public void NextState(){
        int progValTemp = 0;
        int regValTemp = 0;
        int progTreshTemp = 0;
        int regTreshTemp = 0;
        int [][] statoFuturo = new int[DIMENSION][DIMENSION];
        for(int j = 0; j < statoAttuale.length; j++){
            for(int k = 0; k < statoAttuale[j].length; k++){
                //Se il valore è massimo, fa il giro e torna a 0
                    if (statoAttuale[j][k] == COLORS - 1) progValTemp = 0;
                    else progValTemp = statoAttuale[j][k] + 1;

                    //Check progressione
                    if (j < DIMENSION - 1)
                        if (statoAttuale[j + 1][k] == progValTemp)
                            progTreshTemp++;
                    if (j > 0)
                        if (statoAttuale[j - 1][k] == progValTemp)
                            progTreshTemp++;
                    if (j < DIMENSION - 1 && k > 0)
                        if (statoAttuale[j + 1][k - 1] == progValTemp)
                            progTreshTemp++;
                    if (j < DIMENSION - 1 && k < DIMENSION - 1)
                        if (statoAttuale[j + 1][k + 1] == progValTemp)
                            progTreshTemp++;
                    if (j > 0 && k > 0)
                        if (statoAttuale[j - 1][k - 1] == progValTemp)
                            progTreshTemp++;
                    if (j > 0 && k < DIMENSION - 1)
                        if (statoAttuale[j - 1][k + 1] == progValTemp)
                            progTreshTemp++;
                    if (k < DIMENSION - 1)
                        if (statoAttuale[j][k + 1] == progValTemp)
                            progTreshTemp++;
                    if (k > 0)
                        if (statoAttuale[j][k - 1] == progValTemp)
                            progTreshTemp++;

                if(progTreshTemp >= PROGRESSIONTHRESHOLD)
                {
                    statoFuturo[j][k] = progValTemp;
                    if(mappaVariazioni[j][k] < 2) mappaVariazioni[j][k]+=2;
                }
                else {
                    statoFuturo[j][k] = statoAttuale[j][k];
                    if(mappaVariazioni[j][k] != 0)   mappaVariazioni[j][k]--;
                }
                progTreshTemp = 0;
                regTreshTemp = 0;
            }
        }
        statoAttuale = statoFuturo;
    }

    public int KernelMedia(int startx, int starty, int dimensions)
    {
        int tot = 0;
        int validElements = 0;
        //Questo è un kernel 3x3 che studia la media
        //Proposte:
        //1. Cambiare le dimensioni
        //2. Provare media pesata
        //3. Ottimizzare il kernel e cambiare parametri via ML per trovare melodie valide
        //4. Studiare il procedimento e provare a ricavare l'operazione inversa

        // Con threshold = 1
        //TODO: implementare un algoritmo genetico che giochi sulle variabili controllo, colori e dimensioni griglia[?]

        // Con threshold = 2
        //TODO: studiare il comportamento delle aree a bassa entropia (quelle che cambiano molto) e interpretare il risultato

        for(int i = startx; i < startx + dimensions; i++)
        {
                for(int  j = starty; j < starty + dimensions; j++)
                {
                    if(i < DIMENSION && j < DIMENSION)
                    {
                        tot += statoAttuale[i][j];
                        validElements++;
                    }
                }
        }

        return tot/validElements;
    }

    public int GlobalMedia()
    {
        int i = 0;
        for(int j = 0; j < statoAttuale.length; j++){
            for(int k = 0; k < statoAttuale[j].length; k++){
                i += statoAttuale[j][k];
            }
        }
        return i / statoAttuale.length;
    }

    public void setThreshold(int t) {
        PROGRESSIONTHRESHOLD = t;
    }

    public void setColors(int c) {
        COLORS = c;
    }

    public void setDimension(int d){
        DIMENSION = d;
    }

    public void setCooldownBetweenStates(int a)
    {
        millisecondi = a;
    }

    public int [][] GenerateRandomState(boolean option){
        int [][] state = new int[DIMENSION][DIMENSION];

        for(int i = 0; i < DIMENSION; i++){
            for(int j = 0; j < DIMENSION; j++){
                state[i][j] = new Random().nextInt(COLORS);
            }
        }

        //Se true, crea una zona di partenza di un colore random al centro
        if(option) {
            int col = new Random().nextInt(COLORS);

            for (int i = DIMENSION / 2 - COLORS; i < DIMENSION / 2 + COLORS; i++) {
                for (int j = DIMENSION / 2 - COLORS; j < DIMENSION / 2 + COLORS; j++) {
                    state[i][j] = col;
                }
            }
        }
        return state;
    }

    public int [][] SetStateToNull()
    {
        int [][] zeroState = new int[DIMENSION][DIMENSION];

        for(int i = 0; i < DIMENSION; i++){
            for(int j = 0; j < DIMENSION; j++){
                zeroState[i][j] = 0;
            }
        }

        return zeroState;
    }
}