package tetris;
import java.awt.Color;

public class Block {
  private int row;
  private int col;
  private Color color;

  public Block(int row, int col, Color color){
    this.row = row;
    this.col = col;
    this.color = color;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row){
    this.row = row;
  }

  public int getCol(){
    return col;
  }

  public void  setCol(int col){
    this.col = col;
  }

  public Color getColor() {
        return color;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}