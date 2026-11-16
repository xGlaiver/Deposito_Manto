# Lambda expression e programmazione funzionale

## Spiegazione

Una lambda è una funzione senza nome che si passa come se fosse un valore. Si può usare dove Java si aspetta un'**interfaccia funzionale**, cioè con un solo metodo astratto. Sostituisce le classi anonime e rende il codice molto più corto.

In Spring la incontri di continuo: `.user(u -> u.text(...))` nel `ChatClient`, il `RowMapper` di `JdbcTemplate`, `CommandLineRunner`.

## Parametri

### Sintassi

| Forma | Esempio |
| --- | --- |
| Un parametro | `s -> s.length()` |
| Più parametri | `(a, b) -> a + b` |
| Nessun parametro | `() -> "Ciao"` |
| Corpo a blocco | `(a, b) -> { int r = a + b; return r; }` |
| Method reference | `String::length`, `System.out::println`, `Studente::nome` |

### Interfacce funzionali principali (`java.util.function`)

| Interfaccia | Metodo | Firma | Quando |
| --- | --- | --- | --- |
| `Predicate<T>` | `test` | `T → boolean` | Condizione, filtro |
| `Function<T,R>` | `apply` | `T → R` | Trasformazione |
| `Consumer<T>` | `accept` | `T → void` | Azione su un valore (stampa, salva) |
| `Supplier<T>` | `get` | `() → T` | Produce un valore |
| `BiFunction<T,U,R>` | `apply` | `(T, U) → R` | Trasformazione con due input |
| `UnaryOperator<T>` | `apply` | `T → T` | Trasformazione dello stesso tipo |
| `BinaryOperator<T>` | `apply` | `(T, T) → T` | Combina due valori (somma, massimo) |
| `Comparator<T>` | `compare` | `(T, T) → int` | Ordinamento |
| `Runnable` | `run` | `() → void` | Azione senza input né output |

### Metodi che accettano lambda

| Metodo | Parametro | Effetto |
| --- | --- | --- |
| `list.forEach(...)` | `Consumer` | Esegue l'azione su ogni elemento |
| `list.removeIf(...)` | `Predicate` | Elimina gli elementi per cui è vera (la lista deve essere modificabile) |
| `list.sort(...)` | `Comparator` | Ordina la lista |
| `stream.filter(...)` | `Predicate` | Tiene solo gli elementi che passano |
| `stream.map(...)` | `Function` | Trasforma ogni elemento |
| `stream.sorted(...)` | `Comparator` | Ordina il flusso |
| `stream.reduce(zero, ...)` | `BinaryOperator` | Riduce il flusso a un solo valore |
| `map.computeIfAbsent(k, ...)` | `Function` | Crea il valore se la chiave manca |

⚠️ Una lambda può usare le variabili locali del metodo solo se sono **effectively final**, cioè se non vengono mai riassegnate.

## Esempio base

```java
import java.util.*;
import java.util.function.*;

public class LambdaBase {
    public static void main(String[] args) {
        // Prima di Java 8: classe anonima (verboso)
        Comparator<String> vecchio = new Comparator<String>() {
            @Override
            public int compare(String a, String b) { return a.length() - b.length(); }
        };

        // Con la lambda: stessa cosa in una riga
        Comparator<String> perLunghezza = (a, b) -> a.length() - b.length();

        List<String> nomi = new ArrayList<>(List.of("Anna", "Giuseppe", "Io", "Marta"));
        nomi.sort(perLunghezza);
        System.out.println(nomi);                       // [Io, Anna, Marta, Giuseppe]

        Predicate<String> lungo = s -> s.length() > 4;
        Function<String, Integer> lunghezza = String::length;       // method reference
        Consumer<String> stampa = s -> System.out.println("Nome: " + s);
        Supplier<String> saluto = () -> "Ciao!";

        System.out.println(lungo.test("Marta"));        // true
        System.out.println(lunghezza.apply("Anna"));    // 4
        System.out.println(saluto.get());               // Ciao!

        nomi.forEach(stampa);                           // Nome: Io / Nome: Anna / ...
        nomi.removeIf(lungo);                           // toglie Marta e Giuseppe
        System.out.println(nomi);                       // [Io, Anna]
    }
}
```

