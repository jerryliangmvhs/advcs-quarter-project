import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{
    private Socket socket;
    private Manager manager;
    private ServerScreen sc;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private MyHashTable<Location,String> map;
    private MyHashMap<PlayerID,PlayerData> players;
    private PhaseData phaseData;
    private String username;

    public ServerThread(Socket socket, Manager manager, ServerScreen sc, MyHashTable<Location,String> map, MyHashMap<PlayerID,PlayerData> players,PhaseData phaseData){
        this.socket = socket;
        this.manager = manager;
        this.sc = sc;
        this.map = map;
        this.players = players;
        this.phaseData = phaseData;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            disconnect();
        }
    }
    public void send(Object data){
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
        send(map);
        System.out.println("broadcasting");
        //sends the message to all the clients through calling send of all serverthreads
        while(true){
            //constantly recieve inputs
            try {
                Object data = in.readObject();
                if(data==null){
                    break;
                }
                else if(data instanceof PhaseData){
                    manager.broadcast(phaseData);
                }
                else if (data instanceof String){
                    //after one presses the start button
                   username = (String)data;
                   synchronized(players){
                     players.put(new PlayerID(username),new PlayerData(10,0,true));
                   }
                   manager.broadcast(players);
                    int readyCounter = 0;
                    synchronized(players){
                    for(PlayerID each: players.keySet()){
                        //if one player is not ready
                        if(players.get(each).isReady()){
                            readyCounter++;
                        }
                    }
                    if(readyCounter>=2){
                        phaseData.setPhase(1);
                        manager.broadcast(phaseData);
                    }
                    }
                    
                }
                
                else if (data instanceof PlayerData){
                    PlayerData incoming = (PlayerData) data;
                    synchronized(players){
                        map.get(new Location(incoming.getPrevRow(), incoming.getPrevCol())).set(0, "lava");
                        if(map.get(new Location(incoming.getRow(), incoming.getCol())).remove("coin")){
                            incoming.increaseScore();
                        }

                        if(map.get(new Location(incoming.getRow(), incoming.getCol())).get(0).equals("lava")){
                            incoming.setVisible(false);
                        }

                        players.put(new PlayerID(username), incoming);
                    }
                    manager.broadcast(new PlayerID(username));
                    manager.broadcast(incoming);
                    //manager.broadcast(map);
                }
                else if(data instanceof MyHashTable){
                    map = (MyHashTable<Location,String>)data;
                    manager.broadcast(map);
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
   public void disconnect() {
        manager.remove(this);
        if (username != null) {
        synchronized (players) {
            players.remove(new PlayerID(username));
        }
        manager.broadcast(players);
        }
        sc.repaint();

        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
