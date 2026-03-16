import java.awt.*;
public class Avatar implements Runnable{
    private int x;
    private int y;
    private double dx;
    private double dy;
    public boolean targetReached;
    private int targetX;
    private int targetY;
    private Screen sc;
    private DLList<Location> path;
    private int pathIndex;
    public Avatar(int x, int y, Screen sc){
        this.x = x;
        this.y = y;
        targetX = -1;
        targetY = -1;
        dx = 0;
        dy = 0;
        targetReached = false;
        this.sc = sc;
    }
    public void drawMe(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(x-10,y-10,20,20);
    }
    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setPath(DLList<Location> path){
        this.path = path;
        pathIndex = 0;

        Location start = path.get(pathIndex);
        Location next = path.get(pathIndex+1);

        System.out.println(start.getX());
        System.out.println(start.getY());
        setCoordinates(start.getX(), start.getY());
        setDirection(start.getX(), start.getY(), next.getX(), next.getY());
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setDirection(int x1, int y1, int x2, int y2){
        targetReached = false;
        dy = (y2-y1)/x2-x1;
        dx = 1;
        targetX = x2;
        targetY = y2;
    }
    @Override
    public void run(){
        while(true){
            if(!targetReached && dx!=0 && dy!=0){
                x += dx;
                y += dy;
                if(Math.abs(x - targetX) < 5 && Math.abs(y - targetY) < 5){
                    x = targetX;
                    y = targetY;
                    targetReached = true;
                }
                else{
                    targetReached = false;
                }

            } 
            else if(path != null && pathIndex < path.size()-1){
                pathIndex++;
                if(pathIndex < path.size()-1){
                    Location current = path.get(pathIndex);
                    Location next = path.get(pathIndex+1);
                    setDirection(current.getX(), current.getY(),next.getX(), next.getY());
                }
            }
            sc.repaint();
            try{
                Thread.sleep(200); 
            }catch(Exception e){}
        }
    }
}