Si usa `new ArrayList<>(...)` perché `List.of(...)` restituisce una lista **immutabile**, su cui `sort` e `removeIf` lanciano `UnsupportedOperationException`.

## Esempio medio

Stream di dati con filtro, ordinamento, raggruppamento, una variabile catturata e un'interfaccia funzionale scritta da noi.

`Prodotto.java`

```java
public record Prodotto(String nome, String categoria, double prezzo) { }
```

`LambdaMedio.java`

```java
import java.util.*;
import java.util.stream.Collectors;

public class LambdaMedio {

    @FunctionalInterface                       // il compilatore verifica che il metodo astratto sia uno solo
    interface Regola {
        boolean verifica(Prodotto p);
    }

    static List<Prodotto> filtra(List<Prodotto> lista, Regola regola) {
        List<Prodotto> risultato = new ArrayList<>();
        for (Prodotto p : lista) {
            if (regola.verifica(p)) risultato.add(p);
        }
        return risultato;
    }

    public static void main(String[] args) {
        List<Prodotto> prodotti = List.of(
                new Prodotto("Mouse", "periferiche", 25.0),
                new Prodotto("Tastiera", "periferiche", 45.0),
                new Prodotto("Monitor", "schermi", 199.0),
                new Prodotto("Webcam", "periferiche", 60.0),
                new Prodotto("TV", "schermi", 450.0));

        // 1) Pipeline: filtra → ordina → trasforma → raccogli
        List<String> economici = prodotti.stream()
                .filter(p -> p.prezzo() < 100)
                .sorted(Comparator.comparingDouble(Prodotto::prezzo))
                .map(Prodotto::nome)
                .toList();
        System.out.println(economici);          // [Mouse, Tastiera, Webcam]

        // 2) Somma dei prezzi
        double totale = prodotti.stream().mapToDouble(Prodotto::prezzo).sum();
        System.out.println(totale);             // 779.0

        // 3) Raggruppa per categoria e conta (l'ordine delle chiavi non è garantito)
        Map<String, Long> perCategoria = prodotti.stream()
                .collect(Collectors.groupingBy(Prodotto::categoria, Collectors.counting()));
        System.out.println(perCategoria);       // {periferiche=3, schermi=2}

        // 4) Variabile catturata: deve restare effectively final
        double sconto = 10;
        prodotti.stream()
                .filter(p -> p.prezzo() > 100)
                .forEach(p -> System.out.println(p.nome() + " scontato: " + (p.prezzo() - sconto)));
        // Monitor scontato: 189.0
        // TV scontato: 440.0
        // ❌ Se dopo scrivessi sconto = 20; il codice non compilerebbe

        // 5) La nostra interfaccia funzionale
        List<Prodotto> schermi = filtra(prodotti, p -> p.categoria().equals("schermi"));
        System.out.println(schermi.size());     // 2
    }
}
```

### Collegamento con Spring

Le stesse idee compaiono nelle dispense di Spring AI e di Spring JDBC:

```java
// Consumer: configura il prompt dentro la lambda
chatClient.prompt().user(u -> u.text("Spiega {argomento}").param("argomento", x))

// Function (RowMapper): come leggere una riga del database
jdbc.query("SELECT name FROM product", (rs, riga) -> rs.getString("name"));
```

## Esercizi per gli studenti

### Facile

**Obiettivo:** usare `filter` e `map` con una lambda.

**Dati:** `List<Integer> numeri = List.of(3, 8, 12, 5, 20, 7);`

**Compito:** stampa solo i numeri **pari**, ciascuno **raddoppiato**, uno per riga, usando uno stream e `forEach`.

**Risultato atteso:**

```
16
24
40
```

### Medio

**Obiettivo:** usare record, method reference, `sorted` e un valore aggregato.

**Dati:** `record Studente(String nome, int voto) {}` con questi cinque studenti: Anna 28, Luca 24, Marta 30, Paolo 18, Sara 27.

**Compito:**

