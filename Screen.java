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
import javax.swing.JButton;


public class Screen extends JPanel implements ActionListener {
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

		addChicken = new JButton("Add Chicken");
		addChicken.setBounds(850,50,100,30);
		addChicken.addActionListener(this);
		this.add(addChicken);

		removeChicken = new JButton("Remove Chicken");
		removeChicken.setBounds(850,85,100,30);
		removeChicken.addActionListener(this);
		this.add(removeChicken);

		addCow = new JButton("Add Cow");
		addCow.setBounds(850,120,100,30);
		addCow.addActionListener(this);
		this.add(addCow);

		removeCow = new JButton("Remove Cow");
		removeCow.setBounds(850,155,100,30);
		removeCow.addActionListener(this);
		this.add(removeCow);

		addFarmer = new JButton("Add Farmer");
		addFarmer.setBounds(850,190,100,30);
		addFarmer.addActionListener(this);
		this.add(addFarmer);

		removeFarmer = new JButton("Remove Farmer");
		removeFarmer.setBounds(850,225,100,30);
		removeFarmer.addActionListener(this);
		this.add(removeFarmer);

		addPig = new JButton("Add Pig");
		addPig.setBounds(850,260,100,30);
		addPig.addActionListener(this);
		this.add(addPig);

		removePig = new JButton("Remove Pig");
		removePig.setBounds(850,295,100,30);
		removePig.addActionListener(this);
		this.add(removePig);

		addScarecrow = new JButton("Add Scarecrow");
		addScarecrow.setBounds(850,335,100,30);
		addScarecrow.addActionListener(this);
		this.add(addScarecrow);

		removeScarecrow = new JButton("Remove Scarecrow");
		removeScarecrow.setBounds(850,370,100,30);
		removeScarecrow.addActionListener(this);
		this.add(removeScarecrow);

		addSheep = new JButton("Add Sheep");
		addSheep.setBounds(850,405,100,30);
		addSheep.addActionListener(this);
		this.add(addSheep);

		removeSheep = new JButton("Remove Sheep");
		removeSheep.setBounds(850,440,100,30);
		removeSheep.addActionListener(this);
		this.add(removeSheep);

		scramble = new JButton("Scramble");
		scramble.setBounds(850,475,100,30);
		scramble.addActionListener(this);
		this.add(scramble);

		clear = new JButton("Clear");
		clear.setBounds(850,510,100,30);
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
	}

	public void actionPerformed(ActionEvent e){


		repaint();
	}
}
