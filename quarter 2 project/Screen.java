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
	private Tourist player;
	private int playerRow = 38;
	private int playerCol = 38;
	private int renderDistance = 31; //101 is largest, must be ODD
	private int screenSize = 808;
	private int blockSize = screenSize/renderDistance;
	private int touristX = ((renderDistance-1)/2)*blockSize;
	private int touristY = ((renderDistance-1)/2)*blockSize;
	private int gridX = (((renderDistance-1)/2)*blockSize)-(playerCol*blockSize);
	private int gridY = (((renderDistance-1)/2)*blockSize)-(playerRow*blockSize);


	private int gridSize = 101; //blocks, DO NOT CHANGE
	private BufferedImage diamondHeadIcon, bigIslandVolcanoIcon, observatoryIcon, pearlHarborIcon, theMountainIcon, treeIcon, flowerIcon, grass, grassDark, water, sand, road;

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
					Location key = new Location(row,col,blockSize);
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
			map.put(new Location(74,75,blockSize), new GridObject("bigIslandVolcano"));
			map.put(new Location(31,40,blockSize), new GridObject("diamondHead"));
			map.put(new Location(50,75,blockSize), new GridObject("observatory"));
			map.put(new Location(29,35,blockSize), new GridObject("pearlHarbor"));
			map.put(new Location(18,18,blockSize), new GridObject("theMountain"));

			for(int i=0; i<100; i++){
				for(int j=0; j<100; j++){
					DLList<GridObject> g = map.get(new Location(j,i,blockSize));
					//if a grid box is land and doesn't already have an obstacle, there is a 0.25 chance there will be a tree.
					if(g.size()==1 && (g.get(0).getName().equals("land") || g.get(0).getName().equals("hills"))){
						if((int)(Math.random()*6)==3){
							map.put(new Location(j,i,blockSize),new GridObject("tree"));
						}
						if((int)(Math.random()*6)==2){
							map.put(new Location(j,i,blockSize),new GridObject("flower"));
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
            diamondHeadIcon = ImageIO.read(new File("icons/diamond-head.png"));
			bigIslandVolcanoIcon = ImageIO.read(new File("icons/big-island-volcano.png"));
			observatoryIcon = ImageIO.read(new File("icons/observatory.png"));
			pearlHarborIcon = ImageIO.read(new File("icons/pearl-harbor.png"));
			theMountainIcon = ImageIO.read(new File("icons/the-mountain.png"));
			treeIcon = ImageIO.read(new File("icons/tree.png"));
			flowerIcon = ImageIO.read(new File("icons/yellow-flower.png"));
			grass = ImageIO.read(new File("icons/grass-block.png"));
			grassDark = ImageIO.read(new File("icons/grass-block-dark.png"));
			water = ImageIO.read(new File("icons/water.png"));
			sand = ImageIO.read(new File("icons/sand.png"));
			road = ImageIO.read(new File("icons/road.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

		player = new Tourist(playerRow,playerCol,touristX,touristY,blockSize,map);
		this.setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);

		
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(screenSize,screenSize);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(new Color(20,130,195));
		g.fillRect(0,0,screenSize,screenSize);
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				Location key = new Location(i,j,blockSize);
				DLList<GridObject> block = map.get(key);
				for(int k=0; k<block.size(); k++){
					int blockX = key.getX()+gridX;
					int blockY = key.getY()+gridY;
					if(block.get(k).getName().equals("water")){
						if(renderDistance<=11){
							g.drawImage(water,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.setColor(new Color(20,130,195));
							g.fillRect(blockX,blockY,blockSize,blockSize);
						}
					}
					if(block.get(k).getName().equals("land")){
						//g.setColor(new Color(60,150,10));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						g.drawImage(grass,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("hills")){
						//g.setColor(new Color(50,130,5));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						g.drawImage(grassDark,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("road")){
						//g.setColor(new Color(75,80,95));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						g.drawImage(road,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("sand")){
						//g.setColor(new Color(255,210,75));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						g.drawImage(sand,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("diamondHead")){
						g.drawImage(diamondHeadIcon,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("observatory")){
						g.drawImage(observatoryIcon,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("theMountain")){
						g.drawImage(theMountainIcon,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("pearlHarbor")){
						g.drawImage(pearlHarborIcon,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("bigIslandVolcano")){
						g.drawImage(bigIslandVolcanoIcon,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("tree")){
						g.drawImage(treeIcon,blockX,blockY,blockSize,blockSize,null);
					}
					if(block.get(k).getName().equals("flower")){
						g.drawImage(flowerIcon,blockX,blockY,blockSize,blockSize,null);
					}
				}
			}
		}
		g.setColor(new Color(220,30,240));
		g.fillRect(player.getX(),player.getY(),player.getSize(),player.getSize());
	}

	public void actionPerformed(ActionEvent e){}
	public void keyPressed(KeyEvent e){
		//left is 37, up is 38, right is 39, down is 40.
		//91 is zoom out, 93 is zoom in
		//System.out.println(e.getKeyCode());

		if(e.getKeyCode()==37){
			gridX+=blockSize;
			playerCol--;
		}
		if(e.getKeyCode()==38){
			gridY+=blockSize;
			playerRow--;
		}
		if(e.getKeyCode()==39){
			gridX-=blockSize;
			playerCol++;
		}
		if(e.getKeyCode()==40){
			gridY-=blockSize;
			playerRow++;
		}
		if(e.getKeyCode()==91 && renderDistance <95){
			renderDistance+=6;
		
			System.out.println("Zooming Out");
			System.out.println("Render Distance: " + renderDistance + " chunks.");
		}
		if(e.getKeyCode()==93 && renderDistance >=11){
			renderDistance-=6;
			
			System.out.println("Zooming In");
			System.out.println("Render Distance: " + renderDistance + " chunks.");
		}
		blockSize = screenSize/renderDistance;
		player.setSize(screenSize/renderDistance);
		player.setX(((renderDistance-1)/2)*blockSize);
		player.setY(((renderDistance-1)/2)*blockSize);
		gridX = (((renderDistance-1)/2)*blockSize)-(playerCol*blockSize);
		gridY = (((renderDistance-1)/2)*blockSize)-(playerRow*blockSize);
		System.out.println("Player Row: " + playerRow + " Player Column: " + playerCol);
		repaint();
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e){
		System.out.println("X: " + e.getX() + " Y: " + e.getY());
	}
}
