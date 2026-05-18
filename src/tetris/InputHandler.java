package tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {
    private final GameEngine engine;

    public InputHandler(GameEngine engine) {
        this.engine = engine;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (engine.getState() != GameState.PLAYING) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT ->  engine.movePieceLeft();
            case KeyEvent.VK_RIGHT -> engine.movePieceRight();
            case KeyEvent.VK_DOWN ->  engine.movePieceDown();
            case KeyEvent.VK_UP ->    engine.rotatePiece();
            case KeyEvent.VK_SPACE -> engine.hardDrop();
        }
    }
}

