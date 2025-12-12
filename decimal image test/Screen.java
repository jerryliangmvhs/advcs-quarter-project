import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class Screen extends JPanel {
	private BufferedImage image;
	public Screen(){
		try {
            image = ImageIO.read(new File("image.png"));
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
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform tx = new AffineTransform();
		tx.translate(5.5,5.5);
		tx.scale(50.5,50.5);
		g2.drawImage(image,tx,null);
	}
}
