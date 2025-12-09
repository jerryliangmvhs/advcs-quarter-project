public class LandAnimal implements Runnable{
    private int x;
    private int y;
    private int row;
    private int col;
    private int size;
    private Screen sc;
    private MyHashTable<Location,GridObject> map;
    private DLList<GridObject> gridBucket;
    public LandAnimal(int row, int col, int size, MyHashTable<Location,GridObject> map, Screen sc){
        this.row = row;
        this.col = col;
        this.size = size;
        this.map = map;
        this.sc = sc;
        x = col*size;
        y = row*size;
    }
    public int getRow(){
        return row;
    }
    public void setRow(int row){
        this.row = row;
        y = row*size;
    }
    public int getCol(){
        return col;
    }
    public void setCol(int col){
        this.col = col;
        x = col*size;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
        x = col*size;
        y = row*size;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    @SuppressWarnings("unchecked")
    public boolean canMoveLeft(){
        gridBucket = map.get(new Location(row,col-1,size));
        if(gridBucket.size()==1 && !gridBucket.get(0).getName().equals("water") && !gridBucket.get(0).getName().equals("road") && !gridBucket.get(0).getName().equals("road2")){
            return true;
        }
        return false;
    }
     @SuppressWarnings("unchecked")
    public boolean canMoveRight(){
        gridBucket = map.get(new Location(row,col+1,size));
        if(gridBucket.size()==1 && !gridBucket.get(0).getName().equals("water") && !gridBucket.get(0).getName().equals("road") && !gridBucket.get(0).getName().equals("road2")){
            return true;
        }
        return false;
    }
     @SuppressWarnings("unchecked")
    public boolean canMoveUp(){
        gridBucket = map.get(new Location(row-1,col,size) );
        if(gridBucket.size()==1 && !gridBucket.get(0).getName().equals("water") && !gridBucket.get(0).getName().equals("road") && !gridBucket.get(0).getName().equals("road2")){
            return true;
        }
        return false;
    }
     @SuppressWarnings("unchecked")
    public boolean canMoveDown(){
        gridBucket = map.get(new Location(row+1,col,size));
        if(gridBucket.size()==1 && !gridBucket.get(0).getName().equals("water") && !gridBucket.get(0).getName().equals("road") && !gridBucket.get(0).getName().equals("road2")){
            return true;
        }
        return false;
    }
    @Override
    public void run(){
        while(true){
            int randInt = (int)(Math.random()*4);
            if(randInt==0 && canMoveUp()){
                row--;
                y = row*size;
            }
            if(randInt==1 && canMoveLeft()){
                col--;
                x = col*size;
            }
            if(randInt==2 && canMoveDown()){
                row++;
                y = row*size;
            }
            if(randInt==3 && canMoveRight()){
                col++;
                x = col*size;
            }
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
            sc.repaint();
        }
    }
}
