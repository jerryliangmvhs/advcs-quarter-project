import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{
    private Socket socket;
    private Manager manager;
    private ServerScreen sc;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ServerThread(Socket socket, Manager manager, ServerScreen sc){
        this.socket = socket;
        this.manager = manager;
        this.sc = sc;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

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
