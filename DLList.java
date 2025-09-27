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
            Node<E> current = tail.prev();
            for(int i=size; i>index+1; i--){
                current = current.prev();
            }
            return current;
        } else {
            //traverse forwards
            Node<E> current = head.next();
            for(int i=0; i<index; i++){
                current = current.next();
            }
            return current;
        }
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
            //don't do size++ here because add(data) has it already.
        }
        else if(index ==0 && size >=1){
            //if the index is at the start and theres at least one element
            Node<E> prevNode = head;
            Node<E> nextNode = head.next();
            newNode.setNext(nextNode);
            nextNode.setPrev(newNode);
            newNode.setPrev(prevNode);
            prevNode.setNext(newNode);
            size++;
        }
        else{
            //if the index is sandwiched
            Node<E> nextNode = getNode(index);
            Node<E> prevNode = getNode(index-1);
            newNode.setNext(nextNode);
            nextNode.setPrev(newNode);
            prevNode.setNext(newNode);
            newNode.setPrev(prevNode);
            size++;
        }
    }
    @SuppressWarnings("unchecked")
    public E get(int index){
        return getNode(index).get();
    }
    public int size(){
        return size;
    }
    public E remove(int index){
        Node <E> removedNode = getNode(index);
        Node<E> prevNode = removedNode.prev();
        Node<E> nextNode = removedNode.next();
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
        size--;
        return (E)removedNode.get();
    }
    public E set(int index, E data){
        Node<E> node = getNode(index);
        E replacedNode = node.get();
        node.setData(data);
        return replacedNode;
    }
    public String toString(){
        String str = "";
        Node<E> current = head.next();
        while(current != tail){
            str += current.get().toString();
            current = current.next();
        }
        return str;
    }
    public boolean remove(Object other){
        Node<E> current = head.next();
        int index = 0;
        while(current.get()!=null){
            if(current.get().equals(other)){
                remove(index);
                return true;
            }
            index++;
            current = current.next();
        }
        return false;
    }
    public void clear(){
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }
}
