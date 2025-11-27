import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


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
    public void playSound() {


        try {
            URL url = this.getClass().getClassLoader().getResource("sounds/cow.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

}
