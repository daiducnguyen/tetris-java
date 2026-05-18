package tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

public class GameEngine {
    private final Grid grid;
    private Tetromino currentPiece;
    private final Timer gameTimer;
    private final Random random;
    private GamePanel panel;

    private int score;
    private GameState state;


    public GameEngine() {
        this.grid = new Grid();
        this.random = new Random();
        this.score = 0;
        this.state = GameState.WAITING;

        this.gameTimer = new Timer(600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
    }

    public void setPanel(GamePanel panel) {
        this.panel = panel;
    }

    public void start() {
        if (state != GameState.WAITING) return;
        state = GameState.PLAYING;
        spawnNewPiece();
        gameTimer.start();
        if (panel != null) panel.repaint();
    }

    public void reset() {
        gameTimer.stop();
        grid.clearAll();
        score = 0;
        currentPiece = null;
        state = GameState.WAITING;
        if (panel != null) panel.repaint();
    }

    public void pause() {
        gameTimer.stop();
    }

    private void updateGame() {
        if (state != GameState.PLAYING) {
            return;
        }
        movePieceDown();
        if (panel != null) {
            panel.repaint();
        }
    }

    private void spawnNewPiece() {
        ShapeType[] shapes = ShapeType.values();
        ShapeType randomShape = shapes[random.nextInt(shapes.length)];
        currentPiece = new Tetromino(randomShape);

        if (!isValidPosition()) {
            state = GameState.GAME_OVER;
            gameTimer.stop();
        }
    }

    public void movePieceDown() {
        if (state != GameState.PLAYING) return;

        currentPiece.moveDown();

        if (!isValidPosition()) {
            currentPiece.moveUp(); 
            lockCurrentPiece();     
        }
    }

    public void movePieceLeft() {
        if (state != GameState.PLAYING) return;

        currentPiece.moveLeft();
        if (!isValidPosition()) {
            currentPiece.moveRight(); 
        }
    }

    public void movePieceRight() {
        if (state != GameState.PLAYING) return;

        currentPiece.moveRight();
        if (!isValidPosition()) {
            currentPiece.moveLeft(); 
        }
    }

    public void rotatePiece() {
        if (state != GameState.PLAYING) return;

        currentPiece.rotateClockwise();
        if (!isValidPosition()) {
            currentPiece.rotateCounterClockwise();
        }
    }

    public void hardDrop() {
        if (state != GameState.PLAYING) return;

        while (isValidPosition()) {
            currentPiece.moveDown();
        }
        currentPiece.moveUp(); 
        lockCurrentPiece();
    }

    private boolean isValidPosition() {
        int[][] absoluteCoords = currentPiece.getAbsoluteCoordinates();

        for (int i = 0; i < 4; i++) {
            int r = absoluteCoords[i][0];
            int c = absoluteCoords[i][1];


            if (c < 0 || c >= grid.getCols() || r >= grid.getRows()) {
                return false;
            }

            if (r >= 0 && grid.getCell(r, c) != null) {
                return false;
            }
        }
        return true;
    }

    private void lockCurrentPiece() {
        int[][] absoluteCoords = currentPiece.getAbsoluteCoordinates();
        Color pieceColor = currentPiece.getShapeType().getColor();

        for (int i = 0; i < 4; i++) {
            int r = absoluteCoords[i][0];
            int c = absoluteCoords[i][1];
            
            if (grid.isValidCoordinate(r, c)) {
                Block block = new Block(r, c, pieceColor);
                grid.lockBlock(r, c, block);
            }
        }

        int clearedLines = grid.checkAndClearLines();
        if (clearedLines > 0) {
            calculateScore(clearedLines);
        }

        spawnNewPiece();
    }

    private void calculateScore(int lines) {
        switch (lines) {
            case 1 -> score += 100;
            case 2 -> score += 300;
            case 3 -> score += 500;
            case 4 -> score += 800; 
        }
    }

    public Grid getGrid() { return grid; }
    public Tetromino getCurrentPiece() { return currentPiece; }
    public int getScore() { return score; }
    public GameState getState() { return state; }
    public boolean isGameOver() { return state == GameState.GAME_OVER; }
}

