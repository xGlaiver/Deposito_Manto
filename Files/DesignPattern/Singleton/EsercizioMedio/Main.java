package EsercizioMedio;

public class Main {
    public static void main(String[] args) {
        Distributore distributore = Distributore.INSTANCE;

        distributore.inserisci(5);
        System.out.println(distributore.eroga(2));
        System.out.println(distributore.eroga(4));
    }
}
