import java.util.*;

public class ConteggioParole {
    public static void main(String[] args) {
        String[] parole = {"spring", "java", "ai", "java", "boot", "spring", "java"};

        Map<String, Integer> ordineArrivo = new LinkedHashMap<>();    // ricorda l'ordine di primo inserimento
        TreeMap<String, Integer> ordineAlfabetico = new TreeMap<>();  // tiene le chiavi ordinate

        for (String p : parole) {
            ordineArrivo.merge(p, 1, Integer::sum);     // manca → inserisce 1; c'è → somma 1
            ordineAlfabetico.merge(p, 1, Integer::sum);
        }

        System.out.println(ordineArrivo);                // {spring=2, java=3, ai=1, boot=1}
        System.out.println(ordineAlfabetico);            // {ai=1, boot=1, java=3, spring=2}
        System.out.println(ordineAlfabetico.firstKey()); // ai
        System.out.println(ordineAlfabetico.lastKey()); // Spring
    }
}