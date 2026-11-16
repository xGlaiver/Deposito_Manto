public class Main {
    public static void main(String[] args) {
        DueGen<Integer, String> dueGen = new DueGen<>(3, "Gatto");

        dueGen.showTypes();

        int numero = dueGen.getObj1();
        String stringa = dueGen.getObj2();

        System.out.println("Valore of numero is: " + numero);
        System.out.println("Valore of Stringa is: " + stringa);
    }
}
