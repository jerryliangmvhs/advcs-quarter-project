import javax.swing.JPanel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;

public class Screen extends JPanel implements ActionListener, KeyListener, MouseListener, MouseWheelListener{
	private MyHashTable<Location,GridObject> map;
	private JButton zoomOut;
	private JButton zoomIn;
	private Tourist player;
	private Chicken chicken1;
	private Pig pig1;
	private Car car1,car2,car3,car4,car5,car6;
	private Villager villager1, villager2, villager3, villager4, villager5, villager6, villager7, villager8;
	
	private Font minecraftFive;
	private Font minecraftTen;

	private int playerRow = 27;
	private int playerCol = 36;
	private Color buttonColor = new Color(40, 180, 25);
	private Color buttonColorSunset = new Color(130, 120, 250);
	private int renderDistance = 35; //101 is largest, must be ODD
	private int screenSize = 800;
	private int blockSize = screenSize/renderDistance;
	private int touristX = ((renderDistance-1)/2)*blockSize;
	private int touristY = ((renderDistance-1)/2)*blockSize;
	private int gridX = (((renderDistance-1)/2)*blockSize)-(playerCol*blockSize);
	private int gridY = (((renderDistance-1)/2)*blockSize)-(playerRow*blockSize);
	private boolean sunset = false;
	private String currentIsland = "O'ahu";

	private int gridSize = 101; //blocks, DO NOT CHANGE
	private BufferedImage diamondHeadIcon, bigIslandVolcanoIcon, observatoryIcon, pearlHarborIcon, theMountainIcon, treeIcon, flowerIcon, grass, grassDark, water, sand, road, road2;
	private BufferedImage diamondHeadIconSunset, bigIslandVolcanoIconSunset, observatoryIconSunset, pearlHarborIconSunset, theMountainIconSunset, treeIconSunset, flowerIconSunset, grassSunset, grassDarkSunset, waterSunset, sandSunset, roadSunset, road2Sunset;

	private BufferedImage playerSprite;
	private BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, right1, right2;
	private BufferedImage chicken, pig, car, villager;


