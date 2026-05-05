import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import java.net.*;
import java.io.*;



public class ClientScreen extends JPanel implements ActionListener, KeyListener, MouseListener{
	private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

	public ClientScreen(){
	    this.setLayout(null);
		addMouseListener(this);
		addKeyListener(this);

		try {
			String hostName = "";
			int portNumber = 1024;
			socket= new Socket(hostName, portNumber);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

		} catch (IOException e){
			
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
