import java.io.Serializable;

public class Node<E> implements Serializable{
    private E data;
    private Node<E> next;
    private Node<E> prev;

    public Node(E data){
        next = null;
        prev = null;
        this.data = data;
    }
    public E get(){
        return data;
    }
    public Node<E> next(){
        return next;
    }
    public Node<E> prev(){
        return prev;
    }
    public void setNext(Node<E> next){
        this.next = next;
    }
    public void setPrev(Node<E> prev){
        this.prev = prev;
    }
    public void setData(E data){
        this.data = data;
    }
}
