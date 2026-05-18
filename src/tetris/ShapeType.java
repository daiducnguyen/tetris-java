package tetris;
import java.awt.Color;

public enum ShapeType {
    I(Color.CYAN,        new int[][]{{0, -1}, {0, 0}, {0, 1}, {0, 2}}),
    J(Color.BLUE,        new int[][]{{-1, -1}, {0, -1}, {0, 0}, {0, 1}}),
    L(Color.ORANGE,      new int[][]{{0, -1}, {0, 0}, {0, 1}, {-1, 1}}),
    O(Color.YELLOW,      new int[][]{{-1, 0}, {-1, 1}, {0, 0}, {0, 1}}),
    S(Color.GREEN,       new int[][]{{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}),
    T(Color.MAGENTA,     new int[][]{{0, -1}, {0, 0}, {0, 1}, {-1, 0}}),
    Z(Color.RED,         new int[][]{{-1, -1}, {-1, 0}, {0, 0}, {0, 1}});

    private final Color color;
    private final int[][] relativeCoordinates;

    ShapeType(Color color, int[][] relativeCoordinates) {
        this.color = color;
        this.relativeCoordinates = relativeCoordinates;
    }

    public Color getColor() {
        return color;
    }

    public int[][] getRelativeCoordinates() {
        int[][] copy = new int[4][2];
        for (int i = 0; i < 4; i++) {
            copy[i][0] = relativeCoordinates[i][0]; 
            copy[i][1] = relativeCoordinates[i][1]; 
        }
        return copy;
    }
}

