import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ClockButton {
    private int x;
    private int y;
    private int size;
    private Color buttonColor;
    private BufferedImage image;

    public ClockButton(int x, int y, int size, Color buttonColor){
        this.x = x;
        this.y = y;
        this.size = size;
        this.buttonColor = buttonColor;
        try {
            image = ImageIO.read(new File("icons/clock-icon.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void setColor(Color buttonColor){
        this.buttonColor = buttonColor;
    }
    public void drawMe(Graphics g){
        g.setColor(buttonColor);
        g.fillRect(x,y,size,size);
        g.drawImage(image,x,y,size,size,null);
    }
    public boolean mouseClicked(int mouseX, int mouseY){
        if(mouseX>=x && mouseX <=x+size && mouseY>=y && mouseY<=y+size ){
            return true;
        }
        return false;
    }
}
