package tetris;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private final GameEngine engine;
    private Rectangle buttonBounds;

    public GamePanel(GameEngine engine) {
        this.engine = engine;
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (buttonBounds != null && buttonBounds.contains(e.getPoint())) {
                    GameState state = engine.getState();
                    if (state == GameState.WAITING) {
                        engine.start();
                    } else if (state == GameState.GAME_OVER) {
                        engine.reset();
                        engine.start();
                    }
                    requestFocusInWindow();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (buttonBounds != null && buttonBounds.contains(e.getPoint())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
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

        GameState state = engine.getState();

        if (state == GameState.PLAYING) {
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
            buttonBounds = null;
        } else {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(xOffset, yOffset, cols * cellSize, rows * cellSize);

            int centerX = xOffset + (cols * cellSize) / 2;
            int centerY = yOffset + (rows * cellSize) / 2;

            if (state == GameState.GAME_OVER) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                String message = "GAME OVER";
                FontMetrics metrics = g.getFontMetrics();
                int xMsg = centerX - metrics.stringWidth(message) / 2;
                g.drawString(message, xMsg, centerY - 40);
            }

            String buttonLabel = (state == GameState.WAITING) ? "START" : "REPLAY";
            g.setFont(new Font("Arial", Font.BOLD, 18));
            FontMetrics btnMetrics = g.getFontMetrics();
            int btnW = btnMetrics.stringWidth(buttonLabel) + 40;
            int btnH = btnMetrics.getHeight() + 16;
            int btnX = centerX - btnW / 2;
            int btnY = centerY - btnH / 2 + (state == GameState.GAME_OVER ? 10 : 0);

            g.setColor(new Color(50, 180, 50));
            g.fillRoundRect(btnX, btnY, btnW, btnH, 10, 10);
            g.setColor(new Color(30, 140, 30));
            g2.drawRoundRect(btnX, btnY, btnW, btnH, 10, 10);

            g.setColor(Color.WHITE);
            int textX = centerX - btnMetrics.stringWidth(buttonLabel) / 2;
            int textY = btnY + ((btnH - btnMetrics.getHeight()) / 2) + btnMetrics.getAscent();
            g.drawString(buttonLabel, textX, textY);

            buttonBounds = new Rectangle(btnX, btnY, btnW, btnH);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Score: " + engine.getScore(), 15, 25);
    }
}

