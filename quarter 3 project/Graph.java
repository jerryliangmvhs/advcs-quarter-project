import java.awt.*;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;
public class Graph<E>{
    private int size;
    private MyHashMap<E, MyHashMap<E, Integer>> graph;
    private MyHashMap<E, Pair<Integer, E>> table;
    private MyHashMap<String, E> abbreviationMap;

    public Graph(){
        size = 0;
        graph = new MyHashMap<E,MyHashMap<E, Integer>>();
        abbreviationMap = new MyHashMap<String, E>();
    }
    public void add(E node){
        graph.put(node, new MyHashMap<E, Integer>());
        abbreviationMap.put(((Location)node).getAbbreviation(),node);
        size++;
    }
    public void addEdge(E node1, E node2, int weight){
        graph.get(node1).put(node2, weight);
        graph.get(node2).put(node1, weight);
    }
    public int getWeight(E node1, E node2){
        return graph.get(node1).get(node2);
    }
    public Location clickedLocation(int mouseX, int mouseY){
        for(E node : graph.keySet()){
            Location locationObj = (Location)node;
            if((int)(Math.abs(mouseX-locationObj.getX()))<40 && (int)(Math.abs(mouseY-locationObj.getY()))<40){
                System.out.println("Location click detected at " + locationObj.getName());
                return locationObj;
            }
        }
        return null;
    }
    public void remove(E node){
        abbreviationMap.remove(((Location)node).getAbbreviation());
        for(E each: graph.get(node).keySet()){
            graph.get(each).remove(node);
        }
        graph.remove(node);
        size--;
    }
    public E getNodeByAbbreviation(String abbreviation){
        return abbreviationMap.get(abbreviation);
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
    public void drawPath(Graphics g, Pair<DLList<E>,Integer> path, Color color){
        DLList<E> pathList = path.getFirst();
        for(int i=0; i<pathList.size(); i++){
            Location locationObj = (Location)pathList.get(i);
            locationObj.drawMe(g,color);
            
            if(i<pathList.size()-1){
                Location location1 = (Location)pathList.get(i);
                Location location2 = (Location)pathList.get(i+1);
                int x1 = location1.getX();
                int y1 = location1.getY();
                int x2 = location2.getX();
                int y2 = location2.getY();
                g.setColor(color);
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }

    public int size(){
        return size;
    }
    public Pair<DLList<E>, Integer> shortestPath(E start, E end){
        //linkedlist is path and int is distance
        table(start);
        DLList<E> path = new DLList<E>();
        path.add(0, end);// add to the front
        while (path.get(0).equals(start)==false){
            path.add(0, table.get(path.get(0)).getLastNode()); // add to the front 
        }
        return new Pair<DLList<E>,Integer>(path, table.get(end).getShortestDistance());
    }

    public void table(E start){
        table = new MyHashMap<E, Pair<Integer, E>>(); // setup table
        for(E each : graph.keySet()) { // putting in nodes of graph into the table
            table.put(each, new Pair<Integer, E>(Integer.MAX_VALUE, null));
        }
        MyHashSet<E> visitedNodes = new MyHashSet<E>();
        MyHashSet<E> unvisitedNodes = new MyHashSet<E>();
        E current = start;
        table.get(current).setShortestDistance(0);
        unvisitedNodes.add(current); // add first node to unvisited
        while(unvisitedNodes.size()>0){ // when there are nodes to visit
            visitedNodes.add(current);
            unvisitedNodes.remove(current); // move it to visited 

            for(E each : graph.get(current).keySet()) { // for each neighboring node to current 
                if (!visitedNodes.contains(each)) { // if the neighbors are not checked yet
                    unvisitedNodes.add(each); // add to unvisited
                    int distance = table.get(current).getShortestDistance() + graph.get(current).get(each); // get distance to there
                    if(table.get(each).getShortestDistance() > distance) { // if its smaller than what we had before 
                        table.get(each).setLastNode(current); // switch it out 
                        table.get(each).setShortestDistance(distance); // switch it out
                    }    
                }
            }
            int smallest = Integer.MAX_VALUE;
            for(E each : unvisitedNodes){ // go through unvisited nodes
                if(smallest > table.get(each).getShortestDistance()) { // if shortest distance is smaller
                    smallest = table.get(each).getShortestDistance(); // make it smallest
                    current = each; // move on to the next current the smallest distance
                }
            }
        }
    }


}
