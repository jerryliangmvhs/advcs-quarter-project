import java.io.Serializable;

public class PlayerData implements Serializable {
    private int row;
    private int col;
    private int prevRow;
    private int prevCol;
    private boolean isReady;
    private int score;
    //visible = alive, not visible = dead
    private boolean visible;

    public PlayerData(int row, int col, boolean isReady){
        this.row = row;
        this.col = col;
        prevRow = row;
        prevCol = col;
        this.isReady = isReady;
        score = 0;
        visible = true;
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
    public int getScore(){
        return score;
    }
    public void setReady(boolean isReady){
        this.isReady = isReady;
    }
    public boolean isReady(){
        return isReady;
    }
    public void moveRight(){
        if(col<23 && visible){
            prevRow = row;
            prevCol = col;
            col++;
        }
    }
    public void moveLeft(){
        if(col>0 && visible){
            prevRow = row;
            prevCol = col;
            col--;
        }
    }
    public void moveUp(){
        if(row>0 && visible){
            prevCol = col;
            prevRow = row;
            row--;
        }
    }
    public void moveDown(){
        if(row<17 && visible){
            prevCol = col;
            prevRow = row;
            row++;
        }
    }
    public void increaseScore(){
        score++;
    }
    public void setVisible(boolean visible){
        this.visible = visible;
    }
    public boolean isVisible(){
        return visible;
    }
}
