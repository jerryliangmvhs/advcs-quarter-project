import java.io.Serializable;

public class Countdown implements Runnable, Serializable {
    private int seconds;
    private boolean started;
    private transient Manager manager;

    public Countdown(Manager manager){
        seconds = 31;
        started = false;
        this.manager = manager;
    }
    public int getSeconds(){
        return seconds;
    }
    public boolean isStarted(){
        return started;
    }
    
    public void start(){
        started = true;
    }
    public void stop(){
        started = false;
    }
    @Override
    public void run(){
        while(true){
            if(started){
                seconds--;
                manager.broadcast(this);
                if(seconds==0){
                    seconds = 31;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
