import javax.swing.JPanel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Screen extends JPanel implements ActionListener, KeyListener, MouseListener{
	private MyHashTable<Location,GridObject> map;
	private Player player;
	private BufferedImage diamondHeadIcon, bigIslandVolcanoIcon, observatoryIcon, pearlHarborIcon, theMountainIcon;

	public Screen(){
		this.setLayout(null);
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
					else if(numberArray[col].equals("5")){
						String value = "hills";
						map.put(key,new GridObject(value));
					}
					else if(numberArray[col].equals("6")){
						String value = "road";
						map.put(key,new GridObject(value));
					}
					else if(numberArray[col].equals("1")){
						String value = "sand";
						map.put(key,new GridObject(value));
					}
				}
				row++;
			}
			map.put(new Location(75,75), new GridObject("bigIslandVolcano"));
			map.put(new Location(28,42), new GridObject("diamondHead"));
			map.put(new Location(50,75), new GridObject("observatory"));
			map.put(new Location(30,36), new GridObject("pearlHarbor"));
			map.put(new Location(18,18), new GridObject("theMountain"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
            diamondHeadIcon = ImageIO.read(new File("icons/diamond-head.png"));
			bigIslandVolcanoIcon = ImageIO.read(new File("icons/big-island-volcano.png"));
			observatoryIcon = ImageIO.read(new File("icons/observatory.png"));
			pearlHarborIcon = ImageIO.read(new File("icons/pearl-harbor.png"));
			theMountainIcon = ImageIO.read(new File("icons/the-mountain.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

		player = new Player(75,75,map);
		this.setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);

		
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
						g.setColor(new Color(20,130,195));
						g.fillRect(key.getX(),key.getY(),7,7);
					}
					if(block.get(k).getName().equals("land")){
						g.setColor(new Color(60,150,10));
						g.fillRect(key.getX(),key.getY(),7,7);
					}
					if(block.get(k).getName().equals("hills")){
						g.setColor(new Color(50,130,5));
						g.fillRect(key.getX(),key.getY(),7,7);
					}
					if(block.get(k).getName().equals("road")){
						g.setColor(new Color(75,80,95));
						g.fillRect(key.getX(),key.getY(),7,7);
					}
					if(block.get(k).getName().equals("sand")){
						g.setColor(new Color(255,210,75));
						g.fillRect(key.getX(),key.getY(),7,7);
					}
					if(block.get(k).getName().equals("diamondHead")){
						g.drawImage(diamondHeadIcon,key.getX(),key.getY(),7,7,null);
					}
					if(block.get(k).getName().equals("observatory")){
						g.drawImage(observatoryIcon,key.getX(),key.getY(),7,7,null);
					}
					if(block.get(k).getName().equals("theMountain")){
						g.drawImage(theMountainIcon,key.getX(),key.getY(),7,7,null);
					}
					if(block.get(k).getName().equals("pearlHarbor")){
						g.drawImage(pearlHarborIcon,key.getX(),key.getY(),7,7,null);
					}
					if(block.get(k).getName().equals("bigIslandVolcano")){
						g.drawImage(bigIslandVolcanoIcon,key.getX(),key.getY(),7,7,null);
					}
				}
			}
		}
		g.setColor(Color.RED);
		g.fillRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
	}

	public void actionPerformed(ActionEvent e){

	}
	
	public void keyPressed(KeyEvent e){
		//up is 38, right is 39, down is 40, left is 37
		System.out.println(e.getKeyCode());

		if(e.getKeyCode()==37){
			player.moveLeft();
		}
		if(e.getKeyCode()==38){
			player.moveUp();
		}
		if(e.getKeyCode()==39){
			player.moveRight();
		}
		if(e.getKeyCode()==40){
			player.moveDown();
		}
		repaint();
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e){
		System.out.println("X: " + e.getX() + "Y: " + e.getY());
	}

}
