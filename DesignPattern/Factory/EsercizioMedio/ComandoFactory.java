package EsercizioMedio;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ComandoFactory {
    private static final Map<String, Supplier<Comando>> comands = Map.ofEntries(
        Map.entry("somma", Somma::new),
        Map.entry("moltiplica", Moltiplica::new)
    );
    
    public static Optional<Comando> crea(String tipo) {
        Supplier<Comando> type = comands.get(tipo);
        if(type == null) {
            return Optional.empty();
        }

        return Optional.of(type.get());
    }
}
