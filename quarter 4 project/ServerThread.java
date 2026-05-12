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

    public ServerThread(Socket socket, Manager manager, ServerScreen sc, MyHashTable<Location,String> map, MyHashMap<PlayerID,PlayerData> players){
        this.socket = socket;
        this.manager = manager;
        this.sc = sc;
        this.map = map;
        this.players = players;

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
                out.writeObject(data);
                out.flush();
            } catch (IOException e) {
                 disconnect();
            }
        }
    }
    @Override
    public void run(){
        send(map);
        System.out.println("broadcasting");
        //sends the message to all the clients through calling send of all serverthreads
        manager.broadcast("A client has connected!");
        while(true){
            //constantly recieve inputs
            try {
                Object data = in.readObject();
                if(data==null){
                    break;
                }
                else if (data instanceof String){
                   String username = (String)data;
                   players.put(new PlayerID(username),new PlayerData(10,0));
                }
                else if(data instanceof MyHashTable){
                    manager.broadcast((MyHashTable)data);
                }
                manager.broadcast(data);
                
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
        manager.broadcast("A client disconnected!");
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
