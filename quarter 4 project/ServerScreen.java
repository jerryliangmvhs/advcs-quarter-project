import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import java.net.*;
import java.io.*;


public class ServerScreen extends JPanel implements ActionListener, KeyListener, MouseListener{
    private int users;
	private Manager mg;
	public ServerScreen(){
	    this.setLayout(null);
		addMouseListener(this);
		addKeyListener(this);
        users = 0;
	}
    public void startServer() throws IOException{
        int portNumber = 1024;

		ServerSocket serverSocket = new ServerSocket(portNumber);
		mg = new Manager();
		
		while(true){
			System.out.println("Waiting for a connection");
			Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected!");
			ServerThread st = new ServerThread(clientSocket,mg);
			mg.add(st);
			users = mg.size();
			Thread thread = new Thread(st);
			thread.start();
            repaint();
		}
    }
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(300,200);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        g.setFont(new Font("Arial",Font.PLAIN,20));
        g.setColor(Color.BLACK);
        try {
            g.drawString("IP: " + InetAddress.getLocalHost().getHostAddress(),20,20);
            g.drawString("Number of Clients: " + users,20,40);
        } catch (UnknownHostException ex) {
            System.out.println("Could not find IP address for this host");
        }
       

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