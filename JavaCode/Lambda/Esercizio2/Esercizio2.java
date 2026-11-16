package Esercizio2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Esercizio2 {
    public static void main(String[] args) {
        
        List<String> frutta = new ArrayList<>(
        List.of("kiwi", "banana", "mela", "ananas", "pera", "fico", "arancia"));

        //Es: 1
        Consumer<String> stampa = a -> System.out.println(a);
        frutta.forEach(stampa);

        //Es: 2
        Predicate<String> corta = a -> a.length() <= 4;
        frutta.removeIf(corta);
        System.out.println("\nCorta: ");
        frutta.forEach(stampa);

        //Es: 3
        Comparator<String> customComparator = (a,b) -> {
            
            int stringLength = a.length() - b.length();
            if(stringLength != 0) {
                return stringLength;
            }

            return a.compareTo(b);
        };

        frutta.sort(customComparator);
         System.out.println("\nSorted: ");
        frutta.forEach(stampa);
    }
}
