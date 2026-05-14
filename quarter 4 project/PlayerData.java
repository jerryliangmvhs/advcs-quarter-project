import java.io.Serializable;

public class PlayerData implements Serializable {
    private int row;
    private int col;
    private int prevRow;
    private int prevCol;
    private boolean isReady;

    public PlayerData(int row, int col, boolean isReady){
        this.row = row;
        this.col = col;
        prevRow = row;
        prevCol = col;
        this.isReady = isReady;
    }
    public int getRow(){
        return row;
    }
    public int getPrevRow(){
        return prevRow;
    }
    public int getCol(){
        return col;
    }
    public int getPrevCol(){
        return prevCol;
    }
    public void setReady(boolean isReady){
        this.isReady = isReady;
    }
    public boolean isReady(){
        return isReady;
    }
    public void moveRight(){
        if(col<23){
            prevRow = row;
            prevCol = col;
            col++;
        }
    }
    public void moveLeft(){
        if(col>0){
            prevRow = row;
            prevCol = col;
            col--;
        }
    }
    public void moveUp(){
        if(row>0){
            prevCol = col;
            prevRow = row;
            row--;
        }
    }
    public void moveDown(){
        if(row<17){
            prevCol = col;
            prevRow = row;
            row++;
        }
    }
}
