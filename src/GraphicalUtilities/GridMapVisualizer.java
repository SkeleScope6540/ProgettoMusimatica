package GraphicalUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GridMapVisualizer extends JPanel {

    // La tua matrice di dati
    private int[][] grid;
    // Mappatura dei colori per i valori da 0 a 9
    private final Color[] colorMap;

    public GridMapVisualizer(int[][] grid, boolean selectForVariations) {
        this.grid = grid;

        if(!selectForVariations) {
            // Definiamo 10 colori distinti per i valori 0-9
            this.colorMap = new Color[]{
                    Color.ORANGE,     // 0
                    Color.RED,        // 1
                    Color.pink,       // 2
                    Color.magenta,    // 3
                    Color.BLUE,       // 4
                    Color.CYAN,       // 5
                    Color.GREEN,      // 6
                    Color.YELLOW,     // 7
                    Color.WHITE,      // 8
                    Color.LIGHT_GRAY, // 9
                    Color.GRAY,       // 10
                    Color.BLACK,      // 11
                    Color.ORANGE,     // 12
                    Color.RED,        // 13
                    Color.pink,       // 14
                    Color.magenta,    // 15
                    Color.BLUE,       // 16
                    Color.CYAN,       // 17
                    Color.GREEN,      // 18
                    Color.YELLOW,     // 19
                    Color.WHITE,      // 20
                    Color.LIGHT_GRAY, // 21
                    Color.GRAY,       // 22
                    Color.BLACK,      // 23
            };
        }
        else  {
            this.colorMap = new Color[]{
                    Color.gray,     // 0
                    Color.red,        // 1
                    Color.yellow,       // 2
                    Color.green,    // 3
                    Color.magenta,       // 4
            };
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Controlli di sicurezza
        if (grid == null || grid.length == 0) return;

        int rows = grid.length;
        int cols = grid[0].length;

        // Calcoliamo la dimensione di ogni quadratino in base alla dimensione della finestra
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int value = grid[r][c];

                // Assicuriamoci che il valore sia tra 0 e 9 per evitare crash
                if (value >= 0 && value < colorMap.length) {
                    g.setColor(colorMap[value]);
                } else {
                    g.setColor(Color.LIGHT_GRAY); // Colore di fallback
                }

                // Disegna il rettangolo pieno
                g.fillRect(c * cellWidth, r * cellHeight, cellWidth, cellHeight);

                // Opzionale: disegna il bordo nero per separare i quadratini
                g.setColor(Color.BLACK);
                g.drawRect(c * cellWidth, r * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    // Metodo per aggiornare la griglia dall'esterno
    public void setGrid(int[][] newGrid) {
        this.grid = newGrid;
        this.repaint();
    }
}