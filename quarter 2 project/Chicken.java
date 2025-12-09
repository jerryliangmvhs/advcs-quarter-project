public class Chicken extends LandAnimal implements Runnable{
    public Chicken(int row, int col, int size, MyHashTable<Location,GridObject> map, Screen sc){
       super(row,col,size,map,sc);
    }
}