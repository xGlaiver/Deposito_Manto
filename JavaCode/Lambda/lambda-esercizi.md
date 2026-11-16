# Lambda: 3 esercizi per gli studenti

## Esercizio 1 (Facile): lambda con zero, uno e più parametri

**Obiettivo:** scrivere lambda con un parametro e con due parametri, usando solo liste e array. Niente stream e niente interfacce funzionali da dichiarare o nominare.

**Compito:** in una classe `Esercizio1` con un `main` (con gli import necessari), scrivi tre lambda e usale:

1. **Un parametro:** crea `List<Integer> numeri = List.of(1, 2, 3)` e usa `forEach` con una lambda che stampa ogni numero moltiplicato per 2.
2. **Due parametri:** crea l'array `String[] parole = {"mondo", "a", "ciao"}` e ordinalo per lunghezza con `Arrays.sort(parole, (x, y) -> ...)`. Poi stampa `Arrays.toString(parole)`.


---

## Esercizio 2 (Medio): `Predicate`, `Comparator`, `Consumer`

**Obiettivo:** usare le tre interfacce funzionali con i metodi della lista (`forEach`, `removeIf`, `sort`), senza stream.

**Dati:**

```java
List<String> frutta = new ArrayList<>(
        List.of("kiwi", "banana", "mela", "ananas", "pera", "fico", "arancia"));
```

**Compito:**

1. Scrivi un `Consumer<String> stampa` che stampa la parola, e usalo con `forEach` per mostrare la lista iniziale.
2. Scrivi un `Predicate<String> corta` vera per le parole con **4 o meno** caratteri, e usalo con `removeIf`.
3. Ordina la lista con un `Comparator<String>`: prima le parole **più lunghe**, e a parità di lunghezza in **ordine alfabetico**.
4. Stampa di nuovo la lista con lo stesso `Consumer`, dopo una riga `---`.

**Suggerimento:** il `Comparator` si può scrivere come lambda a due parametri `(a, b) -> ...` che restituisce un numero negativo, zero o positivo, oppure con `Comparator.comparingInt(...)`.

---

## Esercizio 3 (Difficile): stream

**Compito:** con gli stream, ricava:

1. i **nomi dei prodotti** senza doppioni, in ordine alfabetico;
2. il **totale incassato** (somma dei `totale()` di tutti gli ordini);
3. i **clienti** con almeno un ordine da **più di 100 euro**, senza doppioni e in ordine alfabetico;
4. l'ordine con il totale più alto, come testo `cliente - prodotto`.

**Dati:**

```java
public record Ordine(String cliente, String prodotto, int quantita, double prezzoUnitario) {
    double totale() { return quantita * prezzoUnitario; }
}

List<Ordine> ordini = List.of(
        new Ordine("Anna", "Mouse", 2, 25.0),
        new Ordine("Luca", "Monitor", 1, 199.0),
        new Ordine("Anna", "Tastiera", 1, 45.0),
        new Ordine("Marta", "Mouse", 3, 25.0),
        new Ordine("Luca", "Webcam", 2, 60.0),
        new Ordine("Paolo", "Monitor", 2, 199.0));
```

