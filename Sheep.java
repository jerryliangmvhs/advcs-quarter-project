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
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public boolean mouseEvent(int mouseX, int mouseY){
        int imageWidth = 209;
        int imageHeight = 171;
        if(mouseX >= x && mouseX<= x+imageWidth && mouseY >= y && mouseY <= y + imageHeight){
            return true;
        }
        return false;
    }

}
