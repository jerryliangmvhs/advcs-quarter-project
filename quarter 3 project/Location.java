import java.awt.*;

public class Location{
    private int x;
    private int y;
    private String name;
    private String description;
    public Location(String name, String description, int x, int y){
        this.x = x;
        this.y = y;
        this.name = name;
        this.description = description;
    }
    public void drawMe(Graphics g){
        
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
