import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import java.text.DecimalFormat;


public class Screen extends JPanel implements ActionListener, KeyListener, MouseListener{

	private JTextField startInput;
	private JTextField endInput;
	private JButton submit;
	private BufferedImage mapBackground;
	private int mouseY = 0;
    private int mouseX = 0;
	private String startLocationName;
	private String endLocationName;
	private Graph<Location> map;
	private Pair<DLList<Location>, Integer> shortestPath;

	public Screen(){
		this.setLayout(null);
		startInput = new JTextField();
		startInput.setFont(new Font("Arial", Font.PLAIN, 20));
		startInput.setBounds(30, 705, 200, 50);
		startInput.setText("");
		this.add(startInput);

		endInput = new JTextField();
		endInput.setFont(new Font("Arial", Font.PLAIN, 20));
		endInput.setBounds(300, 705, 200, 50);
		endInput.setText("");
		this.add(endInput);

		submit = new JButton();
		submit.setFont(new Font("Arial", Font.BOLD, 20));
		submit.setBounds(570, 705, 200, 50);
		submit.setText("Get Directions");
		this.add(submit);
		submit.addActionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		this.setFocusable(true);

		try {
            mapBackground = ImageIO.read(new File("images/legolandMapNoTextTranslucent.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

		map = new Graph<Location>();
		shortestPath = null;
		//String name, String abbreviation, String description, int x, int y
		Location sla = new Location("Sea Life Aquarium","sla",120,410);
		map.add(sla);
		Location ent = new Location("Entrance","ent",370,400);
		map.add(ent);
		Location dnv = new Location("Dino Valley","dnv",190,350);
		map.add(dnv);
		Location lmh = new Location("Legoland Main Hotel","lmh",580,460);
		map.add(lmh);
		Location lch = new Location("Legoland Castle Hotel","lch",190,500);
		map.add(lch);
		Location nwd = new Location("Ninjago World", "nwd", 500, 370);
		map.add(nwd);
		Location lgf = new Location("Lego Ferrari", "lgf", 460, 290);
		map.add(lgf);
		Location usa = new Location("Miniland USA", "usa", 510, 210);
		map.add(usa);
		Location loa = new Location("Land of Adventure", "loa", 660, 260);
		map.add(loa);
		Location chl = new Location("Castle Hill", "chl", 645, 120);
		map.add(chl);
		Location dsa = new Location("Deep Sea Adventure", "dsa", 540, 100);
		map.add(dsa);
		Location psh = new Location("Pirate Shores", "psh", 450, 110);
		map.add(psh);
		Location sfc = new Location("Surfer’s Cove", "sfc", 380, 70);
		map.add(sfc);
		Location ftn = new Location("Fun Town", "ftn", 340, 110);
		map.add(ftn);
		Location wpk = new Location("Water Park", "wpk", 270, 50);
		map.add(wpk);
		Location lmw = new Location("The Lego Movie World", "lmw", 60, 230);
		map.add(lmw);
		Location msp = new Location("Model Shop", "msp", 570, 220);
		map.add(msp);
		Location igz = new Location("Imagination Zone", "igz", 560, 320);
		map.add(igz);
		Location rtm = new Location("Restrooms", "rtm", 250, 260);
		map.add(rtm);
		Location btl = new Location("Bridge @ the Lake", "btl", 400, 250);
		map.add(btl);

		map.addEdge(lmh, lch,5);
		map.addEdge(lmh, ent, 4);
		map.addEdge(lch, ent, 4);
		map.addEdge(ent,rtm,3);
		map.addEdge(rtm,btl,3);
		map.addEdge(lmw,sla,3);
		map.addEdge(dnv,sla,2);
		map.addEdge(dnv,ent,3);
		map.addEdge(dnv,lmw,3);
		map.addEdge(wpk,lmw,4);
		map.addEdge(wpk,btl,5);
		map.addEdge(wpk,ftn,2);
		map.addEdge(ftn,sfc,1);
		map.addEdge(ftn,usa,4);
		map.addEdge(usa,btl,3);
		map.addEdge(ent,lgf,4);
		map.addEdge(nwd,lgf,1);
		map.addEdge(igz,lgf,2);
		map.addEdge(msp,lgf,2);
		map.addEdge(igz,msp,2);
		map.addEdge(usa,msp,1);
		map.addEdge(msp,chl,3);
		map.addEdge(loa,chl,2);
		map.addEdge(igz,loa,2);
		map.addEdge(dsa,usa,3);
		map.addEdge(dsa,chl,2);
		map.addEdge(psh,dsa,3);
		map.addEdge(psh,sfc,2);
		map.addEdge(rtm,lmw,3);
		map.addEdge(rtm,dnv,2);
		map.addEdge(rtm,wpk,4);
		map.addEdge(btl,lgf,1);

	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800,900);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0,0,800,900);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.BOLD,10));
		g.drawString("Starting Location (Enter Abbreviation)",30,700);
		g.drawString("Destination (Enter Abbreviation)",300,700);
		g.drawImage(mapBackground,0,0,null);
		g.drawString("MouseX: " + mouseX + " MouseY: " + mouseY,40,640);
		map.drawMe(g);
		if(shortestPath!=null){
			map.drawPath(g, shortestPath, new Color(0, 0, 255));
		}
		if(startLocationName!=null && endLocationName!=null && shortestPath!=null){
			DecimalFormat df = new DecimalFormat("0.0"); 
			g.drawString("From " +startLocationName+ " to " + endLocationName,240,620);
			double distance = shortestPath.getSecond()*0.1;
			String roundedDistance = df.format(distance);
			g.drawString("Distance: " + roundedDistance + " km",240,635);	

			g.drawString("Directions",510,610);
			int y = 630;
			for(int i=0; i<shortestPath.getFirst().size()-1; i++){
				Location locationObj1 = (Location)shortestPath.getFirst().get(i);
				Location locationObj2 = (Location)shortestPath.getFirst().get(i+1);
				double stepDistance = map.getWeight(locationObj1, locationObj2)*0.1;
				String roundedStepDistance = df.format(stepDistance);
				g.drawString(locationObj1.getName()+ " -> "+locationObj2.getName() + " " + roundedStepDistance + " km",510,y);
				y+=15;
			}
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==submit){
			String startingAbbreviation = startInput.getText();
			String endingAbbreviation = endInput.getText();
			startLocationName = map.getNodeByAbbreviation(startingAbbreviation).getName();
			endLocationName = map.getNodeByAbbreviation(endingAbbreviation).getName();
			Location start = map.getNodeByAbbreviation(startingAbbreviation);
			Location end = map.getNodeByAbbreviation(endingAbbreviation);
			startInput.setText("");
			endInput.setText("");
			shortestPath = map.shortestPath(start, end);
		}
		repaint();
	}
	public void mousePressed(MouseEvent e){
		System.out.println("X: " + e.getX() + " Y: " + e.getY());
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

}
