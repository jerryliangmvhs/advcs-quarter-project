import java.io.Serializable;

public class MapObject implements Serializable{
    private String name;
    public MapObject(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
