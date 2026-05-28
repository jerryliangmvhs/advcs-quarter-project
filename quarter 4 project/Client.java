import javax.swing.JFrame;
import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame("Quarter 4 Project - Lava Spleef");
		ClientScreen sc = new ClientScreen();
		
		frame.add(sc);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

        new Thread(() -> {
			try { sc.connect(); } 
			catch (IOException e) { e.printStackTrace(); }
		}).start();
    }
}