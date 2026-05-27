import java.io.Serializable;

public class Countdown implements Runnable, Serializable {
    private int seconds;
    private boolean started;
    private transient Manager manager;
    private int restarts;
    private transient ServerScreen sc;

    public Countdown(Manager manager, ServerScreen sc){
        seconds = 10;
        started = false;
        this.manager = manager;
        restarts = 0;
        this.sc = sc;

    }
    public int getRestarts(){
        return restarts;
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
                    sc.resetRound();
                    seconds = 10;
                    restarts++;
                }
                if(restarts>=5){
                    stop();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
