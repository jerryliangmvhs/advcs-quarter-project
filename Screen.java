import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Screen extends JPanel {
	private BufferedImage background, chickenImage, cowImage, farmerImage, sheepImage, pigImage, scarecrowImage;

	private DLList<Chicken> chicken;
	private DLList<Cow> cow;
	private DLList<Sheep> sheep;
	private DLList<Pig> pig;
	private DLList<Farmer> farmer;
	private DLList<Scarecrow> scarecrow;

	public Screen(){
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
		//int x = (int)(Math.random()*600)+50;
		//int y = (int)(Math.random()*100)+350;
		
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800,600);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background,0,0,800,600,null);
	}
}
