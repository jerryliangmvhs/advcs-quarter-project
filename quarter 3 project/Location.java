import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Location{
    private int x;
    private int y;
    private String name;
    private String abbreviation;
    public Location(String name, String abbreviation, int x, int y){
        this.x = x;
        this.y = y;
        this.name = name;
        this.abbreviation = abbreviation;
    }
    public void drawMe(Graphics g){
        g.setColor(Color.RED);
        g.drawOval(x-10,y-10,20,20);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public String getAbbreviation(){
        return abbreviation;
    }
    public String getName(){
        return name;
    }
    @Override
    public int hashCode(){
        int value = 0;
        for(int i=0; i<abbreviation.length(); i++){
            value += (((int)abbreviation.charAt(i))*(26^abbreviation.length()-i-1));
        }
        return value;
    }
    public boolean equals(Location other){
        if(abbreviation.equals(other.getAbbreviation())){
            return true;
        }
        return false;
    }
}
