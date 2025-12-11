import java.io.Serializable;

public class Animate implements Serializable, Runnable {
    private Screen sc;
    public Animate(Screen sc){
        this.sc = sc;
    }
    @Override
    public void run(){
        while(true){
            sc.repaint();
        }
    }
}
