import javax.swing.JFrame;


public class Runner {
  
	public static void main(String[] args) {

		JFrame frame = new JFrame("Jerry's Windmill Scenery");
		Screen sc = new Screen();
		
		DLList<Integer> nums = new DLList<Integer>();
		for(int i=0; i<10; i++){
			nums.add(i);
		}
		nums.remove(8);
		System.out.println(nums);
		frame.add(sc);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
    }
}
