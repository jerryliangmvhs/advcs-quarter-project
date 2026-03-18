import java.awt.*;
import java.awt.image.BufferedImage;

public class Location{
    private int x;
    private int y;
    private String name;
    private String abbreviation;
    private BufferedImage image;
    public Location(String name, String abbreviation, int x, int y){
        this.x = x;
        this.y = y;
        this.name = name;
        this.abbreviation = abbreviation;
    }
    public Location(String name, String abbreviation, int x, int y, BufferedImage image){
        this.x = x;
        this.y = y;
        this.name = name;
        this.abbreviation = abbreviation;
        this.image = image;
    }
    public void drawMe(Graphics g){
        drawMe(g,Color.BLACK);
    }
    public void drawImage(Graphics g){
        if(image!=null){
            g.drawImage(image,850,120,null);
        }
    }
    public void drawMe(Graphics g, Color color){
        g.setColor(color);
        g.fillOval(x-5,y-5,10,10);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.BOLD,10));
        g.drawString(name,x-15,y+15);
        g.drawString(abbreviation,x-15,y+25);
    }
    public int getX(){
        return x;
    }
    @Override
    public String toString(){
        return "Landmark Name: " + name + " Abbreviation: " + abbreviation;
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
        double value = 0;
        for(int i=0; i<abbreviation.length(); i++){
            value += (((int)abbreviation.charAt(i))*Math.pow(26,abbreviation.length()-i-1));
        }
        return (int)value;
    }
    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(abbreviation.equals(((Location)other).getAbbreviation())){
            return true;
        }
        return false;
    }
}
