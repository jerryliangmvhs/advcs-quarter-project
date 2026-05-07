import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class ClientScreen extends JPanel implements ActionListener, KeyListener, MouseListener{
    private String message;
    private PrintWriter out;
    private Socket socket;
    private JButton playButton;
    private JTextField nameInput;
    private String username;

	public ClientScreen(){
	    this.setLayout(null);
        
        playButton = new JButton();
        playButton.setFont(new Font("Arial", Font.BOLD, 25));
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        playButton.setBounds(355, 555, 500, 100);
        playButton.setText("PLAY");
        this.add(playButton);
        playButton.addActionListener(this);

        nameInput = new JTextField();
        nameInput.setFont(new Font("Arial", Font.PLAIN, 20));
        nameInput.setHorizontalAlignment(SwingConstants.LEFT);
        nameInput.setBounds(355, 485, 500, 50);
        nameInput.setText("");
        nameInput.addActionListener(this);
        this.add(nameInput);

		addMouseListener(this);
		addKeyListener(this);
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1200,900);
	}
    public void connect() throws IOException{
		String hostName = "localhost"; 
		int portNumber = 1024;
	
		try {
            socket = new Socket(hostName, portNumber);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
                System.out.println("Waiting for message");
                message = in.readLine();
                if(message==null){
                    break;
                }
				repaint();
			}
            in.close();
            socket.close();
		} catch (UnknownHostException e){
			System.err.println("Host unknown: " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

	@Override
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(252, 144, 35));
        g.fillRect(0,0,1200,900);
        g.setFont(new Font("Arial",Font.BOLD,50));
        g.setColor(Color.BLACK);
        g.drawString("Lava Spleef",450,100);
	}
	public void actionPerformed(ActionEvent e){
        if(e.getSource()==playButton || e.getSource()==nameInput){
            username = nameInput.getText();
            nameInput.setText("");
        }
        repaint();
    }
	public void mousePressed(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

}