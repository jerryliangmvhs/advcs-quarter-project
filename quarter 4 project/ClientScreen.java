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
    private int level = 0;

	public ClientScreen(){
	    this.setLayout(null);
        
        playButton = new JButton();
        playButton.setFont(new Font("Arial", Font.BOLD, 25));
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        playButton.setBounds(355, 555, 500, 100);
        playButton.setText("Ready");
        playButton.setBackground(new Color(237, 83, 0));
		playButton.setOpaque(true);
		playButton.setBorderPainted(false);
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
            out = new PrintWriter(socket.getOutputStream(), true);

			while (true) {
                message = in.readLine();
                System.out.println(message);
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
        if(level == 0){
            g.setColor(new Color(252, 144, 35));
            g.fillRect(0,0,1200,900);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.setColor(Color.BLACK);
            g.drawString("Lava Spleef",450,100);
            g.setFont(new Font("Arial",Font.BOLD,15));
            g.drawString("Enter Username",360,480);
            
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Collect as many coins as possible by moving around the map",320,190);
            g.drawString("You must move quickly as a trail of lava appears behind you",320,210);
            g.drawString("Your goal is to have the other player accidentally walk into lava",320,230);
            g.drawString("Do your best to not stay in one spot for too long.",320,250);
            g.drawString("The map will reset every 30 seconds for 4 times for more chances.",320,270);
            g.drawString("If you die, you have to wait for the map to reset to continue playing.",320,290);
            g.drawString("The player with the most coins after round 5 wins!",320,310);
        }
	}
	public void actionPerformed(ActionEvent e){
        if(e.getSource()==playButton || e.getSource()==nameInput){
            username = nameInput.getText();
            nameInput.setText("");
            out.println(username + " is ready to play!");
            playButton.setVisible(false);
            nameInput.setVisible(false);
            level = 1;
        }
        repaint();
    }
	public void mousePressed(MouseEvent e){
        System.out.println("X: "+e.getX()+" Y: "+e.getY());
    }
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

}