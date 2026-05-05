import java.net.*;
import java.io.*;

public class Manager {
    private DLList<ServerThread> threads;
    public Manager(){
        threads = new DLList<DLList>();
    }
    public void add(ServerThread s){
        threads.add(s);
    }
    public void broadcast(){
        for(int i=0; i<threads.size(); i++){
            threads.get(i).send();
        }
    }
}
