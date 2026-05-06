import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{
    private Socket socket;
    private Manager manager;
    public ServerThread(Socket socket, Manager manager){
        this.socket = socket;
        this.manager = manager;
    }
    public void send(String string){
        try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //send output
			out.println(string);

		} catch (IOException e) {
			
		}
    }
    @Override
    public void run(){
        //prints only when the thread just started
        System.out.println("broadcasting");
        manager.broadcast("A client has connected!");
        while(true){
            //recieve message
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                manager.broadcast(in.readLine());
                
            } catch (IOException e) {
                disconnect();
            }
        }
    }
    public void disconnect(){
        try {
            System.out.println("a client discconnected");
            socket.close();
        } catch (IOException e) {
            manager.remove(this);
        }
    }
}
