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
	public Screen(){
		try {
            background = ImageIO.read(new File("strawberry.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800,600);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
}
