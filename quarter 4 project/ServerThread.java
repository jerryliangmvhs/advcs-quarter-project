import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{
    private Socket socket;
    private Manager manager;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket socket, Manager manager){
        this.socket = socket;
        this.manager = manager;

        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(
                    socket.getOutputStream(), true);

        } catch (IOException e) {
            disconnect();
        }
    }
    public void send(String string){
        if (out != null) {
            out.println(string);

            if (out.checkError()) {
                disconnect();
            }
        }
    }
    @Override
    public void run(){
        //prints only when the thread just started
        System.out.println("broadcasting");
        manager.broadcast("A client has connected!");
        while(true){
            //constantly recieve inputs
            try {
                manager.broadcast(in.readLine());

                if(in.readLine()==null){
                    break;
                }
                
            } catch (IOException e) {
                System.out.println("Connection lost");
            }
        }
        disconnect();
    }
   public void disconnect() {
        System.out.println("Client disconnected");
        manager.remove(this);
        manager.broadcast("A client disconnected!");

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
