import java.io.Serializable;

public class MyHashSet<E> implements Serializable{
    private Object[] hashArray;
    private int size;
    private DLList<E> DLList;

    public MyHashSet(){
        hashArray = new Object[20000];
        size = 0;
        DLList = new DLList<E>();
    }
    
    public boolean add(E obj){
        int location = obj.hashCode();
        if(hashArray[location] == null){
            hashArray[location] = obj;
            size++;
            return true;
            
        }
        return false;
    }
    public void clear(){
        Object[] newHashArray = new Object[9999];
        hashArray = newHashArray;
        DLList.clear();
        size = 0;
    }
    public boolean contains(Object o){
        int location = o.hashCode();
        if(hashArray[location]!=null && hashArray[location].equals(o)){
            return true;
        }
        return false;
    }
    public int size(){
        return size;
    }
    public boolean remove(Object o){
        int location = o.hashCode();
        if(hashArray[location] != null){
            if(hashArray[location].equals(o)){
                hashArray[location] = null;
                size--;
                return true;
            }
        }
        return false;
    }
    @SuppressWarnings("unchecked")
    public DLList<E> toDLList(){
        DLList.clear();
        for(Object each: hashArray){
            if(each!=null){
                DLList.add((E)each);
            }
        }
        return DLList;
    }

}
