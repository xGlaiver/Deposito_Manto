package EsercizioMedio;

public class TuttoMaiuscolo implements Formattazione{
    
    @Override
    public String applica(String testo) {
        return testo.toUpperCase();
    }
}