	@SuppressWarnings("unchecked")
	public Screen(){
		this.setLayout(null);

		try{
			minecraftFive = Font.createFont(Font.TRUETYPE_FONT,new File("fonts/minecraft-five.ttf")).deriveFont(7.5f);
			minecraftTen = Font.createFont(Font.TRUETYPE_FONT,new File("fonts/minecraft-ten.ttf")).deriveFont(30f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(minecraftFive);
			ge.registerFont(minecraftTen);
		} catch (IOException | FontFormatException e){
			e.printStackTrace();
  		}

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

			//player sprites
			up1 = ImageIO.read(new File("sprites/up1.png"));
			up2 = ImageIO.read(new File("sprites/up2.png"));
			up3 = ImageIO.read(new File("sprites/up3.png"));
			down1 = ImageIO.read(new File("sprites/down1.png"));
			down2 = ImageIO.read(new File("sprites/down2.png"));
			down3 = ImageIO.read(new File("sprites/down3.png"));
			left1 = ImageIO.read(new File("sprites/left1.png"));
			left2 = ImageIO.read(new File("sprites/left2.png"));
			right1 = ImageIO.read(new File("sprites/right1.png"));
			right2 = ImageIO.read(new File("sprites/right2.png"));

			chicken = ImageIO.read(new File("sprites/chicken.png"));
			pig = ImageIO.read(new File("sprites/pig.png"));
			car = ImageIO.read(new File("sprites/car.png"));
			villager = ImageIO.read(new File("sprites/villager.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

		player = new Tourist(playerRow,playerCol,touristX,touristY,blockSize,map,this);
		chicken1 = new Chicken(16,19,blockSize,map,this);
		pig1 = new Pig(70,70,blockSize,map,this);
		car1 = new Car(27,34,blockSize,map,this);
		car2 = new Car(17,21,blockSize,map,this);
		car3 = new Car(37,53,blockSize,map,this);
		car4 = new Car(51,54,blockSize,map,this);
		car5 = new Car(47,73,blockSize,map,this);
		car6 = new Car(67,74,blockSize,map,this);

		villager1 = new Villager(23,7,blockSize,map,this);
		villager2 = new Villager(16,19,blockSize,map,this);
		villager3 = new Villager(24,36,blockSize,map,this);
		villager4 = new Villager(36,57,blockSize,map,this);
		villager5 = new Villager(50,58,blockSize,map,this);
		villager6 = new Villager(49,73,blockSize,map,this);
		villager7 = new Villager(58,62,blockSize,map,this);
		villager8 = new Villager(73,77,blockSize,map,this);

		playerSprite = down1;

		Thread playerThread = new Thread(player);
		playerThread.start();
		Thread chickenThread1 = new Thread(chicken1);
		chickenThread1.start();
		Thread pigThread1 = new Thread(pig1);
		pigThread1.start();
		Thread carThread1 = new Thread(car1);
		carThread1.start();
		Thread carThread2 = new Thread(car2);
		carThread2.start();
		Thread carThread3 = new Thread(car3);
		carThread3.start();
		Thread carThread4 = new Thread(car4);
		carThread4.start();
		Thread carThread5 = new Thread(car5);
		carThread5.start();
		Thread carThread6 = new Thread(car6);
		carThread6.start();

		Thread villagerThread1 = new Thread(villager1);
		villagerThread1.start();
		Thread villagerThread2 = new Thread(villager2);
		villagerThread2.start();
		Thread villagerThread3 = new Thread(villager3);
		villagerThread3.start();
		Thread villagerThread4 = new Thread(villager4);
		villagerThread4.start();
		Thread villagerThread5 = new Thread(villager5);
		villagerThread5.start();
		Thread villagerThread6 = new Thread(villager6);
		villagerThread6.start();
		Thread villagerThread7 = new Thread(villager7);
		villagerThread7.start();
		Thread villagerThread8 = new Thread(villager8);
		villagerThread8.start();

		zoomIn = new JButton("+");
		zoomIn.setBounds(715,20,75,75);
		zoomIn.addActionListener(this);
		zoomIn.setMargin(new Insets(0,0,0,0));
		zoomIn.setFont(minecraftTen);
		zoomIn.setFocusable(false);
		zoomIn.setBackground(buttonColor);
		zoomIn.setBorderPainted(false);
		zoomIn.setOpaque(true);
		this.add(zoomIn);

		zoomOut = new JButton("-");
		zoomOut.setBounds(715,105,75,75);
		zoomOut.setFocusable(false);
		zoomOut.addActionListener(this);
		zoomOut.setFont(minecraftTen);
		zoomOut.setBackground(buttonColor);
		zoomOut.setBorderPainted(false);
		zoomOut.setOpaque(true);
		this.add(zoomOut);

		/*addChicken.setBackground(buttonColor);
		addChicken.setOpaque(true);
		addChicken.setBorderPainted(false); */
		
		this.setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
		addMouseWheelListener(this);

		
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(screenSize,screenSize);
	}
	
	@Override
	 @SuppressWarnings("unchecked")
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

		//g.setColor(new Color(220,30,240));
		//g.fillRect(player.getX(),player.getY(),player.getSize(),player.getSize());
		g.drawImage(playerSprite,player.getX(),player.getY(),player.getSize(),player.getSize(),null);
		g.drawImage(chicken,chicken1.getX()+gridX,chicken1.getY()+gridY,chicken1.getSize(),chicken1.getSize(),null);
		g.drawImage(pig,pig1.getX()+gridX,pig1.getY()+gridY,pig1.getSize(),pig1.getSize(),null);

		g.drawImage(car,car1.getX()+gridX,car1.getY()+gridY,car1.getSize(),car1.getSize(),null);
		g.drawImage(car,car2.getX()+gridX,car2.getY()+gridY,car2.getSize(),car2.getSize(),null);
		g.drawImage(car,car3.getX()+gridX,car3.getY()+gridY,car3.getSize(),car3.getSize(),null);
		g.drawImage(car,car4.getX()+gridX,car4.getY()+gridY,car4.getSize(),car4.getSize(),null);
		g.drawImage(car,car5.getX()+gridX,car5.getY()+gridY,car5.getSize(),car5.getSize(),null);
		g.drawImage(car,car6.getX()+gridX,car6.getY()+gridY,car6.getSize(),car6.getSize(),null);

		g.drawImage(villager,villager1.getX()+gridX,villager1.getY()+gridY,villager1.getSize(),villager1.getSize(),null);
		g.drawImage(villager,villager2.getX()+gridX,villager2.getY()+gridY,villager2.getSize(),villager2.getSize(),null);
		g.drawImage(villager,villager3.getX()+gridX,villager3.getY()+gridY,villager3.getSize(),villager3.getSize(),null);
		g.drawImage(villager,villager4.getX()+gridX,villager4.getY()+gridY,villager4.getSize(),villager4.getSize(),null);
		g.drawImage(villager,villager5.getX()+gridX,villager5.getY()+gridY,villager5.getSize(),villager5.getSize(),null);
		g.drawImage(villager,villager6.getX()+gridX,villager6.getY()+gridY,villager6.getSize(),villager6.getSize(),null);
		g.drawImage(villager,villager7.getX()+gridX,villager7.getY()+gridY,villager7.getSize(),villager7.getSize(),null);
		g.drawImage(villager,villager8.getX()+gridX,villager8.getY()+gridY,villager8.getSize(),villager8.getSize(),null);

		g.setColor(Color.WHITE);
		g.setFont(minecraftFive);
		checkIsland();
		g.drawString("Render Distance: " + renderDistance + " chunks",10,15);
		g.drawString("Current Island: "+currentIsland,10,30);
		
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource()==zoomIn && renderDistance >=11){
			decreaseRenderDistance();
		}
		else if(e.getSource()==zoomOut && renderDistance <=95){
			increaseRenderDistance();
		}
		repaint();
	}
	public void keyPressed(KeyEvent e){
		//left is 37, up is 38, right is 39, down is 40.
		//91 is zoom out, 93 is zoom in
		System.out.println(e.getKeyCode());

		if(e.getKeyCode()==37 && player.canMoveLeft()){
			gridX+=blockSize;
			playerCol--;
			player.setCol(playerCol);
			if(player.adjacentToLandmark()){
				player.destinationSound();
			}
			if(playerCol%2==0){
				playerSprite = left1;
			}
			else{
				playerSprite = left2;
			}
			
		}
		if(e.getKeyCode()==38 && player.canMoveUp()){
			gridY+=blockSize;
			playerRow--;
			player.setRow(playerRow);
			if(player.adjacentToLandmark()){
				player.destinationSound();
			}
			if(playerRow%4==0 || playerRow%4==2){
				playerSprite = up1;
			}
			else if(playerRow%4==1){
				playerSprite = up2;
			}
			else if(playerRow%4==3){
				playerSprite = up3;
			}
		}
		if(e.getKeyCode()==39 && player.canMoveRight()){
			gridX-=blockSize;
			playerCol++;
			player.setCol(playerCol);
			if(player.adjacentToLandmark()){
				player.destinationSound();
			}
			if(playerCol%2==0){
				playerSprite = right1;
			}
			else{
				playerSprite = right2;
			}
		}
		if(e.getKeyCode()==40 && player.canMoveDown()){
			gridY-=blockSize;
			playerRow++;
			player.setRow(playerRow);
			if(player.adjacentToLandmark()){
				player.destinationSound();
			}
			if(playerRow%4==0 || playerRow%4==2){
				playerSprite = down1;
			}
			else if(playerRow%4==1){
				playerSprite = down3;
			}
			else if(playerRow%4==3){
				playerSprite = down2;
			}
		}
		if(e.getKeyCode()==91 && renderDistance <=95){
			increaseRenderDistance();
		
			System.out.println("Zooming Out");
			System.out.println("Render Distance: " + renderDistance + " chunks.");
		}
		if(e.getKeyCode()==93 && renderDistance >=11){
			decreaseRenderDistance();
			
			System.out.println("Zooming In");
			System.out.println("Render Distance: " + renderDistance + " chunks.");
		}
		if(e.getKeyCode()==59){
			if(sunset){
				sunset = false;
				zoomIn.setBackground(buttonColor);
				zoomOut.setBackground(buttonColor);
				
			}
			else if(!sunset){
				sunset = true;
				zoomIn.setBackground(buttonColorSunset);
				zoomOut.setBackground(buttonColorSunset);
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
			playerRow = 17;
			playerCol = 21;
			player.setRow(16);
			player.setCol(19);
		}
		if(e.getKeyCode()==51){
			playerRow = 27;
			playerCol = 36;
			player.setRow(26);
			player.setCol(36);
		}
		if(e.getKeyCode()==52){
			playerRow = 37;
			playerCol = 53;
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
			playerRow = 49;
			playerCol = 70;
			player.setRow(48);
			player.setCol(71);
		}
		if(e.getKeyCode()==55){
			playerRow = 57;
			playerCol = 61;
			player.setRow(58);
			player.setCol(62);
		}
		if(e.getKeyCode()==56){
			playerRow = 71;
			playerCol = 78;
			player.setRow(74);
			player.setCol(77);
		}
		gridX = (((renderDistance-1)/2)*blockSize)-(playerCol*blockSize);
		gridY = (((renderDistance-1)/2)*blockSize)-(playerRow*blockSize);
		System.out.println("Player Row: " + playerRow + " Player Column: " + playerCol);

	
		repaint();
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()>=37 && e.getKeyCode()<=40){
			System.out.println("key released");
			playerSprite = down1;
		}
		repaint();
	}
	public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e){
		System.out.println("X: " + e.getX() + " Y: " + e.getY());
	}

