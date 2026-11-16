package Esercizio3;

import java.util.Comparator;
import java.util.List;

public class Esercizio3 {
    public static void main(String[] args) {
        List<Ordine> ordini = List.of(
            new Ordine("Anna", "Mouse", 2, 25.0),
            new Ordine("Luca", "Monitor", 1, 199.0),
            new Ordine("Anna", "Tastiera", 1, 45.0),
            new Ordine("Marta", "Mouse", 3, 25.0),
            new Ordine("Luca", "Webcam", 2, 60.0),
            new Ordine("Paolo", "Monitor", 2, 199.0)
        );
    
        //Es: 1
        ordini.stream()
            .map(Ordine::prodotto)
            .distinct()
            .sorted()
            .forEach(System.out::println);

        //Es: 2
        double totalAll = ordini.stream()
            .mapToDouble(Ordine::totale)
            .sum();
        
        System.out.println("Totale: " + totalAll);

        //Es: 3
        ordini.stream()
            .filter(c -> c.totale() > 100)
            .sorted(Comparator.comparing(Ordine::cliente))
            .map(Ordine::cliente)
            .distinct()
            .forEach(System.out::println);

        //Es: 4
        ordini.stream()
            .max(Comparator.comparingDouble(Ordine::totale))
            .ifPresent(c -> System.out.println(c.cliente() +" - " + c.prodotto()));;
    }
}
