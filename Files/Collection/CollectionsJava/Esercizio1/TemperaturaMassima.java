package CollectionsJava.Esercizio1;

public class TemperaturaMassima {
    public static void main(String[] args) {
        double[] temperature = {12.5, 15.0, 9.8, 18.2, 14.0, 11.3, 16.7};
        String[] giorni = {"lunedì", "martedì", "mercoledì", "giovedì", "venerdì", "sabato", "domenica"};

        int indiceMassimo = 0;
        for (int i = 1; i < temperature.length; i++) {
            if (temperature[i] > temperature[indiceMassimo]) {
                indiceMassimo = i;
            }
        }

        System.out.println("Temperatura più alta: " + temperature[indiceMassimo]);
        System.out.println("Giorno: " + giorni[indiceMassimo]);
    }
}
