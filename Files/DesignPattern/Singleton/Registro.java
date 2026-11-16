import java.util.ArrayList;
import java.util.List;

public enum Registro {
    INSTANCE;                                    // un solo valore = una sola istanza

    private final List<String> messaggi = new ArrayList<>();

    public void scrivi(String m) {  // un thread alla volta modifica la lista
        messaggi.add(m);
    }

    public List<String> leggi() {
        return List.copyOf(messaggi);            // copia immutabile: il chiamante non può modificare
    }
}