	public void checkIsland(){
		if(playerCol>=5 && playerCol <=10 && playerRow >=19 && playerRow <=26){
			currentIsland = "Ni'ihau";
		}
		else if(playerCol>=13 && playerCol <=24 && playerRow >=12 && playerRow <=20){
			currentIsland = "Kauai";
		}
		else if(playerCol>=28 && playerCol <=43 && playerRow >=19 && playerRow <=31){
			currentIsland = "O'ahu";
		}
		else if(playerCol>=45 && playerCol <=59 && playerRow >=35 && playerRow <=39){
			currentIsland = "Moloka'i";
		}
		else if(playerCol>=52 && playerCol <=59 && playerRow >=45 && playerRow <=52){
			currentIsland = "Lanai";
		}
		else if(playerCol>=62 && playerCol <=79 && playerRow >=43 && playerRow <=53){
			currentIsland = "Maui";
		}
		else if(playerCol>=59 && playerCol <=65 && playerRow >=55 && playerRow <=60){
			currentIsland = "Kaho'olawe";
		}
		else if(playerCol>=65 && playerCol <=93 && playerRow >=60 && playerRow <=91){
			currentIsland = "Hawai'i";
		}
		
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0 && renderDistance <=95){
			//zoom out
			increaseRenderDistance();
        } else if (e.getWheelRotation() < 0 && renderDistance >=11){
			//zoom in
			decreaseRenderDistance();
        }
		repaint();
    }

	public void decreaseRenderDistance(){
		renderDistance -=6;
		blockSize = screenSize/renderDistance;
		player.setSize(blockSize);
		chicken1.setSize(blockSize);
		pig1.setSize(blockSize);
		car1.setSize(blockSize);
		car2.setSize(blockSize);
		car3.setSize(blockSize);
		car4.setSize(blockSize);
		car5.setSize(blockSize);
		car6.setSize(blockSize);

		villager1.setSize(blockSize);
		villager2.setSize(blockSize);
		villager3.setSize(blockSize);
		villager4.setSize(blockSize);
		villager5.setSize(blockSize);
		villager6.setSize(blockSize);
		villager7.setSize(blockSize);
		villager8.setSize(blockSize);

		player.setX(((renderDistance-1)/2)*blockSize);
		player.setY(((renderDistance-1)/2)*blockSize);
		player.setRow(playerRow);
		player.setCol(playerCol);
		gridX = (((renderDistance-1)/2)*blockSize)-(playerCol*blockSize);
		gridY = (((renderDistance-1)/2)*blockSize)-(playerRow*blockSize);
		
	}
	public void increaseRenderDistance(){
		renderDistance +=6;
		blockSize = screenSize/renderDistance;
		player.setSize(blockSize);
		chicken1.setSize(blockSize);
		pig1.setSize(blockSize);
		car1.setSize(blockSize);
		car2.setSize(blockSize);
		car3.setSize(blockSize);
		car4.setSize(blockSize);
		car5.setSize(blockSize);
		car6.setSize(blockSize);

		villager1.setSize(blockSize);
		villager2.setSize(blockSize);
		villager3.setSize(blockSize);
		villager4.setSize(blockSize);
		villager5.setSize(blockSize);
		villager6.setSize(blockSize);
		villager7.setSize(blockSize);
		villager8.setSize(blockSize);
		
		player.setX(((renderDistance-1)/2)*blockSize);
		player.setY(((renderDistance-1)/2)*blockSize);
		player.setRow(playerRow);
		player.setCol(playerCol);
		gridX = (((renderDistance-1)/2)*blockSize)-(playerCol*blockSize);
		gridY = (((renderDistance-1)/2)*blockSize)-(playerRow*blockSize);
	}
	
}
