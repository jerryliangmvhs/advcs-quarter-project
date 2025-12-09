public class Pig extends LandAnimal implements Runnable{
    public Pig(int row, int col, int size, MyHashTable<Location,GridObject> map, Screen sc){
       super(row,col,size,map,sc);
    }
}