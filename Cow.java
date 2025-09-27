import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Cow {
    private int x;
    private int y;
    private BufferedImage cow;
    public Cow(BufferedImage cow, int x, int y){
        this.x = x;
        this.y = y;
        this.cow = cow;
    }
    public void drawMe(Graphics g){
        g.drawImage(cow,x,y,null);
    }
    //for troubleshooting purposes
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
