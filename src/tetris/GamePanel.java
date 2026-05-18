package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private final GameEngine engine;

    public GamePanel(GameEngine engine) {
        this.engine = engine;
        this.setBackground(Color.BLACK);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Grid grid = engine.getGrid();
        int rows = grid.getRows();
        int cols = grid.getCols();

        int cellSize = Math.min(getWidth() / cols, getHeight() / rows);

        int xOffset = (getWidth() - (cols * cellSize)) / 2;
        int yOffset = (getHeight() - (rows * cellSize)) / 2;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = xOffset + (c * cellSize);
                int y = yOffset + (r * cellSize);

                Block block = grid.getCell(r, c);

                if (block != null) {
                    g.setColor(block.getColor());
                    g.fillRect(x, y, cellSize, cellSize);
                    
                    g.setColor(block.getColor().darker());
                    g.drawRect(x, y, cellSize - 1, cellSize - 1);
                } else {
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x, y, cellSize, cellSize);
                }
            }
        }

        if (!engine.isGameOver()) {
            Tetromino currentPiece = engine.getCurrentPiece();
            Color pieceColor = currentPiece.getShapeType().getColor();
            int[][] absoluteCoords = currentPiece.getAbsoluteCoordinates();

            g.setColor(pieceColor);
            for (int i = 0; i < 4; i++) {
                int r = absoluteCoords[i][0];
                int c = absoluteCoords[i][1];

                if (r >= 0) {
                    int x = xOffset + (c * cellSize);
                    int y = yOffset + (r * cellSize);

                    g.fillRect(x, y, cellSize, cellSize);
                    
                    g.setColor(pieceColor.darker());
                    g.drawRect(x, y, cellSize - 1, cellSize - 1);
                    g.setColor(pieceColor); 
                }
            }
        } else {
            g.setColor(new Color(0, 0, 0, 180)); 
            g.fillRect(xOffset, yOffset, cols * cellSize, rows * cellSize);

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            
            String message = "GAME OVER";
            FontMetrics metrics = g.getFontMetrics();
            
            int xMsg = xOffset + (cols * cellSize - metrics.stringWidth(message)) / 2;
            int yMsg = yOffset + ((rows * cellSize - metrics.getHeight()) / 2) + metrics.getAscent();
            
            g.drawString(message, xMsg, yMsg);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Score: " + engine.getScore(), 15, 25);
    }
}

