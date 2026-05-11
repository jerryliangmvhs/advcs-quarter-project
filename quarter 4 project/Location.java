import java.io.Serializable;

public class Location implements Serializable {
    private int row;
    private int col;
    //one tile is 50x50px
    //map is 24x18 tiles
    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }
    @Override
    public int hashCode(){
        return (row*24)+col;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
}
