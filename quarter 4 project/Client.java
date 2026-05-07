import javax.swing.JFrame;
import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame("Client Screen");
		ClientScreen sc = new ClientScreen();
		
		frame.add(sc);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

        sc.connect();
    }
}