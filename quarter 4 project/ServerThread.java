import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{
    private Socket socket;
    private Manager manager;
    private ServerScreen sc;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;

    public ServerThread(Socket socket, Manager manager, ServerScreen sc){
        this.socket = socket;
        this.manager = manager;
        this.sc = sc;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            disconnect();
        }
    }
    public synchronized void send(Object data){
        //sends message to client linked to this thread
        if (out != null) {
            try {
                out.reset();
                out.writeObject(data);
                out.flush();
            } catch (IOException e) {
                 disconnect();
            }
        }
    }
    @Override
    @SuppressWarnings("unchecked")
    public void run(){

        send(sc.getMap());
        //sends the message to all the clients through calling send of all serverthreads
        while(true){
            //constantly recieve inputs
            try {
                Object data = in.readObject();
                if(data==null){
                    break;
                }
                else if(data instanceof PhaseData){
                    PhaseData incoming = (PhaseData)data;
                    sc.getPhaseData().setPhase(incoming.getPhase());
                    manager.broadcast(sc.getPhaseData());
                    if(incoming.getPhase()==0){
                        sc.resetGame();
                        manager.broadcast(sc.getPlayers());
                        manager.broadcast(sc.getMap());
                    }
                }
                else if(data instanceof String){
                    username = (String)data;
                    synchronized(sc.getPlayers()){
                        if(sc.getPlayers().size()==0){
                            sc.getPlayers().put(new PlayerID(username), new PlayerData(10,0,true));
                        } else {
                            sc.getPlayers().put(new PlayerID(username), new PlayerData(10,23,true));
                        }
                    }
                    manager.broadcast(sc.getPlayers());
                    int readyCounter = 0;
                    synchronized(sc.getPlayers()){
                        for(PlayerID each: sc.getPlayers().keySet()){
                            if(sc.getPlayers().get(each).isReady()){
                                readyCounter++;
                            }
                        }
                        if(readyCounter>=2){
                            sc.getPhaseData().setPhase(1);
                            manager.broadcast(sc.getPhaseData());
                            sc.startCountdown();
                        }
                    }
                }
                else if(data instanceof PlayerData){
                    PlayerData incoming = (PlayerData) data;
                    synchronized(sc.getPlayers()){
                        sc.getMap().get(new Location(incoming.getPrevRow(), incoming.getPrevCol())).set(0, "lava");
                        sc.getMap().get(new Location(incoming.getPrevRow(), incoming.getPrevCol())).remove("potion");
                        sc.getMap().get(new Location(incoming.getPrevRow(), incoming.getPrevCol())).remove("multiplier");
                        if(sc.getMap().get(new Location(incoming.getRow(), incoming.getCol())).remove("coin")){
                            incoming.increaseScore(1);
                        }
                        if(sc.getMap().get(new Location(incoming.getRow(), incoming.getCol())).remove("potion")){
                            incoming.increaseScore(5);
                        }
                        if(sc.getMap().get(new Location(incoming.getRow(), incoming.getCol())).remove("multiplier")){
                            incoming.increaseScore(5);
                        }
                        if(sc.getMap().get(new Location(incoming.getRow(), incoming.getCol())).get(0).equals("lava")){
                            incoming.setVisible(false);
                        }
                        sc.getPlayers().put(new PlayerID(username), incoming);
                    }
                    manager.broadcast(new PlayerID(username));
                    manager.broadcast(incoming);
                }
                else if(data instanceof MyHashMap){
                    manager.broadcast((MyHashMap)data);
                    
                }
                else{
                     manager.broadcast(data);
                }
                
            } catch (IOException e) {
                System.out.println("Connection lost");
                break;
            }
             catch (ClassNotFoundException e) {
                System.out.println("Received unknown object type");
                e.printStackTrace();
                break;
            }
        }
        disconnect();
    }
    public void disconnect(){
        manager.remove(this);
        if(username != null){
            synchronized(sc.getPlayers()){
                sc.getPlayers().remove(new PlayerID(username));
            }
            manager.broadcast(sc.getPlayers());
        }
        sc.repaint();
        try {
            if(in != null) in.close();
            if(out != null) out.close();
            if(socket != null && !socket.isClosed()) socket.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
