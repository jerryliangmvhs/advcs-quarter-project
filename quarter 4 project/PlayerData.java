import java.io.Serializable;

public class PlayerData implements Serializable {
    private int row;
    private int col;

    public PlayerData(int row, int col){
        this.row = row;
        this.col = col;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public void moveRight(){
        if(col<23){
            col++;
        }
    }
    public void moveLeft(){
        if(col>0){
            col--;
        }
    }
    public void moveUp(){
        if(row>0){
            row--;
        }
    }
    public void moveDown(){
        if(row<17){
            row++;
        }
    }
}
