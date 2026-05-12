import java.io.Serializable;

public class PlayerID implements Serializable {
    private String name;
    public PlayerID(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        String nameLowercase = name.toLowerCase();

        for (int i = 0; i < nameLowercase.length(); i++) {
            hash = hash * 31 + nameLowercase.charAt(i);
        }

        return hash;
    }
}
