public class DueGen<T,V> {

    private T obj1;
    private V obj2;

    public DueGen(T obj1, V obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }


    public void setObj1(T obj1) {
        this.obj1 = obj1;
    }

    public T getObj1() {
        return this.obj1;
    }

    public void setObj2(V obj2) {
        this.obj2 = obj2;
    }
    
    public V getObj2() {
        return this.obj2;
    }

    public void showTypes() {
        System.out.println("Tipo of T is: " + obj1.getClass().getName());
        System.out.println("Tipo of V is: " + obj2.getClass().getName());
    }
}