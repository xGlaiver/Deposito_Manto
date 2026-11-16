# Factory Pattern

## Spiegazione teorica

- Una **factory** è un punto unico che decide **quale oggetto creare** e lo restituisce. Il chiamante non usa `new` con la classe concreta.
- Il chiamante conosce l'interfaccia, non l'implementazione. Cambiare la classe creata non tocca il resto del codice.
- Varianti: *simple factory* (un metodo con `switch`), *factory method* (le sottoclassi decidono), *abstract factory* (famiglie di oggetti correlati).
- In Spring il container è una factory. Ogni metodo `@Bean` è una "ricetta" di creazione, e `ApplicationContext.getBean()` restituisce l'oggetto richiesto.

### Parametri

**Annotazioni** (in una classe `@Configuration`)

| Annotazione | Attributo | Significato |
|---|---|---|
| `@Bean` | `name` / `value` | Nome del bean (default: nome del metodo) |
| `@Bean` | `initMethod` / `destroyMethod` | Metodo chiamato dopo la creazione / prima della distruzione |
| `@Bean` | `autowireCandidate` | Se `false`, il bean non viene iniettato per tipo |
| `@Configuration` | `proxyBeanMethods` | Default `true`: chiamate tra metodi `@Bean` restituiscono lo stesso singleton |
| `@Value` | `value` | Valore da proprietà, con default dopo i due punti: `${chiave:default}` |

## Esempi teorici di utilizzo

- **Creare una forma di pagamento:** il codice chiede "carta" o "bonifico" e riceve l'oggetto giusto.
- **Scegliere un parser:** JSON o CSV, in base al formato del file ricevuto.
- **Scegliere un archivio:** disco locale o cloud, in base alla configurazione dell'ambiente.
- **Creare un client di un servizio esterno:** con indirizzo e chiave diversi tra test e produzione.
- **Creare DTO da entità:** una classe dedicata costruisce la risposta, così il controller non conosce i dettagli.

## Esempi di codice

### Base

Cosa mostra: simple factory con `switch`. Il chiamante riceve un'interfaccia e non usa `new`.

```java
interface Pizza {
    String descrizione();
}

class Margherita implements Pizza {
    public String descrizione() { return "Pizza margherita"; }
}

class Diavola implements Pizza {
    public String descrizione() { return "Pizza diavola"; }
}

class PizzaFactory {
    static Pizza crea(String tipo) {
        return switch (tipo) {                   // la factory decide QUALE classe creare
            case "margherita" -> new Margherita();
            case "diavola"    -> new Diavola();
            default -> throw new IllegalArgumentException("Pizza sconosciuta: " + tipo);
        };
    }
}

public class Main {
    public static void main(String[] args) {
        Pizza p = PizzaFactory.crea("diavola");  // il chiamante non usa new
        System.out.println(p.descrizione());     // Pizza diavola
    }
}
```

- Il chiamante scrive `PizzaFactory.crea(...)` e riceve un `Pizza`.
- Il `new` è concentrato in un solo punto: più facile da cambiare.
- Il `default` gestisce i tipi sconosciuti con un errore chiaro.

### Intermedio

Cosa mostra: factory basata su una `Map` di `Supplier`. Ogni tipo è associato al proprio costruttore, senza `switch`.

```java
import java.util.Map;
import java.util.function.Supplier;

interface Animale {
    String verso();
}

record Cane() implements Animale {
    public String verso() { return "Bau"; }
}

record Gatto() implements Animale {
    public String verso() { return "Miao"; }
}

class AnimaleFactory {
    // ogni chiave è associata al costruttore della classe da creare
    private static final Map<String, Supplier<Animale>> FABBRICHE = Map.of(
            "cane", Cane::new,
            "gatto", Gatto::new);

    static Animale crea(String tipo) {
        Supplier<Animale> fabbrica = FABBRICHE.get(tipo);
        if (fabbrica == null) {
            throw new IllegalArgumentException("Tipo sconosciuto: " + tipo);
        }
        return fabbrica.get();                   // chiama il costruttore solo ora
    }
}
```

- `Cane::new` è un method reference al costruttore: equivale a `() -> new Cane()`.
- Aggiungere un tipo significa aggiungere una riga alla mappa.
- Il `Supplier` crea l'oggetto solo quando viene richiesto.

### Avanzato

Cosa mostra: factory come metodo `@Bean` in Spring. La scelta dell'implementazione dipende da una proprietà. (Import omessi.)

```java
public interface Archivio {
    void salva(String nome, String contenuto);
}

class ArchivioLocale implements Archivio {
    public void salva(String nome, String contenuto) {
        System.out.println("Salvo in locale: " + nome);
    }
}

class ArchivioCloud implements Archivio {
    public void salva(String nome, String contenuto) {
        System.out.println("Salvo nel cloud: " + nome);
    }
}

@Configuration
class ArchivioConfig {

    @Bean                                        // il metodo è la factory del bean
    Archivio archivio(@Value("${app.storage:locale}") String tipo) {  // default: "locale"
        return switch (tipo) {
            case "cloud"  -> new ArchivioCloud();
            case "locale" -> new ArchivioLocale();
            default -> throw new IllegalStateException("app.storage non valido: " + tipo);
        };                                       // errore all'avvio, non a metà uso
    }
}
```

- Il metodo restituisce l'interfaccia `Archivio`: il resto dell'app non sa quale classe è attiva.
- `${app.storage:locale}` legge la proprietà e usa `locale` se manca.
- Con `app.storage=cloud` in `application.properties` cambia l'implementazione, senza modificare il codice.
- Il `default` fa fallire l'avvio con un messaggio chiaro: meglio dell'errore a metà uso.

## Esercizi

### Facile

**Obiettivo:** scrivere una simple factory con `switch` che restituisce oggetti diversi.

**Consegna:** scrivi un'interfaccia `Veicolo` con il metodo `int ruote()`. Crea le classi `Auto` (4 ruote) e `Moto` (2 ruote). Scrivi una classe `VeicoloFactory` con il metodo `crea(String tipo)` che restituisce il veicolo giusto e lancia un'eccezione per i tipi sconosciuti. Crea un'auto e una moto e stampa il numero di ruote di ciascuna.

### Medio

**Obiettivo:** creare una factory che associa un comando testuale a un oggetto da eseguire.

**Consegna:** un mini terminale riceve comandi come `"somma 2 3"` oppure `"moltiplica 4 5"`. Scrivi un'interfaccia `Comando` con il metodo `String esegui(int a, int b)`. Crea una factory che, dato il nome del comando, restituisce l'implementazione corretta. Il comando `"somma"` restituisce la somma, `"moltiplica"` il prodotto. Per un nome sconosciuto la factory deve restituire un `Optional` vuoto, non lanciare un'eccezione.

### Difficile

**Obiettivo:** usare un metodo `@Bean` come factory in Spring, con scelta da proprietà e valore di default.

**Consegna:** una stazione meteo ha tre tipi di sensore: `"temperatura"`, `"umidita"` e `"pressione"`, ciascuno con un'interfaccia comune `Sensore` e il metodo `String leggi()`. Crea una `@Configuration` con un `@Bean` che legge la proprietà `meteo.sensore` (default `"temperatura"`) e restituisce il sensore giusto. Un valore non valido deve far fallire l'avvio con un messaggio che elenca i tipi ammessi.
