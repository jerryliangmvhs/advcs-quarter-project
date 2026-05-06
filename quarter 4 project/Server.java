import javax.swing.JFrame;
import  java.net.*;
import java.io.*;

public class Server {
	public static void main(String[] args) throws IOException{

		JFrame frame = new JFrame("Server Screen");
		ServerScreen sc = new ServerScreen();
		
		frame.add(sc);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);

        sc.startServer();
    }
}