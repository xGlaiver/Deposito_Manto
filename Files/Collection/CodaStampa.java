import java.util.LinkedList;

public class CodaStampa {
    public static void main(String[] args) {
        LinkedList<String> coda = new LinkedList<>();

        coda.addLast("Report Anna");                 // arrivo: in fondo alla coda
        coda.addLast("Fattura Luca");
        coda.addLast("Lettera Marta");

        while (!coda.isEmpty()) {                    // finché la coda non è vuota
            String documento = coda.removeFirst();   // esce dalla testa: primo arrivato, primo stampato
            System.out.println("Stampo: " + documento);
        }
    }
}