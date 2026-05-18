package tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class GameEngine {
    private final Grid grid;
    private Tetromino currentPiece;
    private final Timer gameTimer;
    private final TetrominoFactory factory;
    private final List<GameListener> listeners;

    private int score;
    private GameState state;


    public GameEngine() {
        this.grid = new Grid();
        this.factory = new TetrominoFactory();
        this.listeners = new ArrayList<>();
        this.score = 0;
        this.state = GameState.WAITING;

        this.gameTimer = new Timer(600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
    }

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (GameListener listener : listeners) {
            listener.onGameUpdated();
        }
    }

    public void start() {
        if (state != GameState.WAITING) return;
        state = GameState.PLAYING;
        spawnNewPiece();
        gameTimer.start();
        notifyListeners();
    }

    public void reset() {
        gameTimer.stop();
        grid.clearAll();
        score = 0;
        currentPiece = null;
        state = GameState.WAITING;
        notifyListeners();
    }

    public void pause() {
        gameTimer.stop();
    }

    private void updateGame() {
        if (state != GameState.PLAYING) {
            return;
        }
        movePieceDown();
    }

    private void spawnNewPiece() {
        currentPiece = factory.createRandom();

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
        notifyListeners();
    }

    public void movePieceLeft() {
        if (state != GameState.PLAYING) return;

        currentPiece.moveLeft();
        if (!isValidPosition()) {
            currentPiece.moveRight();
        }
        notifyListeners();
    }

    public void movePieceRight() {
        if (state != GameState.PLAYING) return;

        currentPiece.moveRight();
        if (!isValidPosition()) {
            currentPiece.moveLeft();
        }
        notifyListeners();
    }

    public void rotatePiece() {
        if (state != GameState.PLAYING) return;

        currentPiece.rotateClockwise();
        if (!isValidPosition()) {
            currentPiece.rotateCounterClockwise();
        }
        notifyListeners();
    }

    public void hardDrop() {
        if (state != GameState.PLAYING) return;

        while (isValidPosition()) {
            currentPiece.moveDown();
        }
        currentPiece.moveUp();
        lockCurrentPiece();
        notifyListeners();
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