1. Crea la lista dei **nomi** degli studenti con voto **maggiore o uguale a 27**, in **ordine alfabetico**.
2. Calcola la **media dei voti** di tutti gli studenti.

**Risultato atteso:**

```
[Anna, Marta, Sara]
25.4
```

### Difficile

**Obiettivo:** scrivere un'interfaccia funzionale e usare le lambda come strategie intercambiabili.

**Dati:** crea l'interfaccia `Operazione` con il metodo `int esegui(int a, int b)`. Crea una `Map<String, Operazione>` con le quattro operazioni `"+"`, `"-"`, `"*"`, `"/"`, ciascuna scritta con una lambda. Scrivi un metodo `static int calcola(String simbolo, int a, int b)` che cerca l'operazione nella mappa e la esegue.

**Compito:**

- Se il simbolo non è nella mappa, lancia `IllegalArgumentException("Operatore sconosciuto: " + simbolo)`.
- Se la divisione ha il secondo operando uguale a zero, lancia `IllegalArgumentException("Divisione per zero")`.
- Prova il metodo con le chiamate nella tabella.

**Risultato atteso:**

| Chiamata | Risultato |
| --- | --- |
| `calcola("+", 12, 4)` | `16` |
| `calcola("-", 12, 4)` | `8` |
| `calcola("*", 12, 4)` | `48` |
| `calcola("/", 12, 4)` | `3` |
| `calcola("/", 5, 0)` | eccezione `Divisione per zero` |
| `calcola("%", 5, 2)` | eccezione `Operatore sconosciuto: %` |

---

## Soluzioni (solo per il docente)

### Facile

```java
List<Integer> numeri = List.of(3, 8, 12, 5, 20, 7);

numeri.stream()
      .filter(n -> n % 2 == 0)
      .map(n -> n * 2)
      .forEach(System.out::println);
```

### Medio

```java
record Studente(String nome, int voto) { }

List<Studente> studenti = List.of(
        new Studente("Anna", 28), new Studente("Luca", 24), new Studente("Marta", 30),
        new Studente("Paolo", 18), new Studente("Sara", 27));

List<String> bravi = studenti.stream()
        .filter(s -> s.voto() >= 27)
        .map(Studente::nome)
        .sorted()
        .toList();

double media = studenti.stream()
        .mapToInt(Studente::voto)
        .average()
        .orElse(0);

System.out.println(bravi);   // [Anna, Marta, Sara]
System.out.println(media);   // 25.4
```

### Difficile

```java
import java.util.Map;

public class Calcolatrice {

    @FunctionalInterface
    interface Operazione {
        int esegui(int a, int b);
    }

    private static final Map<String, Operazione> OPERAZIONI = Map.of(
            "+", (a, b) -> a + b,
            "-", (a, b) -> a - b,
            "*", (a, b) -> a * b,
            "/", (a, b) -> {
                if (b == 0) throw new IllegalArgumentException("Divisione per zero");
                return a / b;
            });

    static int calcola(String simbolo, int a, int b) {
        Operazione op = OPERAZIONI.get(simbolo);
        if (op == null) {
            throw new IllegalArgumentException("Operatore sconosciuto: " + simbolo);
        }
        return op.esegui(a, b);
    }

    public static void main(String[] args) {
        System.out.println(calcola("+", 12, 4));   // 16
        System.out.println(calcola("/", 12, 4));   // 3
        System.out.println(calcola("/", 5, 0));    // eccezione: Divisione per zero
    }
}
```

### Errori frequenti da segnalare agli studenti

- **Facile:** scrivere `n / 2 == 0` al posto di `n % 2 == 0`. `%` è il resto, `/` è la divisione.
- **Medio:** dimenticare `.toList()` o `.sorted()` e stampare l'oggetto stream, che mostra un riferimento (`java.util.stream...`) al posto dei dati.
- **Difficile:** il caso `"%"` restituisce `null` dalla mappa. Senza il controllo, l'errore sarebbe un `NullPointerException` poco leggibile.

Il Difficile riprende il pattern **Strategy** (vedi il documento sui Design pattern): ogni lambda è una strategia scelta per nome.
