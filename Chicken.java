import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.awt.Graphics;

public class Chicken {
    private int x;
    private int y;
    private BufferedImage chicken;
    public Chicken(BufferedImage chicken, int x, int y){
        this.x = x;
        this.y = y;
        this.chicken = chicken;
    }
    public void drawMe(Graphics g){
        g.drawImage(chicken,x,y,null);
    }
    //for troubleshooting purposes
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
