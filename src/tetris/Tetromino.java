package tetris;

public class Tetromino {
    private final ShapeType shapeType;
    private int row;
    private int col;
    private int[][] relativeCoords;

    public Tetromino(ShapeType shapeType) {
        this.shapeType = shapeType;
        this.relativeCoords = shapeType.getRelativeCoordinates();
        this.row = 1; 
        this.col = 4;
    }

    public void moveDown() {
        row++;
    }

    public void moveUp() {
        row--;
    }

    public void moveLeft() {
        col--;
    }

    public void moveRight() {
        col++;
    }

    public void rotateClockwise() {
        if (shapeType == ShapeType.O) {
            return;
        }

        int[][] rotatedCoords = new int[4][2];
        for (int i = 0; i < 4; i++) {
            int oldRow = relativeCoords[i][0];
            int oldCol = relativeCoords[i][1];
            
            // Apply rotation math: New Row = Col, New Column = -Row
            rotatedCoords[i][0] = oldCol;
            rotatedCoords[i][1] = -oldRow;
        }
        relativeCoords = rotatedCoords;
    }

    public void rotateCounterClockwise() {
        if (shapeType == ShapeType.O) {
            return;
        }

        int[][] rotatedCoords = new int[4][2];
        for (int i = 0; i < 4; i++) {
            int oldRow = relativeCoords[i][0];
            int oldCol = relativeCoords[i][1];
            
            rotatedCoords[i][0] = -oldCol;
            rotatedCoords[i][1] = oldRow;
        }
        relativeCoords = rotatedCoords;
    }

    public int[][] getAbsoluteCoordinates() {
        int[][] absoluteCoords = new int[4][2];
        for (int i = 0; i < 4; i++) {
            absoluteCoords[i][0] = this.row + relativeCoords[i][0]; 
            absoluteCoords[i][1] = this.col + relativeCoords[i][1]; 
        }
        return absoluteCoords;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public int[][] getRelativeCoords() {
        int[][] copy = new int[4][2];
        for (int i = 0; i < 4; i++) {
            copy[i][0] = relativeCoords[i][0];
            copy[i][1] = relativeCoords[i][1];
        }
        return copy;
    }
}

