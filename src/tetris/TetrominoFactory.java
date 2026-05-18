package tetris;

import java.util.Random;

public class TetrominoFactory {
    private final Random random;

    public TetrominoFactory() {
        this.random = new Random();
    }

    public Tetromino createRandom() {
        ShapeType[] shapes = ShapeType.values();
        ShapeType shape = shapes[random.nextInt(shapes.length)];
        return new Tetromino(shape);
    }
}
