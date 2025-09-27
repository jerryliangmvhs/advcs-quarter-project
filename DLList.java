public class DLList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLList(){
        head = new Node(null);
        tail = new Node(null);
        head.setNext(tail);
        tail.setPrev(head);
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
        size++;
        return true;
    }
    public void add(int index, E data){
        Node<E> newNode = new Node(data);
        if(index == size || size ==0){
            //if the index is at the end
            //or if the linkedlist is empty
            add(data);
        }
        else if(index ==0 && size >=1){
            //if the index is at the start and theres at least one element
            Node<E> prevNode = head;
            Node<E> nextNode = head.next();
            newNode.setNext(nextNode);
            nextNode.setPrev(newNode);
            newNode.setPrev(prevNode);
            prevNode.setNext(newNode);
        }
        else{
            //if the index is sandwiched
            Node<E> nextNode = getNode(index);
            Node<E> prevNode = getNode(index-1);
            newNode.setNext(nextNode);
            nextNode.setPrev(newNode);
            prevNode.setNext(newNode);
            newNode.setPrev(prevNode);
        }
        size++;
    }
    @SuppressWarnings("unchecked")
    public E get(int index){
        return (E)(getNode(index));
    }
    public int size(){
        return size;
    }
}
