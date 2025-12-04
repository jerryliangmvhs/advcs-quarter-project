import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Tourist implements Runnable {
    private int row;
    private int col;
    private int x;
    private int y;
    private int size;
    private Screen sc;
    private MyHashTable<Location, GridObject> map;
    private DLList<GridObject> gridBucket;
    public Tourist(int row, int col, int x, int y, int size, MyHashTable<Location, GridObject> map, Screen sc){
        this.row = row;
        this.col = col;
        this.map = map;
        this.size = size;
        this.sc = sc;
        this.x = x;
        this.y = y;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return size;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        this.col = col;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean canMoveLeft(){
        gridBucket = map.get(new Location(row,col-1,size));
        if(gridBucket.size()==1 && !gridBucket.get(0).getName().equals("water")){
            return true;
        }
        errorSound();
        return false;
    }
    public boolean canMoveRight(){
        gridBucket = map.get(new Location(row,col+1,size));
        if(gridBucket.size()==1 && !gridBucket.get(0).getName().equals("water")){
            return true;
        }
        errorSound();
        return false;
    }
    public boolean canMoveUp(){
        gridBucket = map.get(new Location(row-1,col,size));
        if(gridBucket.size()==1 && !gridBucket.get(0).getName().equals("water")){
            return true;
        }
        errorSound();
        return false;
    }
    public boolean canMoveDown(){
        gridBucket = map.get(new Location(row+1,col,size));
        if(gridBucket.size()==1 && !gridBucket.get(0).getName().equals("water")){
            return true;
        }
        errorSound();
        return false;
    }
    public void errorSound(){
        //System.out.println("Player tried to move into obstacle");
        try {
            URL url = this.getClass().getClassLoader().getResource("error.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    @Override
    public void run(){
        int counter = 0;
        while(true){
            counter++;
            if(counter%2==0){
                y+=(size/20);
            }
            else if(counter%2==1){
                y-=(size/20);
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                // TODO: handle exception
            }
            sc.repaint();
        }
    }
         


}
