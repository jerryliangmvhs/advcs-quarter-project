import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ClientScreen extends JPanel implements ActionListener, KeyListener, MouseListener{
   
    private Socket socket;
    private JButton playButton;
    private JTextField nameInput;
    private String username;
    private int phase = 0;
    private Object data;
    private MyHashTable<Location,String> map;
    private MyHashMap<PlayerID,PlayerData> players;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private PlayerData myCurrentData;
    private PhaseData phaseData;
    private boolean ready = false;
    private Font titleFont, buttonFont, timerFont;
    private BufferedImage coin, lava, rock, potion, multiplier;
    private int seconds;

	public ClientScreen(){
	    this.setLayout(null);
        phaseData = new PhaseData();
        seconds = 30;

         try{
            titleFont = Font.createFont(Font.TRUETYPE_FONT,new File("minecraft-five.ttf")).deriveFont(35f);
            buttonFont = Font.createFont(Font.TRUETYPE_FONT,new File("minecraft-five.ttf")).deriveFont(15f);
            timerFont = Font.createFont(Font.TRUETYPE_FONT,new File("minecraft-five.ttf")).deriveFont(25f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(titleFont);
            ge.registerFont(buttonFont);
            ge.registerFont(timerFont);

            coin = ImageIO.read(new File("coin.png"));
            lava = ImageIO.read(new File("lava.jpg"));
            rock = ImageIO.read(new File("rock.jpg"));
            multiplier = ImageIO.read(new File("multiplier.png"));
            potion = ImageIO.read(new File("potion.png"));
            } catch (IOException | FontFormatException e){
            e.printStackTrace();
        }

        
        
        playButton = new JButton();
        playButton.setFont(buttonFont);
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

        players = new MyHashMap<PlayerID,PlayerData>();

		addMouseListener(this);
		addKeyListener(this);

       
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1200,900);
	}
    @SuppressWarnings("unchecked")
    public void connect() throws IOException{
		String hostName = "localhost"; 
		int portNumber = 1024;
	
		try {
            socket = new Socket(hostName, portNumber);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
           
			while (true) {
                data = in.readObject();
                if(data==null){
                    System.out.println("Recieved data is null");
                    break;
                }
                else if(data instanceof Countdown){
                    seconds = ((Countdown)data).getSeconds();
                    if(seconds>=10){
                         System.out.println("0:"+seconds);
                    }   
                    else if(seconds<10){
                        System.out.println("0:0"+seconds);
                    }
                }
                else if(data instanceof MyHashTable){
                    map = (MyHashTable)data;
                }
                else if(data instanceof PhaseData){
                    phase = ((PhaseData)data).getPhase();
                }
                //if recieved data is data of all players (hashmap)
                else if (data instanceof PlayerID) {
                    PlayerID movedPlayer = (PlayerID) data;
                    Object next = in.readObject();
                    if (next instanceof PlayerData) {
                        PlayerData pd = (PlayerData) next;
                        synchronized (players) {
                            // apply map changes locally
                            map.get(new Location(pd.getPrevRow(), pd.getPrevCol())).set(0, "lava");
                            if(map.get(new Location(pd.getRow(), pd.getCol())).remove("coin")){
                                coinSound();
                            }
                            map.get(new Location(pd.getRow(), pd.getCol())).remove("potion");
                            map.get(new Location(pd.getRow(), pd.getCol())).remove("multiplier");
                            players.put(movedPlayer, pd);
                        }
                    }
                }
                else if(data instanceof MyHashMap){
                    players = (MyHashMap) data;
                }
                //so there's not a conflict with the network
				SwingUtilities.invokeLater(() -> repaint());
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
        catch (ClassNotFoundException e) {
            System.out.println("Received unknown object type");
            e.printStackTrace();
        }
	}

	@Override
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(phase == 0){
            g.setColor(new Color(252, 144, 35));
            g.fillRect(0,0,1200,900);
            g.setFont(titleFont);
            g.setColor(Color.BLACK);
            g.drawString("Lava Spleef",390,100);
            g.setFont(new Font("Arial",Font.BOLD,15));

            
            
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Collect as many coins as possible by moving around the map (use arrow keys).",320,190);
            g.drawString("You must move quickly as a trail of lava appears behind you",320,210);
            g.drawString("Your goal is to have the other player accidentally walk into lava",320,230);
            g.drawString("Do your best to not stay in one spot for too long.",320,250);
            g.drawString("The map will reset every 30 seconds for 4 times for more chances.",320,270);
            g.drawString("If you die, you have to wait for the map to reset to continue playing.",320,290);
            g.drawString("The player with the most coins after round 5 wins!",320,310);
        }
        if(phase==1){
            int x = 0;
            int y = 0;
            //draw map
            if(map!=null){
                for(int i=0; i<18; i++){
                    for(int j=0; j<24; j++){
        
                        Location location = new Location(i, j);
                        if(map.get(location).get(0).equals("stone")){
                            g.drawImage(rock,x,y,50,50,null);
                        }
                        if(map.get(location).get(0).equals("lava")){
                            g.drawImage(lava,x,y,50,50,null);
                        }
                        if(map.get(location).get(1).equals("coin")){
                            g.drawImage(coin,x,y,50,50,null);
                        }
                        if(map.get(location).get(1).equals("potion")){
                            g.drawImage(potion,x,y,50,50,null);
                        }
                        if(map.get(location).get(1).equals("multiplier")){
                            g.drawImage(multiplier,x,y,50,50,null);
                        }
                        
                        g.setColor(Color.BLACK);
                        g.drawRect(x,y,50,50);
                        x+=50;
                    }
                        x=0;
                        y+=50;
                }
            }
            if(players!=null){
                g.setColor(Color.RED); 
                for(PlayerID key: players.keySet()){
                    if(players.get(key).isVisible()){
                        g.fillRect(players.get(key).getCol()*50,players.get(key).getRow()*50,50,50);
                    }
                }
            }
            displayScores(g);
            displayTimer(g);
        }
	}
    public void displayTimer(Graphics g){
        g.setFont(timerFont);
        g.setColor(Color.WHITE);
        int x = 575;
        int y = 50;
        if(seconds>=10){
            g.drawString("0:"+seconds,x,y);
        }
        else if(seconds<10){
            g.drawString("0:0"+seconds,x,y);
        }
    }
    public void displayScores(Graphics g){
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.setColor(Color.WHITE);
        int x = 20;
        int y = 50;
        for(PlayerID each: players.keySet()){
            int score = players.get(each).getScore();
            String playerName = each.getName();
            g.drawString(playerName+"'s Score: "+score,x,y);
            y+=20;
        }
    }
	public void actionPerformed(ActionEvent e){
        if(e.getSource()==playButton || e.getSource()==nameInput){
            username = nameInput.getText();
            if(username.length()>=2){
                ready = true;
                nameInput.setText("");

                try {
                    out.reset();
                    out.writeObject(username);
                    out.flush();
                } catch (IOException ex) {} 

                playButton.setVisible(false);
                nameInput.setVisible(false);
            }
        }
        requestFocus();
	    setFocusable(true);		

        repaint();
    }
    public void coinSound(){
        try {
            URL url = this.getClass().getClassLoader().getResource("coin.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    public void lavaSound(){
        try {
            URL url = this.getClass().getClassLoader().getResource("lava.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
	public void mousePressed(MouseEvent e){
        System.out.println("X: "+e.getX()+" Y: "+e.getY());
    }
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}

    //controls, use out.writeObject() to send out updated positions after
	public void keyPressed(KeyEvent e){
        boolean successfullyMoved = false;
        if(username==null){
            return;
        }
        int key = e.getKeyCode();
        PlayerData me = players.get(new PlayerID(username));
        if(me == null){
            return;
        }
    
        if(key == 37 && phase!=0){
            int currentRow = me.getRow();
            int currentCol = me.getCol();
            if(me.moveLeft()){
                successfullyMoved = true;
            }
            //map.get(new Location(currentRow,currentCol)).set(0,"lava");
            repaint();

        }
        if(key == 38 && phase !=0){
            int currentRow = me.getRow();
            int currentCol = me.getCol();
            if(me.moveUp()){
                successfullyMoved = true;
            }
            //map.get(new Location(currentRow,currentCol)).set(0,"lava");
            repaint();

        }
        if(key == 39 && phase !=0){
            int currentRow = me.getRow();
            int currentCol = me.getCol();
            if(me.moveRight()){
                successfullyMoved = true;
            }
            repaint();

        }
        if(key == 40 && phase !=0){
            int currentRow = me.getRow();
            int currentCol = me.getCol();
            if(me.moveDown()){
                successfullyMoved = true;
            }
            repaint();
        }
         
        repaint();
        myCurrentData = me;
        if(successfullyMoved){
            lavaSound();
            try {
            out.reset();
            out.writeObject(myCurrentData);
            out.flush();
            } catch (IOException ex) {} 
        }
        
    }
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

}