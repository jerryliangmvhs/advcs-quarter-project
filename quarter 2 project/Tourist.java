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
    private MyHashTable<Location, GridObject> map;
    private DLList<GridObject> gridBucket;
    public Tourist(int row, int col, MyHashTable<Location, GridObject> map){
        this.row = row;
        this.col = col;
        this.map = map;
        width = 7;
        height = 7;
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
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void moveLeft(){
        gridBucket = map.get(new Location(row,col-1));
        if(!gridBucket.get(0).getName().equals("water") && gridBucket.size()==1){
            col--;
            x = col*7;
        }
        else{
            errorSound();
        }
    }
    public void moveRight(){
        gridBucket = map.get(new Location(row,col+1));
        if(!gridBucket.get(0).getName().equals("water") && gridBucket.size()==1){
            col++;
            x = col*7;
        }
        else{
            errorSound();
        }
    }
    public void moveUp(){
        gridBucket = map.get(new Location(row-1,col));
        if(!gridBucket.get(0).getName().equals("water") && gridBucket.size()==1){
            row--;
            y = row*7;
        }
        else{
            errorSound();
        }
    }
    public void moveDown(){
        gridBucket = map.get(new Location(row+1,col));
        if(!gridBucket.get(0).getName().equals("water") && gridBucket.size()==1){
            row++;
            y = row*7;
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
