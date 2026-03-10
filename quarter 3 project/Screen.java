import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;


public class Screen extends JPanel implements ActionListener, KeyListener, MouseListener{
	public Screen(){
		addMouseListener(this);
		addKeyListener(this);
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1200,900);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	public void actionPerformed(ActionEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

}
