import java.awt.*;
import java.awt.image.BufferedImage;

public class LandmarkInfo {
    private BufferedImage photo;

    public LandmarkInfo(BufferedImage photo){
        this.photo = photo;
    
    }
    public void drawMe(Graphics g, int x, int y){
       g.drawImage(photo,x,y,400,300,null);
    }
}
