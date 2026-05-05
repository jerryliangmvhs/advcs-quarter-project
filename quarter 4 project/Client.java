import javax.swing.JFrame;
import java.net.*;
import java.io.*;


public class Client {
  
	public static void main(String[] args) {

		JFrame frame = new JFrame("My Program");
		ClientScreen sc = new ClientScreen();
		
		frame.add(sc);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
    }
}
