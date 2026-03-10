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
    public int size(){
        return size;
    }

}
