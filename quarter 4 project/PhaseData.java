import java.io.Serializable;

public class PhaseData implements Serializable{
   private int phase = 0;
   public void setPhase(int phase){
    this.phase = phase;
   }
   public int getPhase(){
    return phase;
   }
   public void increase(){
    phase++;
   }
   public void reset(){
    phase = 0;
   }
}
