import java.util.*;
import java.util.function.*;

public class LambdaBase {
    public static void main(String[] args) {
        // Prima di Java 8: classe anonima (verboso)
        Comparator<String> vecchio = new Comparator<String>() {
            @Override
            public int compare(String a, String b) { return a.length() - b.length(); }
        };

        // Con la lambda: stessa cosa in una riga
        Comparator<String> perLunghezza = (a, b) -> a.length() - b.length();

        List<String> nomi = new ArrayList<>(List.of("Anna", "Giuseppe", "Io", "Marta"));
        nomi.sort(perLunghezza);
        System.out.println(nomi);                       // [Io, Anna, Marta, Giuseppe]

        Predicate<String> lungo = s -> s.length() > 4;
        Function<String, Integer> lunghezza = String::length;       // method reference
        Consumer<String> stampa = s -> {
            System.out.println("Nome: " + s);
            //...
        };
        Supplier<String> saluto = () -> "Ciao!";

        System.out.println(lungo.test("Marta"));        // true
        System.out.println(lunghezza.apply("Anna"));    // 4
        System.out.println(saluto.get());               // Ciao!

        nomi.forEach(stampa);                           // Nome: Io / Nome: Anna / ...
        nomi.removeIf(lungo);                           // toglie Marta e Giuseppe
        System.out.println(nomi);                       // [Io, Anna]
    }
}
