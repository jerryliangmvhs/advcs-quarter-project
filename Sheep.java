import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Sheep {
    private int x;
    private int y;
    private BufferedImage sheep;
    public Sheep(BufferedImage sheep, int x, int y){
        this.x = x;
        this.y = y;
        this.sheep = sheep;
    }
    public void drawMe(Graphics g){
        g.drawImage(sheep,x,y,null);
    }
    //for troubleshooting purposes
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
