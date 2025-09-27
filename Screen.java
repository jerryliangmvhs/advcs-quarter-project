import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Screen extends JPanel {
	private BufferedImage background;
	private BufferedImage chickenImage;
	private DLList<Chicken> chicken;
	public Screen(){
		chicken = new DLList<Chicken>();
		try {
            background = ImageIO.read(new File("images/background.png"));
			chickenImage = ImageIO.read(new File("images/chicken.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		for(int i=0; i<5; i++){
			int x = (int)(Math.random()*600)+50;
			int y = (int)(Math.random()*100)+350;
			chicken.add(new Chicken(chickenImage,x,y));
		}
		
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800,600);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background,0,0,800,600,null);

		for(int i=0; i<chicken.size(); i++){
			chicken.get(i).drawMe(g);
			System.out.println("X: " + chicken.get(i).getX() + " Y: " + chicken.get(i).getY());
		}
	}
}
