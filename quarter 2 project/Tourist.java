import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Tourist {
    private int row;
    private int col;
    private int x;
    private int y;
    private int width;
    private int height;
    private int size;
    private MyHashTable<Location, GridObject> map;
    private DLList<GridObject> gridBucket;
    public Tourist(int row, int col, int size, MyHashTable<Location, GridObject> map){
        this.row = row;
        this.col = col;
        this.map = map;
        this.size = size;
        x = col*size;
        y = row*size;
    }
    public int getRow(){
        return row;
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
    public int getCol(){
        return col;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void moveLeft(){
        gridBucket = map.get(new Location(row,col-1,size));
        if(!gridBucket.get(0).getName().equals("water") && gridBucket.size()==1){
            col--;
            x = col*size;
        }
        else{
            errorSound();
        }
    }
    public void moveRight(){
        gridBucket = map.get(new Location(row,col+1,size));
        if(!gridBucket.get(0).getName().equals("water") && gridBucket.size()==1){
            col++;
            x = col*size;
        }
        else{
            errorSound();
        }
    }
    public void moveUp(){
        gridBucket = map.get(new Location(row-1,col,size));
        if(!gridBucket.get(0).getName().equals("water") && gridBucket.size()==1){
            row--;
            y = row*size;
        }
        else{
            errorSound();
        }
    }
    public void moveDown(){
        gridBucket = map.get(new Location(row+1,col,size));
        if(!gridBucket.get(0).getName().equals("water") && gridBucket.size()==1){
            row++;
            y = row*size;
        }
        else{
            errorSound();
        }
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


}
