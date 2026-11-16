package CollectionsJava.Esercizio3;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class IncassiCinema {
    public static void main(String[] args) {

        Map<String, Double> ordineArrivo = new LinkedHashMap<>();
        Map<String, Double> ordineAlfabetico = new TreeMap<>();

        String[] titoli = {"Ombre di città", "Alba rossa", "Mare aperto", "Il viaggio"};
        double[] incassi = {12500.0, 8300.5, 15200.0, 9700.0};

        for (int i = 0; i < titoli.length; i++) {
            ordineArrivo.put(titoli[i], incassi[i]);
            ordineAlfabetico.put(titoli[i], incassi[i]);
        }

        System.out.println("Ordine di arrivo: " + ordineArrivo);
        System.out.println("Ordine alfabetico: " + ordineAlfabetico);
    }
}
