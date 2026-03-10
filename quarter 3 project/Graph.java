public class Graph<E>{
    private int size;
    private MyHashMap<E, MyHashSet<E, Integer>> graph;

    public Graph(){
        size = 0;
        graph = new MyHashMap<E,MyHashSet<E, Integer>>();
    }
    public void add(E node){
        graph.put(node, new MyHashSet<E, Integer>());
        size++;
    }
    public void addEdge(E node1, E node2, int weight){
        graph.get(node1).add(node2, weight);
        graph.get(node2).add(node1, weight);
    }
    public void remove(E node){
        graph.remove(node);
        DLList<E> nodes = graph.get(node).toDLList();
        for(int i=0; i<nodes.size(); i++){
            graph.get(nodes.get(i)).remove(node);
        }
        size--;
    }
    public int size(){
        return size;
    }

}
