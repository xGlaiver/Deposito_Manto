# Collections in Java

## Spiegazione teorica

Una collection è un contenitore che gestisce più valori insieme. Le classi stanno nel package `java.util`.

- **Array**: dimensione fissa, decisa alla creazione. Può contenere anche tipi primitivi (`int`, `double`).
- **List** (`ArrayList`, `LinkedList`): sequenza ordinata, ammette duplicati, dimensione variabile.
- **Map** (`HashMap`, `LinkedHashMap`, `TreeMap`): coppie chiave → valore, con chiavi uniche.
- La scelta dipende da cosa fai più spesso: leggere per posizione, inserire in testa, cercare per chiave o mantenere un ordine.

### Confronto tra liste

| Struttura | Dimensione | Lettura per indice | Inserimento in testa | Note |
|---|---|---|---|---|
| Array | fissa | veloce | non previsto | `length` senza parentesi |
| `ArrayList` | variabile | veloce | lento (sposta gli elementi) | scelta predefinita |
| `LinkedList` | variabile | lenta (scorre la catena) | veloce | implementa anche `Deque` |

### Confronto tra mappe

| Implementazione | Ordine delle chiavi | Chiave `null` | Quando usarla |
|---|---|---|---|
| `HashMap` | nessun ordine garantito | sì (una) | default, più veloce |
| `LinkedHashMap` | ordine di primo inserimento | sì (una) | serve ricordare l'ordine di arrivo |
| `TreeMap` | ordinate per chiave (crescente) | no | serve ordine alfabetico o numerico |

### Metodi principali

#### Arrays (classe di utilità)

| Metodo | Argomenti | Effetto |
|---|---|---|
| `toString(arr)` | array | Stringa leggibile, es. `[28, 25, 30]` |
| `sort(arr)` | array | Ordina sul posto |
| `copyOf(arr, n)` | array, nuova lunghezza | Copia; se più lungo riempie con zeri |
| `fill(arr, v)` | array, valore | Riempie tutto con lo stesso valore |

#### List (`ArrayList` e `LinkedList`)

| Metodo | Argomenti | Effetto |
|---|---|---|
| `add(e)` | elemento | Aggiunge in fondo |
| `add(i, e)` | indice, elemento | Inserisce in posizione `i` |
| `get(i)` | indice | Legge l'elemento in posizione `i` |
| `set(i, e)` | indice, elemento | Sostituisce l'elemento in `i` |
| `remove(i)` / `remove(o)` | indice oppure oggetto | Rimuove per posizione o per valore |
| `contains(o)` | oggetto | `true` se presente |
| `size()` | nessuno | Numero di elementi |

Solo `LinkedList`: `addFirst(e)`, `addLast(e)`, `removeFirst()`, `removeLast()`.

#### Map

| Metodo | Argomenti | Effetto |
|---|---|---|
| `put(k, v)` | chiave, valore | Inserisce o sostituisce |
| `get(k)` | chiave | Valore, oppure `null` se manca |
| `getOrDefault(k, d)` | chiave, valore di default | Valore, oppure `d` se manca |
| `containsKey(k)` | chiave | `true` se presente |
| `remove(k)` | chiave | Rimuove la coppia |
| `merge(k, v, f)` | chiave, valore, funzione | Se manca inserisce `v`, altrimenti applica `f` |
| `computeIfAbsent(k, f)` | chiave, funzione | Crea il valore solo se la chiave manca |
| `keySet()` / `values()` / `entrySet()` | nessuno | Viste per scorrere la mappa |

Solo `TreeMap`: `firstKey()`, `lastKey()`, `headMap(k)`, `tailMap(k)`.

## Esempi teorici di utilizzo

- **Lista di ordini di un negozio.** Si scorre tutto l'elenco e si legge spesso per posizione: `ArrayList` è la scelta giusta.
- **Coda di stampa.** I documenti vengono stampati nell'ordine di arrivo: si aggiunge in fondo e si prende dalla testa. Serve una `LinkedList`.
- **Conteggio delle parole di un testo.** Il risultato deve mostrare le parole nell'ordine in cui compaiono: serve una `LinkedHashMap`.
- **Rubrica telefonica.** I contatti vanno sempre letti in ordine alfabetico: `TreeMap` mantiene l'ordine da sola.
- **Punteggi per giocatore in un gioco online.** Conta solo trovare il punteggio di un giocatore, non l'ordine: `HashMap` è la più veloce.
- **Griglia di un gioco di tris.** Le caselle sono sempre nove e non cambiano: un array è sufficiente.

## Esempi di codice

### Base

Cosa mostra: uso essenziale di array e `ArrayList`.

