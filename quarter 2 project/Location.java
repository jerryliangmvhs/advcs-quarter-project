public class Location {
    private int row;
    private int col;
    private int x;
    private int y;
    private int size;
    public Location(int row, int col, int size){
        this.row = row;
        this.col = col;
        this.size = size;
        x = col*size;
        y = row*size;
    }
    @Override
    public int hashCode(){
        return (row*101)+col;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
