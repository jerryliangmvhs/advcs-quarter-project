import java.net.*;
import java.io.*;

public class Manager {
    private DLList<ServerThread> threads;
    public Manager(){
        threads = new DLList<ServerThread>();
    }
    public synchronized void add(ServerThread s){
        threads.add(s);
    }
    public synchronized void remove(ServerThread s){
        threads.remove(s);
    }
    public int size(){
        return threads.size();
    }
    //need to pass in an object
    public synchronized void broadcast(Object data){
        for(int i=0; i<threads.size(); i++){
            threads.get(i).send(data);
        }
    }
}
