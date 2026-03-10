import javax.swing.JFrame;

public class Runner {
  
	public static void main(String[] args) {

		JFrame frame = new JFrame("Quarter 2 Project - Hawaii Map");
		Screen sc = new Screen();
		
		frame.add(sc);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
    }
}
