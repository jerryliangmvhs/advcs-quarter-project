public class Pair<K, V>{
    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
    public K getShortestDistance(){
        return first;
    }
    public V getLastNode(){
        return second;
    }
    public void setLastNode(V lastNode){
        this.second = lastNode;
    }
    public void setShortestDistance(K shortestDistance){
        this.first = shortestDistance;
    }
    public K getFirst(){
        return first;
    }
    public V getSecond(){
        return second;
    }
    
    
}
