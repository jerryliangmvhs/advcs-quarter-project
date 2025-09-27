import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Farmer {
    private int x;
    private int y;
    private BufferedImage farmer;
    public Farmer(BufferedImage farmer, int x, int y){
        this.x = x;
        this.y = y;
        this.farmer = farmer;
    }
    public void drawMe(Graphics g){
        g.drawImage(farmer,x,y,null);
    }
    //for troubleshooting purposes
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
