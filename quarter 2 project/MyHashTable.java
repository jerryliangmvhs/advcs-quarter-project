public class MyHashTable<K,V> {
    private DLList<V>[] table;
    private MyHashSet<K> keySet;

    @SuppressWarnings("unchecked")
    public MyHashTable(){
        table = new DLList[9999];
        //each slot is null by default
        keySet = new MyHashSet<K>();
    }
    public void put(K key, V value){
        if (table[key.hashCode()] == null) {
            table[key.hashCode()] = new DLList<>();
        }
        table[key.hashCode()].add(value);
        keySet.add(key);
    }
    public DLList get(K key){
        return table[key.hashCode()];
    }
    public MyHashSet<K> keySet(){
        return keySet;
    }
    public void remove(K key, V value){
        if(table[key.hashCode()]!=null){
            table[key.hashCode()].remove(value);
            keySet.remove(key);
        }
        if(table[key.hashCode()].size()==0){
            table[key.hashCode()] = null;
        }
    }
}
