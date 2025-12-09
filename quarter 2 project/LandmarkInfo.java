import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LandmarkInfo {
    private BufferedImage photo;
    private String name;
    private String caption;
    private int screenWidth = 400;
    private int screenHeight = 300;
    private int imageWidth = screenWidth*(3/4);
    private int imageHeight = imageWidth*(2/3);
    private int imageX = (screenWidth/2) - (imageWidth/2);
    private int imageY = (screenHeight/2) - (imageHeight/2);

    public LandmarkInfo(BufferedImage photo, String name, String caption){
        this.photo = photo;
        this.name = name;
        this.caption = caption;
    }
    public void drawMe(Graphics g, int x, int y){
        g.setColor(Color.BLACK);
        g.fillRect(x,y,screenWidth,screenHeight);
        g.drawImage(photo,x+imageX,y+imageY,imageWidth,imageHeight,null);
        System.out.println("landmark screen drawn");
    }
}
