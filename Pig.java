import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pig {
    private int x;
    private int y;
    private BufferedImage pig;
    public Pig(BufferedImage pig, int x, int y){
        this.x = x;
        this.y = y;
        this.pig = pig;
    }
    public void drawMe(Graphics g){
        g.drawImage(pig,x,y,null);
    }
    //for troubleshooting purposes
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
