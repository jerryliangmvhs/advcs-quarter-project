import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;

public class Screen extends JPanel{
	private MyHashTable<Location,GridObject> map;

	public Screen(){
		map = new MyHashTable<Location,GridObject>();
		try {
			Scanner scan = new Scanner(new FileReader("Map.txt"));

			int row=0; //Each line in your text file is a row
			while (scan.hasNextLine()){
				//Reads in one line at a time.
				String line = scan.nextLine();

				//if there's a space between each number in your text file
				String[] numberArray = line.split(" ");  
				for(int col=0; col<numberArray.length; col++){  //Each number in the line is a column
					Location key = new Location(row,col);
					if(numberArray[col].equals("3")){
						String value = "water";
						map.put(key,new GridObject(value));
					}
					else if(numberArray[col].equals("0")){
						String value = "land";
						map.put(key,new GridObject(value));
					}
				}
				row++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(700,700);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0; i<100; i++){
			for(int j=0; j<100; j++){
				Location key = new Location(i,j);
				DLList<GridObject> block = map.get(key);
				for(int k=0; k<block.size(); k++){
					if(block.get(k).getName().equals("water")){
						g.setColor(Color.BLUE);
						g.fillRect(key.getX(),key.getY(),7,7);
					}
					if(block.get(k).getName().equals("land")){
						g.setColor(Color.GREEN);
						g.fillRect(key.getX(),key.getY(),7,7);
					}
				}
			}
		}
	}
}
