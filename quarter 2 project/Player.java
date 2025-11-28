public class Player {
    private int row;
    private int col;
    private int x;
    private int y;
    public Player(int row, int col){
        this.row = row;
        this.col = col;
        x = col*7;
        y = row*7;
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
