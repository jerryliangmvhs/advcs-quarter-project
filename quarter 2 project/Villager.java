public class Villager extends LandAnimal implements Runnable{
    public Villager(int row, int col, int size, MyHashTable<Location,GridObject> map, Screen sc){
       super(row,col,size,map,sc);
    }
}