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
	private String ipAddress;
	private int port;
	private MyHashTable<Location,String> map;
	private MyHashMap<PlayerID,PlayerData> players;
	private int resets = 0;
	private int phase = 0;
	private PhaseData phaseData;

	public ServerScreen(){
	    this.setLayout(null);
		addMouseListener(this);
		addKeyListener(this);
        users = 0;
		port = 1024;
		map = new MyHashTable<Location,String>();
		players = new MyHashMap<PlayerID,PlayerData>();
		phaseData = new PhaseData();
		
		//set up initial map
		
		for(int i=0; i<18; i++){
			for(int j=0; j<24; j++){
				Location location = new Location(i,j);
				map.put(location,"stone");

				//RNG for coin generation
				int random = (int)(Math.random()*100);
				if(random<=30){
					//~30% chance of a coin on a tile
					map.put(location,"coin");
				}
				else if(random>=95){
					//~5% chance of a fire resistance potion power up
					map.put(location,"potion");
				}
				else if(random<=4){
					//~4% chance of a multiplier power up
					map.put(location,"multiplier");
				}
				
			}
		}
			
	}
    public void startServer() throws IOException{

		ServerSocket serverSocket = new ServerSocket(port);
		mg = new Manager();

		while(true){
			System.out.println("Waiting for a connection");
			Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected!");
			//give this info upon user connection
			ServerThread st = new ServerThread(clientSocket,mg,this,map,players,phaseData);
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
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
        }
		catch (UnknownHostException ex) {
            System.out.println("Could not find IP address for this host");
        }

        g.setFont(new Font("Arial",Font.PLAIN,20));
        g.setColor(Color.BLACK);
		if(mg!=null){
			users = mg.size();
		}
		g.drawString("IP: " + ipAddress,20,20);
		g.drawString("Port: "+port,20,40);
		g.drawString("Number of Clients: " + users,20,60);
	}
	public void actionPerformed(ActionEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){
	}
	public void keyReleased(KeyEvent e){}

}