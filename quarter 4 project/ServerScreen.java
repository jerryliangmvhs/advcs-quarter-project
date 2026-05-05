import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import java.net.*;
import java.io.*;


public class ServerScreen extends JPanel{

	private Manager manager;
	private int portNumber = 1024;

	public ServerScreen(){
	    this.setLayout(null);
	}
	public void startServer(){
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("Waiting for a connection");
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Connection Successful!");
				ServerThread serverThread = new ServerThread(socket, manager);
				manager.add(serverThread);
				serverThread.start();
			}

		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " +
				portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
		

	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1200,900);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}

}
