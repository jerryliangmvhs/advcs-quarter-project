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
	private Countdown countdown;
	private Thread myTimer;

	public ServerScreen(){
	    this.setLayout(null);
		addMouseListener(this);
		addKeyListener(this);
        users = 0;
		port = 5700;
		map = new MyHashTable<Location,String>();
		players = new MyHashMap<PlayerID,PlayerData>();
		phaseData = new PhaseData();
		
		//set up initial map
		createMap();
			
	}
	public void createMap(){
		for(int i=0; i<18; i++){
			for(int j=0; j<24; j++){
				Location location = new Location(i,j);
				map.put(location,"stone");

				//RNG for coin generation
				int random = (int)(Math.random()*100);
				if(random<=29){
					//~30% chance of a coin on a tile
					map.put(location,"coin");
				}
				if(random==99){
					//~2% chance of a fire resistance potion power up
					map.put(location,"potion");
				}
				if(random==30){
					//~1% chance of a multiplier power up
					map.put(location,"multiplier");
				}
			}
		}
	}
	public MyHashTable<Location,String> getMap(){
		return map;
	}
	 public void resetGame(){
		if(countdown != null){
			countdown.stop();
		}
		if(myTimer != null){
			myTimer.interrupt();
		}
		synchronized(players){
			players.clear();
		}
		synchronized(map){
			map.clear();
			createMap();
		}
		resets = 0;
		countdown = new Countdown(mg, this);
	}
	public void resetRound(){
		synchronized(map){
			map.clear();
			createMap();
		}
		synchronized(players){
			int i = 0;
			for(PlayerID each : players.keySet()){
				if(i == 0){
					players.put(each, new PlayerData(10, 0, true));
				} else {
					players.put(each, new PlayerData(10, 23, true));
				}
				i++;
			}
		}
		resets++;
		if(resets>=5){
			phaseData.setPhase(2);
        	mg.broadcast(phaseData);
		}
		mg.broadcast(map);
		mg.broadcast(players);
	}

	public void startCountdown(){
		if(countdown != null && !countdown.isStarted()){
			countdown.start();
			myTimer = new Thread(countdown);
			myTimer.start();
		}
	}
    public void startServer() throws IOException{

		ServerSocket serverSocket = new ServerSocket(port);
		mg = new Manager();
		countdown = new Countdown(mg,this);
		
		while(true){
			System.out.println("Waiting for a connection");
			Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected!");
			//give this info upon user connection
			ServerThread st = new ServerThread(clientSocket,mg,this);
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
	public MyHashMap<PlayerID, PlayerData> getPlayers(){
    	return players;
	}
	public PhaseData getPhaseData(){
		return phaseData;
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