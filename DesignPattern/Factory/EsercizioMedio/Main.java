package EsercizioMedio;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        Optional<Comando> somma = ComandoFactory.crea("somma");
        Optional<Comando> moltiplica = ComandoFactory.crea("moltiplica");
        Optional<Comando> divisione = ComandoFactory.crea("divisione");

        somma.ifPresentOrElse(
            s -> System.out.println(s.esegui(3, 2)),
            () -> System.out.println("Comando non trovato")
        );

        moltiplica.ifPresentOrElse(
            s -> System.out.println(s.esegui(3, 2)),
            () -> System.out.println("Comando non trovato")
        );

         divisione.ifPresentOrElse(
            s -> System.out.println(s.esegui(3, 2)),
            () -> System.out.println("Comando non trovato")
        );
    }
    
}