```java
import java.util.ArrayList;
import java.util.List;

public class ListaBase {
    public static void main(String[] args) {
        int[] voti = {28, 24, 30};                  // array: dimensione fissa, 3 elementi
        voti[1] = 25;                               // modifica per indice (0, 1, 2)

        List<String> studenti = new ArrayList<>();  // lista: dimensione variabile
        studenti.add("Anna");                       // aggiunge in fondo
        studenti.add("Luca");
        studenti.add(0, "Marta");                   // inserisce in posizione 0, gli altri scalano

        System.out.println(studenti.get(1));        // Anna
        System.out.println(studenti.size());        // 3
        System.out.println(voti.length);            // 3
    }
}
```

- L'array si legge con `length`, senza parentesi.
- La lista si legge con `size()`, con parentesi, perché è un metodo.
- `add(i, e)` sposta a destra gli elementi successivi.
- La variabile è dichiarata come `List`, cioè l'interfaccia: il tipo concreto può cambiare senza toccare il resto del codice.

### Intermedio

Cosa mostra: `LinkedList` usata come coda (FIFO).

```java
import java.util.LinkedList;

public class CodaStampa {
    public static void main(String[] args) {
        LinkedList<String> coda = new LinkedList<>();

        coda.addLast("Report Anna");                 // arrivo: in fondo alla coda
        coda.addLast("Fattura Luca");
        coda.addLast("Lettera Marta");

        while (!coda.isEmpty()) {                    // finché la coda non è vuota
            String documento = coda.removeFirst();   // esce dalla testa: primo arrivato, primo stampato
            System.out.println("Stampo: " + documento);
        }
    }
}
```

- `addLast` più `removeFirst` formano una coda FIFO (first in, first out).
- Rimuovere in testa a una `LinkedList` è veloce; in un `ArrayList` obbliga a spostare tutti gli elementi.
- `isEmpty()` evita `removeFirst()` su una lista vuota, che lancia `NoSuchElementException`.

### Avanzato

Cosa mostra: confronto tra `LinkedHashMap` e `TreeMap` sugli stessi dati.

```java
import java.util.*;

public class ConteggioParole {
    public static void main(String[] args) {
        String[] parole = {"spring", "java", "ai", "java", "boot", "spring", "java"};

        Map<String, Integer> ordineArrivo = new LinkedHashMap<>();    // ricorda l'ordine di primo inserimento
        TreeMap<String, Integer> ordineAlfabetico = new TreeMap<>();  // tiene le chiavi ordinate

        for (String p : parole) {
            ordineArrivo.merge(p, 1, Integer::sum);     // manca → inserisce 1; c'è → somma 1
            ordineAlfabetico.merge(p, 1, Integer::sum);
        }

        System.out.println(ordineArrivo);                // {spring=2, java=3, ai=1, boot=1}
        System.out.println(ordineAlfabetico);            // {ai=1, boot=1, java=3, spring=2}
        System.out.println(ordineAlfabetico.firstKey()); // ai
    }
}
```

- `LinkedHashMap` mostra le chiavi nell'ordine in cui sono comparse per la prima volta.
- `TreeMap` ordina le chiavi da sola, in ordine crescente per le stringhe.
- `Integer::sum` è un method reference: equivale alla lambda `(a, b) -> a + b`.
- `firstKey()` esiste solo su `TreeMap`: per questo la variabile è dichiarata come `TreeMap` e non come `Map`.

## Esercizi

### Facile

**Obiettivo:** usare un array e un ciclo per trovare il valore massimo di un insieme di dati.

**Consegna:** dato l'array delle temperature di una settimana (`double[] temperature = {12.5, 15.0, 9.8, 18.2, 14.0, 11.3, 16.7};`), stampa la temperatura più alta e il giorno in cui si è registrata, considerando l'indice `0` come lunedì.

### Medio

**Obiettivo:** usare `ArrayList` per modificare una lista nelle sue posizioni.

**Consegna:** crea una playlist con i brani `"Intro"`, `"Groove"`, `"Ballad"`, `"Finale"`. Poi rimuovi il brano in posizione 2, inserisci `"Sorpresa"` in testa alla lista e stampa la playlist finale con il numero di brani.

### Difficile

**Obiettivo:** scegliere la `Map` giusta in base al requisito, e motivare la scelta.

**Consegna:** un cinema registra gli incassi per ogni film, nell'ordine in cui arrivano i dati. Il direttore vuole due elenchi: uno in ordine alfabetico dei titoli e uno nell'ordine di arrivo dei dati. Implementa entrambi usando due `Map` diverse, e scrivi in due righe, come commento nel codice, quale `Map` hai scelto per ciascun elenco e perché.
