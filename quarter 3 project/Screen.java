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


public class Screen extends JPanel implements ActionListener, KeyListener, MouseListener{

	private JTextField startInput;
	private JTextField endInput;
	private JButton submit;
	private BufferedImage mapBackground;
	private int mouseY = 0;
    private int mouseX = 0;
	private Graph<Location> map;

	public Screen(){
		this.setLayout(null);
		startInput = new JTextField();
		startInput.setFont(new Font("Arial", Font.PLAIN, 20));
		startInput.setBounds(105, 705, 300, 50);
		startInput.setText("");
		this.add(startInput);

		endInput = new JTextField();
		endInput.setFont(new Font("Arial", Font.PLAIN, 20));
		endInput.setBounds(455, 705, 300, 50);
		endInput.setText("");
		this.add(endInput);

		submit = new JButton();
		submit.setFont(new Font("Arial", Font.BOLD, 20));
		submit.setBounds(805, 705, 300, 50);
		submit.setText("Get Directions");
		this.add(submit);
		submit.addActionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		this.setFocusable(true);

		try {
            mapBackground = ImageIO.read(new File("images/legolandMap.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

		map = new Graph<Location>();
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
		Location psh = new Location("Pirate Shores", "psh", 430, 140);
		map.add(psh);
		Location sfc = new Location("Surfer’s Cove", "sfc", 320, 110);
		map.add(sfc);
		Location ftn = new Location("Fun Town", "ftn", 330, 180);
		map.add(ftn);
		Location wpk = new Location("Water Park", "wpk", 270, 50);
		map.add(wpk);
		Location lmw = new Location("The Lego Movie World", "lmw", 60, 230);
		map.add(lmw);
		Location msp = new Location("Model Shop", "msp", 570, 220);
		map.add(msp);
		Location igz = new Location("Imagination Zone", "igz", 560, 320);
		map.add(igz);
		Location rtm = new Location("Restrooms", "rtm", 360, 280);
		map.add(rtm);
		Location btl = new Location("Bridge @ the Lake", "btl", 400, 250);
		map.add(btl);

		map.addEdge(lmh, lch,5);
		map.addEdge(lmh, ent, 4);
		map.addEdge(lch, ent, 4);
		map.addEdge(ent,rtm,2);
		map.addEdge(rtm,btl,1);
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

	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1200,900);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.BOLD,15));
		g.drawString("Starting Location (Enter Abbreviation)",110,700);
		g.drawString("Destination (Enter Abbreviation)",460,700);
		drawLegend(g,875,100);
		g.drawImage(mapBackground,0,0,null);
		g.drawString("MouseX: " + mouseX + " MouseY: " + mouseY,40,640);

	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==submit){
			String startingAbbreviation = startInput.getText();
			String endingAbbreviation = endInput.getText();
			startInput.setText("");
			endInput.setText("");
		}
	}
	public void drawLegend(Graphics g, int x, int y){
		g.setFont(new Font("Arial",Font.PLAIN,20));
		g.drawString("Sea Life Aquarium = sla",x,y);
		g.drawString("Entrance = ent",x,y+20);
		g.drawString("Dino Valley = dnv",x,y+40);
		g.drawString("Legoland Main Hotel = lmh",x,y+60);
		g.drawString("Legoland Castle Hotel = lch",x,y+80);
		g.drawString("Ninjago World = nwd",x,y+100);
		g.drawString("Lego Ferrari = lgf",x,y+120);
		g.drawString("Miniland USA = usa",x,y+140);
		g.drawString("Land of Adventure = loa",x,y+160);
		g.drawString("Castle Hill = chl",x,y+180);
		g.drawString("Deep Sea Adventure = dsa",x,y+200);
		g.drawString("Pirate Shores = psh",x,y+220);
		g.drawString("Surfer's Cove = sfc",x,y+240);
		g.drawString("Fun Town = ftn",x,y+260);
		g.drawString("Water Park = wpk",x,y+280);
		g.drawString("Lego Movie World = lmw",x,y+300);
		g.drawString("Model Shop = msp",x,y+320);
		g.drawString("Imagination Zone = img",x,y+340);
		g.drawString("Restrooms = rtm",x,y+360);
		g.drawString("Bridge @ The Lake = btl",x,y+380);
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
