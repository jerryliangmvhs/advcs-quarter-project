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
	private int playerRow = 17;
	private int playerCol = 17;
	private int renderDistance = 31; //101 is largest, must be ODD
	private int screenSize = 808;
	private int blockSize = screenSize/renderDistance;
	private int touristX = ((renderDistance-1)/2)*blockSize;
	private int touristY = ((renderDistance-1)/2)*blockSize;
	private int gridX = (((renderDistance-1)/2)*blockSize)-(playerCol*blockSize);
	private int gridY = (((renderDistance-1)/2)*blockSize)-(playerRow*blockSize);
	private boolean sunset = false;

	private int gridSize = 101; //blocks, DO NOT CHANGE
	private BufferedImage diamondHeadIcon, bigIslandVolcanoIcon, observatoryIcon, pearlHarborIcon, theMountainIcon, treeIcon, flowerIcon, grass, grassDark, water, sand, road, road2;
	private BufferedImage diamondHeadIconSunset, bigIslandVolcanoIconSunset, observatoryIconSunset, pearlHarborIconSunset, theMountainIconSunset, treeIconSunset, flowerIconSunset, grassSunset, grassDarkSunset, waterSunset, sandSunset, roadSunset, road2Sunset;

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
						if((int)(Math.random()*2)==1){
							String value = "road";
							map.put(key,new GridObject(value));
						}
						else{
							String value = "road2";
							map.put(key,new GridObject(value));
						}

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

			for(int i=0; i<gridSize; i++){
				for(int j=0; j<gridSize; j++){
					DLList<GridObject> g = map.get(new Location(j,i,blockSize));
					//if a grid box is land and doesn't already have an obstacle, there is a 0.25 chance there will be a tree.
					if(g.size()==1 && (g.get(0).getName().equals("land") || g.get(0).getName().equals("hills"))){
						if((int)(Math.random()*6)==3){
							map.put(new Location(j,i,blockSize),new GridObject("tree"));
						}
						else if((int)(Math.random()*6)==2){
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
			road = ImageIO.read(new File("icons/road1.png"));
			road2 = ImageIO.read(new File("icons/road2.png"));

			//sunset icons
			diamondHeadIconSunset = ImageIO.read(new File("sunsetIcons/diamond-head.png"));
			bigIslandVolcanoIconSunset = ImageIO.read(new File("sunsetIcons/big-island-volcano.png"));
			observatoryIconSunset = ImageIO.read(new File("sunsetIcons/observatory.png"));
			pearlHarborIconSunset = ImageIO.read(new File("sunsetIcons/pearl-harbor.png"));
			theMountainIconSunset = ImageIO.read(new File("sunsetIcons/the-mountain.png"));
			treeIconSunset = ImageIO.read(new File("sunsetIcons/tree.png"));
			flowerIconSunset = ImageIO.read(new File("sunsetIcons/yellow-flower.png"));
			grassSunset = ImageIO.read(new File("sunsetIcons/grass-block.png"));
			grassDarkSunset = ImageIO.read(new File("sunsetIcons/grass-block-dark.png"));
			waterSunset = ImageIO.read(new File("sunsetIcons/water.png"));
			sandSunset = ImageIO.read(new File("sunsetIcons/sand.png"));
			roadSunset = ImageIO.read(new File("sunsetIcons/road1.png"));
			road2Sunset = ImageIO.read(new File("sunsetIcons/road2.png"));

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
		int x = 0;
		int y = 0;
		for(int i=0; i<renderDistance+50; i++){
			for(int j=0; j<renderDistance+50; j++){
				if(sunset){
					g.drawImage(waterSunset,x,y,blockSize,blockSize,null);
				}
				else{
					g.drawImage(water,x,y,blockSize,blockSize,null);
				}
				x+=blockSize;
			}
			x=0;
			y+=blockSize;
		}

		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				Location key = new Location(i,j,blockSize);
				DLList<GridObject> block = map.get(key);
				for(int k=0; k<block.size(); k++){
					int blockX = key.getX()+gridX;
					int blockY = key.getY()+gridY;
					if(block.get(k).getName().equals("water")){
						if(sunset){
							g.drawImage(waterSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(water,blockX,blockY,blockSize,blockSize,null);
						}
						
					}
					if(block.get(k).getName().equals("land")){
						//g.setColor(new Color(60,150,10));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						if(sunset){
							g.drawImage(grassSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(grass,blockX,blockY,blockSize,blockSize,null);
						}
					}
					if(block.get(k).getName().equals("hills")){
						//g.setColor(new Color(50,130,5));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						if(sunset){
							g.drawImage(grassDarkSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(grassDark,blockX,blockY,blockSize,blockSize,null);
						}
						
					}
					if(block.get(k).getName().equals("road")){
						//g.setColor(new Color(75,80,95));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						if(sunset){
							g.drawImage(roadSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(road,blockX,blockY,blockSize,blockSize,null);
						}
						
					}
					if(block.get(k).getName().equals("road2")){
						//g.setColor(new Color(75,80,95));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						if(sunset){
							g.drawImage(road2Sunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(road2,blockX,blockY,blockSize,blockSize,null);
						}
						
					}
					if(block.get(k).getName().equals("sand")){
						//g.setColor(new Color(255,210,75));
						//g.fillRect(blockX,blockY,blockSize,blockSize);
						if(sunset){
							g.drawImage(sandSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(sand,blockX,blockY,blockSize,blockSize,null);
						}
					}
					if(block.get(k).getName().equals("diamondHead")){
						if(sunset){
							g.drawImage(diamondHeadIconSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(diamondHeadIcon,blockX,blockY,blockSize,blockSize,null);
						}
					}
					if(block.get(k).getName().equals("observatory")){
						if(sunset){
							g.drawImage(observatoryIconSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(observatoryIcon,blockX,blockY,blockSize,blockSize,null);
						}
					}
					if(block.get(k).getName().equals("theMountain")){
						if(sunset){
							g.drawImage(theMountainIconSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(theMountainIcon,blockX,blockY,blockSize,blockSize,null);
						}
					}
					if(block.get(k).getName().equals("pearlHarbor")){
						if(sunset){
							g.drawImage(pearlHarborIconSunset,blockX,blockY,blockSize,blockSize,null);
						}	
						else{
							g.drawImage(pearlHarborIcon,blockX,blockY,blockSize,blockSize,null);
						}
					}
					if(block.get(k).getName().equals("bigIslandVolcano")){
						if(sunset){
							g.drawImage(bigIslandVolcanoIconSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(bigIslandVolcanoIcon,blockX,blockY,blockSize,blockSize,null);
						}
					}
					if(block.get(k).getName().equals("tree")){
						if(sunset){
							g.drawImage(treeIconSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(treeIcon,blockX,blockY,blockSize,blockSize,null);
						}
					}
					if(block.get(k).getName().equals("flower")){
						if(sunset){
							g.drawImage(flowerIconSunset,blockX,blockY,blockSize,blockSize,null);
						}
						else{
							g.drawImage(flowerIcon,blockX,blockY,blockSize,blockSize,null);
						}
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
		System.out.println(e.getKeyCode());

		if(e.getKeyCode()==37 && player.canMoveLeft()){
			gridX+=blockSize;
			playerCol--;
		}
		if(e.getKeyCode()==38 && player.canMoveUp()){
			gridY+=blockSize;
			playerRow--;
		}
		if(e.getKeyCode()==39 && player.canMoveRight()){
			gridX-=blockSize;
			playerCol++;
		}
		if(e.getKeyCode()==40 && player.canMoveDown()){
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
		if(e.getKeyCode()==59){
			if(sunset){
				sunset = false;
			}
			else if(!sunset){
				sunset = true;
			}
		}
		//1-8 is 49-56
		//Island 1 (22,7)
		//Island 2 (16,19)
		//Island 3 (26,36)
		//Island 4 (36,52)
		//Island 5 (49,56)
		//Island 6 (48,71)
		//Island 7 (58,62)
		//Island 8 (74,77)
		if(e.getKeyCode()==49){
			playerRow = 22;
			playerCol = 7;
			player.setRow(22);
			player.setCol(7);
		}
		if(e.getKeyCode()==50){
			playerRow = 16;
			playerCol = 19;
			player.setRow(16);
			player.setCol(19);
		}
		if(e.getKeyCode()==51){
			playerRow = 26;
			playerCol = 36;
			player.setRow(26);
			player.setCol(36);
		}
		if(e.getKeyCode()==52){
			playerRow = 36;
			playerCol = 52;
			player.setRow(36);
			player.setCol(52);
		}
		if(e.getKeyCode()==53){
			playerRow = 49;
			playerCol = 56;
			player.setRow(49);
			player.setCol(56);
		}
		if(e.getKeyCode()==54){
			playerRow = 48;
			playerCol = 71;
			player.setRow(48);
			player.setCol(71);
		}
		if(e.getKeyCode()==55){
			playerRow = 58;
			playerCol = 62;
			player.setRow(58);
			player.setCol(62);
		}
		if(e.getKeyCode()==56){
			playerRow = 74;
			playerCol = 77;
			player.setRow(74);
			player.setCol(77);
		}
		blockSize = screenSize/renderDistance;
		player.setSize(screenSize/renderDistance);
		player.setX(((renderDistance-1)/2)*blockSize);
		player.setY(((renderDistance-1)/2)*blockSize);
		player.setRow(playerRow);
		player.setCol(playerCol);
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
