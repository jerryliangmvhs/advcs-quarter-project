import javax.swing.JFrame;


public class Runner {
  
	public static void main(String[] args) {

		JFrame frame = new JFrame("Jerry's Windmill Scenery");
		Screen sc = new Screen();
		
		frame.add(sc);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
    }
}
