package Esercizio1;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Esercizio1 {
    public static void main(String[] args) {
        

        //Es: 1
        List<Integer> numeri = List.of(1, 2, 3);

        numeri.forEach(e -> System.out.println(e * 2));

        //Es: 2
        String[] parole = {"mondo", "a", "ciao"};

        Arrays.sort(parole, (a,b) -> a.compareTo(b));
        System.out.println(Arrays.toString(parole));
    }
}