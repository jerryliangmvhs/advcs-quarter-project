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
    public boolean equals(Object other) {
        PlayerID playerObj = (PlayerID)other;
        if (name.equals(playerObj.getName())){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public int hashCode() {
        int hash = 0;
        String nameLowercase = name.toLowerCase();
        hash += nameLowercase.charAt(0)*Math.pow(26,1) + nameLowercase.charAt(1)*Math.pow(26,0);

        return hash;
    }
    
}
