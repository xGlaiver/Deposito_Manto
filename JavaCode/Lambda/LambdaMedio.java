import java.util.*;
import java.util.stream.Collectors;

public class LambdaMedio {

    @FunctionalInterface                       // il compilatore verifica che il metodo astratto sia uno solo
    interface Regola {
        boolean verifica(Prodotto p);
    }

    static List<Prodotto> filtra(List<Prodotto> lista, Regola regola) {
        List<Prodotto> risultato = new ArrayList<>();
        for (Prodotto p : lista) {
            if (regola.verifica(p)) risultato.add(p);
        }
        return risultato;
    }

    public static void main(String[] args) {
        List<Prodotto> prodotti = List.of(
                new Prodotto("Mouse", "periferiche", 25.0),
                new Prodotto("Tastiera", "periferiche", 45.0),
                new Prodotto("Monitor", "schermi", 199.0),
                new Prodotto("Webcam", "periferiche", 60.0),
                new Prodotto("TV", "schermi", 450.0));

        // 1) Pipeline: filtra → ordina → trasforma → raccogli
        // prodotti.sort(Double::compareTo);
        List<String> economici = prodotti.stream()
                .filter(p -> p.prezzo() < 100)
                .sorted(Comparator.comparingDouble(Prodotto::prezzo)) //scorciatoia di p -> p.prezzo() ordina immediatamente in ordine crescente
                .map(Prodotto::nome)
                .toList();
        System.out.println(economici);          // [Mouse, Tastiera, Webcam]

        // 2) Somma dei prezzi
        double totale = prodotti.stream().mapToDouble(Prodotto::prezzo).sum();
        System.out.println(totale);             // 779.0

        // 3) Raggruppa per categoria e conta (l'ordine delle chiavi non è garantito)
        Map<String, Long> perCategoria = prodotti.stream()
                .collect(Collectors.groupingBy(Prodotto::categoria, Collectors.counting()));
        System.out.println(perCategoria);       // {periferiche=3, schermi=2}

        // 4) Variabile catturata: deve restare effectively final
        double sconto = 10;
        prodotti.stream()
                .filter(p -> p.prezzo() > 100)
                .forEach(p -> System.out.println(p.nome() + " scontato: " + (p.prezzo() - sconto)));
        // Monitor scontato: 189.0
        // TV scontato: 440.0
        // ❌ Se dopo scrivessi sconto = 20; il codice non compilerebbe

        // 5) La nostra interfaccia funzionale
        List<Prodotto> schermi = filtra(prodotti, p -> p.categoria().equals("schermi"));
        System.out.println(schermi.size());     // 2
    }
}