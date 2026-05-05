import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{
    private Socket socket;
    private Manager manager;
    public ServerThread(Socket socket, Manager manager){
        this.socket = socket;
        this.manager = manager;
    }
    public void send(){
        try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //send output
			out.close();

		} catch (IOException e) {
			
		}
    }
    @Override
    public void run(){
        while(true){
            //recieve message
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                in.close();
            } catch (IOException e) {
            }
                manager.broadcast();
        }
    }
}
