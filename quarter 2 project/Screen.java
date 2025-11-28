import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;



public class Screen extends JPanel {
	public Screen(){
		
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(700,700);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
}
