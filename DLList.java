public class DLList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLList(){
        head = new Node(null);
        tail = new Node(null);
        size = 0;
    }   
    @SuppressWarnings("unchecked")
    public Node<E> getNode(int index){
        if(index >=size/2){
            //traverse backwards
            Node<E> current = tail;
            for(int i=size; i>index; i--){
                current = current.prev();
            }
            return (Node<E>)current.get();
        }
        else if(index<size/2){
            //traverse forwards
            Node<E> current = head;
            for(int i=0; i<index; i++){
                current = current.next();
            }
            return (Node<E>)current.get();
        }
        return null;
    }
    public boolean add(E data){
        Node<E> newNode = new Node(data);
        Node<E> prevNode = tail.prev();
        newNode.setNext(tail);
        newNode.setPrev(prevNode);
        prevNode.setNext(newNode);
        tail.setPrev(newNode);

        return true;
    }
}
