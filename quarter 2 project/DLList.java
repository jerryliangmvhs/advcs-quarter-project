import java.util.Iterator;
import java.lang.Iterable;
public class DLList<E> implements Iterable<E>, Iterator<E>{
    private Node<E> head;
    private Node<E> tail;
    private int size;
    private Node<E> currentIteration;

    public DLList(){
        
        head = new Node<E>(null);
        tail = new Node<E>(null);
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
        currentIteration = head;
    }   
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
        Node<E> newNode = new Node<E>(data);
        Node<E> prevNode = tail.prev();
        newNode.setNext(tail);
        newNode.setPrev(prevNode);
        prevNode.setNext(newNode);
        tail.setPrev(newNode);
        size++;
        return true;
    }
    public void add(int index, E data){
        Node<E> newNode = new Node<E>(data);
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
    @Override
	public Iterator<E> iterator(){
		return (this);
	}

	@Override
	public boolean hasNext(){
		if(currentIteration!=null){
            return true;
        }
        return false;
	}
	@Override
	public E next(){
		E data = (E)currentIteration.get();
        currentIteration = currentIteration.next();
        return data;
	}

}
