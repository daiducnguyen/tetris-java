package tetris;

public class Grid {

    private final int rows = 20;
    private final int cols = 10;
    private final Block[][] grid;

    public Grid() {
        grid = new Block[rows][cols];
        clearAll();
    }

    public void clearAll() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = null;
            }
        }
    }

    public boolean isRowFull(int row) {
        for (int c = 0; c < cols; c++) {
            if (grid[row][c] == null) {
                return false;
            }
        }
        return true;
    }

    public void clearAndShiftRow(int rowToClear) {
        for (int r = rowToClear; r > 0; r--) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = grid[r - 1][c];
            }
        }
        for (int c = 0; c < cols; c++) {
            grid[0][c] = null;
        }
    }

    public int checkAndClearLines() {
        int linesCleared = 0;
        for (int r = rows - 1; r >= 0; r--) {
            if (isRowFull(r)) {
                clearAndShiftRow(r);
                linesCleared++;
                r++;
            }
        }
        return linesCleared;
    }

    public void lockBlock(int row, int col, Block block) {
        if (isValidCoordinate(row, col)) {
            grid[row][col] = block;
        }
    }

    public Block getCell(int row, int col) {
        if (isValidCoordinate(row, col)) {
            return grid[row][col];
        }
        return null; 
    }

    public boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
}


