import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import java.net.*;
import java.io.*;


public class ServerScreen extends JPanel implements ActionListener, KeyListener, MouseListener{

	private Manager manager;
	private int portNumber = 1024;

	public Screen(){
	    this.setLayout(null);
		addMouseListener(this);
		addKeyListener(this);
	}
	public void startServer(){
		try {
			ServerSocket server = new ServerSocket(portNumber);
			System.out.println("Waiting for a connection");
			while (true) {
				Socket socket = serverSocket.accept();
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
	public void actionPerformed(ActionEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

}
