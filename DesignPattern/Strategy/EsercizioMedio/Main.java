package EsercizioMedio;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Formattazione primaMaiuscola = new PrimaMaiuscolo();
        Formattazione testoContrario = new TestoContrario();
        Formattazione tuttoMaiuscolo = new TuttoMaiuscolo();

        List<Formattazione> formattazioni = List.of(tuttoMaiuscolo, primaMaiuscola, testoContrario);

        String frase = "il pattern strategy in java";

        for (Formattazione formattazione : formattazioni) {
            System.out.println(formattazione.applica(frase));
        }
    }
}
