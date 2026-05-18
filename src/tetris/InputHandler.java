package tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {
    private final GameEngine engine;
    private final GamePanel panel;

    public InputHandler(GameEngine engine, GamePanel panel) {
        this.engine = engine;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (engine.isGameOver()) {
            return;
        }

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT ->  engine.movePieceLeft();
            case KeyEvent.VK_RIGHT -> engine.movePieceRight();
            case KeyEvent.VK_DOWN ->  engine.movePieceDown();
            case KeyEvent.VK_UP ->    engine.rotatePiece();
            case KeyEvent.VK_SPACE -> engine.hardDrop();
            default -> { 
                return; 
            }
        }

        panel.repaint();
    }
}

