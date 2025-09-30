import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


public class Screen extends JPanel implements ActionListener, MouseListener {

	private BufferedImage background, chickenImage, cowImage, farmerImage, sheepImage, pigImage, scarecrowImage;

	private DLList<Chicken> chicken;
	private DLList<Cow> cow;
	private DLList<Sheep> sheep;
	private DLList<Pig> pig;
	private DLList<Farmer> farmer;
	private DLList<Scarecrow> scarecrow;

	private JButton addChicken;
	private JButton removeChicken;
	private JButton addCow;
	private JButton removeCow;
	private JButton addFarmer;
	private JButton removeFarmer;
	private JButton addPig;
	private JButton removePig;
	private JButton addScarecrow;
	private JButton removeScarecrow;
	private JButton addSheep;
	private JButton removeSheep;
	private JButton scramble;
	private JButton clear;



	public Screen(){
		this.setLayout(null);
		addMouseListener(this);

		addChicken = new JButton("Add Chicken");
		addChicken.setBounds(825,50,150,30);
		addChicken.addActionListener(this);
		this.add(addChicken);

		removeChicken = new JButton("Remove Chicken");
		removeChicken.setBounds(825,85,150,30);
		removeChicken.addActionListener(this);
		this.add(removeChicken);

		addCow = new JButton("Add Cow");
		addCow.setBounds(825,120,150,30);
		addCow.addActionListener(this);
		this.add(addCow);

		removeCow = new JButton("Remove Cow");
		removeCow.setBounds(825,155,150,30);
		removeCow.addActionListener(this);
		this.add(removeCow);

		addFarmer = new JButton("Add Farmer");
		addFarmer.setBounds(825,190,150,30);
		addFarmer.addActionListener(this);
		this.add(addFarmer);

		removeFarmer = new JButton("Remove Farmer");
		removeFarmer.setBounds(825,225,150,30);
		removeFarmer.addActionListener(this);
		this.add(removeFarmer);

		addPig = new JButton("Add Pig");
		addPig.setBounds(825,260,150,30);
		addPig.addActionListener(this);
		this.add(addPig);

		removePig = new JButton("Remove Pig");
		removePig.setBounds(825,295,150,30);
		removePig.addActionListener(this);
		this.add(removePig);

		addScarecrow = new JButton("Add Scarecrow");
		addScarecrow.setBounds(825,335,150,30);
		addScarecrow.addActionListener(this);
		this.add(addScarecrow);

		removeScarecrow = new JButton("Remove Scarecrow");
		removeScarecrow.setBounds(825,370,150,30);
		removeScarecrow.addActionListener(this);
		this.add(removeScarecrow);

		addSheep = new JButton("Add Sheep");
		addSheep.setBounds(825,405,150,30);
		addSheep.addActionListener(this);
		this.add(addSheep);

		removeSheep = new JButton("Remove Sheep");
		removeSheep.setBounds(825,440,150,30);
		removeSheep.addActionListener(this);
		this.add(removeSheep);

		scramble = new JButton("Randomize");
		scramble.setBounds(825,475,150,30);
		scramble.addActionListener(this);
		this.add(scramble);

		clear = new JButton("Clear");
		clear.setBounds(825,510,150,30);
		clear.addActionListener(this);
		this.add(clear);




		chicken = new DLList<Chicken>();
		cow = new DLList<Cow>();
		sheep = new DLList<Sheep>();
		pig = new DLList<Pig>();
		farmer = new DLList<Farmer>();
		scarecrow = new DLList<Scarecrow>();

		try {
            background = ImageIO.read(new File("images/background.png"));
			chickenImage = ImageIO.read(new File("images/chicken.png"));
			cowImage = ImageIO.read(new File("images/cow.png"));
			farmerImage = ImageIO.read(new File("images/farmer.png"));
			sheepImage = ImageIO.read(new File("images/sheep.png"));
			pigImage = ImageIO.read(new File("images/pig.png"));
			scarecrowImage = ImageIO.read(new File("images/scarecrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		this.setFocusable(true);

		
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1000,600);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1000,600);
		g.drawImage(background,0,0,800,600,null);

		for(int i=0; i<chicken.size(); i++){
			chicken.get(i).drawMe(g);
		}
		for(int i=0; i<cow.size(); i++){
			cow.get(i).drawMe(g);
		}
		for(int i=0; i<farmer.size(); i++){
			farmer.get(i).drawMe(g);
		}
		for(int i=0; i<pig.size(); i++){
			pig.get(i).drawMe(g);
		}
		for(int i=0; i<scarecrow.size(); i++){
			scarecrow.get(i).drawMe(g);
		}
		for(int i=0; i<sheep.size(); i++){
			sheep.get(i).drawMe(g);
		}
	}

	public void actionPerformed(ActionEvent e){
		int x = (int)(Math.random()*600)+50;
		int y = (int)(Math.random()*150)+300;
		if(e.getSource()==addSheep){
			sheep.add(new Sheep(sheepImage,x,y));
		}
		if(e.getSource()==removeSheep && sheep.size()>=1){
			sheep.remove((int)(Math.random()*sheep.size()));
		}
		if(e.getSource()==addChicken){
			chicken.add(new Chicken(chickenImage,x,y));
		}
		if(e.getSource()==removeChicken && chicken.size() >=1){
			chicken.remove((int)(Math.random()*chicken.size()));
		}
		if(e.getSource()==addCow){
			cow.add(new Cow(cowImage,x,y));
		}
		if(e.getSource()==removeCow && cow.size()>=1){
			cow.remove((int)(Math.random()*cow.size()));
		}
		if(e.getSource()==addFarmer){
			farmer.add(new Farmer(farmerImage,x,y));
		}
		if(e.getSource()==removeFarmer && farmer.size()>=1){
			farmer.remove((int)(Math.random()*farmer.size()));
		}
		if(e.getSource()==addScarecrow){
			scarecrow.add(new Scarecrow(scarecrowImage,x,y));
		}
		if(e.getSource()==removeScarecrow && scarecrow.size()>=1){
			scarecrow.remove((int)(Math.random()*scarecrow.size()));
		}
		if(e.getSource()==addPig){
			pig.add(new Pig(pigImage,x,y));
		}
		if(e.getSource()==removePig && pig.size()>=1){
			pig.remove((int)(Math.random()*pig.size()));
		}
		if(e.getSource()==scramble){
			for(int i=0; i<chicken.size(); i++){
				int randX = (int)(Math.random()*600)+50;
				int randY = (int)(Math.random()*150)+300;
				chicken.get(i).setX(randX);
				chicken.get(i).setY(randY);
			}
			for(int i=0; i<cow.size(); i++){
				int randX = (int)(Math.random()*600)+50;
				int randY = (int)(Math.random()*150)+300;
				cow.get(i).setX(randX);
				cow.get(i).setY(randY);
			}
			for(int i=0; i<farmer.size(); i++){
				int randX = (int)(Math.random()*600)+50;
				int randY = (int)(Math.random()*150)+300;
				farmer.get(i).setX(randX);
				farmer.get(i).setY(randY);
			}
			for(int i=0; i<pig.size(); i++){
				int randX = (int)(Math.random()*600)+50;
				int randY = (int)(Math.random()*150)+300;
				pig.get(i).setX(randX);
				pig.get(i).setY(randY);
			}
			for(int i=0; i<scarecrow.size(); i++){
				int randX = (int)(Math.random()*600)+50;
				int randY = (int)(Math.random()*150)+300;
				scarecrow.get(i).setX(randX);
				scarecrow.get(i).setY(randY);
			}
			for(int i=0; i<sheep.size(); i++){
				int randX = (int)(Math.random()*600)+50;
				int randY = (int)(Math.random()*150)+300;
				sheep.get(i).setX(randX);
				sheep.get(i).setY(randY);
			}
		}
		if(e.getSource()==clear){
			pig.clear();
			cow.clear();
			sheep.clear();
			scarecrow.clear();
			farmer.clear();
			chicken.clear();
		}
		repaint();
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		for(int i=0; i<chicken.size(); i++){
			if(chicken.get(i).mouseEvent(x,y)){
				
			}
		}
		for(int i=0; i<cow.size(); i++){
			if(cow.get(i).mouseEvent(x,y)){

			}
		}
		for(int i=0; i<farmer.size(); i++){
			if(farmer.get(i).mouseEvent(x,y)){

			}
		}
		for(int i=0; i<pig.size(); i++){
			if(pig.get(i).mouseEvent(x,y)){

			}
		}
		for(int i=0; i<sheep.size(); i++){
			if(sheep.get(i).mouseEvent(x,y)){

			}
		}
		for(int i=0; i<scarecrow.size(); i++){
			if(scarecrow.get(i).mouseEvent(x,y)){

			}
		}

		repaint();
	}
    public void mouseReleased(MouseEvent e) {}


    public void mouseEntered(MouseEvent e) {}


    public void mouseExited(MouseEvent e) {}


    public void mouseClicked(MouseEvent e) {}

}
