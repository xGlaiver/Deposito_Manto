# Strategy Pattern

## Spiegazione teorica

- Il pattern definisce una **famiglia di algoritmi**: ognuno sta in una classe propria, dietro la stessa interfaccia.
- Il codice che li usa conosce solo l'interfaccia. La strategia viene scelta dall'esterno e può cambiare senza toccare il resto.
- Vantaggi: niente catene di `if` o `switch` che crescono nel tempo. Una nuova strategia è una nuova classe (principio *open/closed*).
- In Spring ogni strategia è un bean (`@Component`). Il servizio riceve una `List` di interfaccia e sceglie quella giusta.

### Parametri

**Annotazioni** (per le strategie in Spring)

| Annotazione | Attributo | Significato |
|---|---|---|
| `@Component` | `value` | Nome del bean (default: nome della classe con iniziale minuscola) |
| `@Primary` | nessuno | Strategia predefinita quando ne viene richiesta una generica |
| `@Qualifier` | `value` | Sceglie il bean per nome durante l'iniezione |
| `@Order` | `value` | Ordine degli elementi quando si inietta una `List` (numeri bassi prima) |

**Interfaccia della strategia**: ha un solo metodo astratto, quindi può essere implementata anche con una lambda. L'annotazione `@FunctionalInterface` è facoltativa ma consigliata.

## Esempi teorici di utilizzo

- **Spese di spedizione:** standard, express o ritiro in negozio. Cambia solo il calcolo, non il flusso dell'ordine.
- **Metodo di pagamento:** carta, PayPal o bonifico, scelto dal cliente al momento dell'acquisto.
- **Sconti:** studente, over 65 o nessuno. Ogni regola diventa una classe separata.
- **Provider di intelligenza artificiale:** Groq o OpenAI, scelto dalla configurazione senza cambiare il codice che chiama il modello.
- **Compressione di file:** ZIP o GZIP, scelti a runtime dall'utente.

## Esempi di codice

### Base

Cosa mostra: un'interfaccia con due implementazioni, scelte dall'esterno.

```java
interface Sconto {
    double applica(double prezzo);               // ogni strategia implementa questo metodo
}

class ScontoStudente implements Sconto {
    public double applica(double prezzo) { return prezzo * 0.8; }   // 20% di sconto
}

class NessunoSconto implements Sconto {
    public double applica(double prezzo) { return prezzo; }
}

public class Negozio {
    public static void main(String[] args) {
        Sconto strategia = new ScontoStudente();     // scelta esterna della strategia
        System.out.println(strategia.applica(100));  // 80.0

        strategia = new NessunoSconto();             // cambio strategia: il resto non cambia
        System.out.println(strategia.applica(100));  // 100.0
    }
}
```

- `Sconto` è il contratto comune: `Negozio` non sa quale regola sta usando.
- Cambiare strategia significa cambiare il `new`, non il codice che chiama `applica`.
- Se la strategia ha un solo metodo, si può scrivere come lambda: `Sconto s = p -> p * 0.9;`.

### Intermedio

Cosa mostra: strategie come bean Spring. Il servizio le riceve tutte in una lista e le indicizza per nome. (Import omessi.)

```java
public interface Spedizione {
    String tipo();                               // chiave con cui si cerca la strategia
    double costo(double peso);
}

@Component
class Standard implements Spedizione {
    public String tipo() { return "standard"; }
    public double costo(double peso) { return 5 + peso * 0.5; }
}

@Component
class Express implements Spedizione {
    public String tipo() { return "express"; }
    public double costo(double peso) { return 12 + peso * 1.0; }
}

@Service
class CalcoloSpedizione {
    private final Map<String, Spedizione> strategie = new HashMap<>();

    // Spring inietta TUTTE le implementazioni di Spedizione in una lista
    CalcoloSpedizione(List<Spedizione> lista) {
        for (Spedizione s : lista) {
            strategie.put(s.tipo(), s);          // indicizza ogni strategia per nome
        }
    }

    double costo(String tipo, double peso) {
        Spedizione s = strategie.get(tipo);
        if (s == null) throw new IllegalArgumentException("Tipo sconosciuto: " + tipo);
        return s.costo(peso);
    }
}
```

- Ogni strategia è un `@Component`: Spring la trova e la registra da sola.
- `CalcoloSpedizione` non contiene nessun `if` sul tipo di spedizione.
- Per aggiungere una spedizione basta una nuova classe `@Component`.

### Avanzato

Cosa mostra: una catena di strategie con fallback. Spring le ordina con `@Order`, e la prima che si applica vince. (Import omessi.)

```java
public record Biglietto(int eta, boolean studente) { }

public interface Tariffa {
    boolean applicabile(Biglietto b);            // la strategia dichiara se si applica
    double prezzo(Biglietto b);
}

@Component
@Order(1)                                        // provata per prima
class TariffaStudente implements Tariffa {
    public boolean applicabile(Biglietto b) { return b.studente(); }
    public double prezzo(Biglietto b) { return 6.0; }
}

@Component
@Order(2)
class TariffaRidotta implements Tariffa {
    public boolean applicabile(Biglietto b) { return b.eta() < 14 || b.eta() >= 65; }
    public double prezzo(Biglietto b) { return 8.0; }
}

@Component
@Order(3)                                        // fallback: si applica sempre
class TariffaIntera implements Tariffa {
    public boolean applicabile(Biglietto b) { return true; }
    public double prezzo(Biglietto b) { return 12.0; }
}

@Service
class Botteghino {
    private final List<Tariffa> tariffe;         // Spring le inietta già in ordine di @Order

    Botteghino(List<Tariffa> tariffe) { this.tariffe = tariffe; }

    double prezzo(Biglietto b) {
        return tariffe.stream()
                .filter(t -> t.applicabile(b))   // prima strategia valida
                .findFirst()
                .orElseThrow()                   // il fallback garantisce che esista
                .prezzo(b);
    }
}
```

- `@Order` decide la priorità: numeri bassi vengono provati prima.
- Ogni strategia decide da sola se si applica (`applicabile`).
- La strategia di fallback senza condizioni evita il caso "nessuna tariffa trovata".

## Esercizi

### Facile

**Obiettivo:** scrivere un'interfaccia con due implementazioni e scegliere quella giusta dall'esterno.

**Consegna:** un parcheggio calcola il costo in due modi: tariffa oraria a 2 euro l'ora oppure tariffa giornaliera fissa a 10 euro. Scrivi l'interfaccia e le due strategie, poi stampa il costo di 5 ore con ciascuna.

### Medio

**Obiettivo:** usare una lista di strategie in Java puro, applicandole in sequenza.

**Consegna:** scrivi un'interfaccia `Formattazione` con il metodo `String applica(String testo)`. Crea tre strategie: tutto maiuscolo, ogni parola con la prima lettera maiuscola, testo al contrario. Applica tutte e tre alla stessa frase, una dopo l'altra, e stampa ogni risultato su una riga.

### Difficile

**Obiettivo:** usare Spring per scegliere una strategia tra più bean, con un fallback.

**Consegna:** un call center assegna una chiamata a un operatore con tre strategie: *meno occupato*, *a rotazione* e *per competenza*. Ogni strategia è un bean `@Component` con `@Order`. Il servizio `Assegnazione` riceve la lista e usa la prima strategia applicabile. Se nessun operatore è libero, la strategia di fallback deve restituire un messaggio di coda d'attesa e non lasciare il servizio senza risposta.
