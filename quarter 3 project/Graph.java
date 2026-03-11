import java.awt.*;
public class Graph<E>{
    private int size;
    private MyHashMap<E, MyHashMap<E, Integer>> graph;

    public Graph(){
        size = 0;
        graph = new MyHashMap<E,MyHashMap<E, Integer>>();
    }
    public void add(E node){
        graph.put(node, new MyHashMap<E, Integer>());
        size++;
    }
    public void addEdge(E node1, E node2, int weight){
        graph.get(node1).put(node2, weight);
        graph.get(node2).put(node1, weight);
    }
    public void remove(E node){
        graph.remove(node);
        for(E each: graph.get(node).keySet()){
            graph.get(each).remove(node);
        }
        size--;
    }
    public void drawMe(Graphics g){
        //draw Location
        for(E node : graph.keySet()){
            ((Location)node).drawMe(g);
        }
        //draw Lines
        for(E node1 : graph.keySet()){
            int x1 = ((Location)node1).getX();
            int y1 = ((Location)node1).getY();
            //get neighbor
            for(E node2 : graph.get(node1).keySet()){
                int x2 = ((Location)node2).getX();
                int y2 = ((Location)node2).getY();

                g.drawLine(x1, y1, x2, y2);
            }
        }

    }
    public int size(){
        return size;
    }

}
