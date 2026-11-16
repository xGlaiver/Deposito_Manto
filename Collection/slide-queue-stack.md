# Queue e Stack

## Spiegazione teorica

- Una **coda** (queue) segue la regola **FIFO**: *first in, first out*. Esce per primo chi è entrato per primo, come in fila alla cassa.
- Una **pila** (stack) segue la regola **LIFO**: *last in, first out*. Esce per primo l'ultimo elemento inserito, come una pila di piatti.
- In Java le due strutture si implementano con le interfacce `Queue` e `Deque` (*double-ended queue*, coda a doppia uscita).
- Una `Deque` si può usare come coda (inserisce in fondo, toglie dalla testa) oppure come pila (inserisce e toglie dalla cima).
- La classe `java.util.Stack` esiste ancora, ma è vecchia: oggi si usa `ArrayDeque` come pila.

### Parametri

**Metodi di `Queue` (coda)**: ogni operazione esiste in due versioni, che si comportano diversamente con la struttura vuota o piena.

| Operazione | Versione che lancia eccezione | Versione che restituisce `null` / `false` |
|---|---|---|
| Inserire in fondo | `add(e)` | `offer(e)` |
| Togliere dalla testa | `remove()` | `poll()` |
| Leggere la testa senza togliere | `element()` | `peek()` |

**Metodi di `Deque` (pila)**: quando la `Deque` è usata come pila si lavora sulla cima, cioè sulla testa.

| Metodo | Argomenti | Effetto |
|---|---|---|
| `push(e)` | elemento | Mette l'elemento in cima |
| `pop()` | nessuno | Toglie e restituisce la cima; lancia `NoSuchElementException` se vuota |
| `peek()` | nessuno | Restituisce la cima senza toglierla; `null` se vuota |
| `isEmpty()` | nessuno | `true` se non ci sono elementi |

**Implementazioni a confronto**

| Classe | Ordine | Accetta `null` | Note |
|---|---|---|---|
| `ArrayDeque` | FIFO come coda, LIFO come pila | no | scelta consigliata per code e pile |
| `LinkedList` | FIFO come coda, LIFO come pila | sì | implementa anche `List` |
| `PriorityQueue` | per priorità: esce sempre l'elemento più piccolo | no | non è FIFO; accenno, si approfondisce più avanti |
| `Stack` | LIFO | sì | classe legacy, sincronizzata; lancia `EmptyStackException` |

⚠️ Con `ArrayDeque` un `null` non è ammesso: lancia `NullPointerException`.

## Esempi teorici di utilizzo

- **Undo di un editor di testo:** ogni modifica viene messa in una pila; "annulla" toglie l'ultima modifica, cioè la cima.
- **Cronologia del browser:** le pagine visitate si impilano, e il tasto indietro torna alla cima precedente.
- **Chiamate di metodo:** la JVM usa uno stack di chiamate: l'ultimo metodo chiamato è il primo a terminare.
- **Richieste da elaborare in ordine:** un server riceve richieste e le gestisce nell'ordine di arrivo, usando una coda.
- **Messaggi di una chat:** i messaggi ricevuti vengono letti nell'ordine in cui sono arrivati, quindi serve una coda.
- **Verifica di parentesi in un'espressione:** l'ultima parentesi aperta è la prima che deve chiudersi, quindi serve una pila.

## Esempi di codice

### Base

Cosa mostra: una `Deque` usata come pila con `push`, `peek` e `pop`. (Import omessi.)

```java
Deque<String> pila = new ArrayDeque<>();    // pila: ArrayDeque usata con i metodi della Deque

pila.push("primo piatto");                  // push: mette in cima
pila.push("secondo piatto");
pila.push("terzo piatto");

System.out.println(pila.peek());            // terzo piatto: guarda la cima senza togliere
System.out.println(pila.pop());             // terzo piatto: toglie dalla cima (LIFO)
System.out.println(pila.pop());             // secondo piatto
System.out.println(pila.size());            // 1
```

- `push` e `pop` lavorano sempre sulla stessa estremità: la cima.
- L'ultimo elemento inserito è il primo a uscire.
- `peek` legge la cima senza modificare la pila.

### Intermedio

Cosa mostra: una `Queue` con `offer` e `poll`, che restituisce `null` quando la coda è vuota. (Import omessi.)

```java
Queue<String> coda = new ArrayDeque<>();    // coda: il tipo dichiarato è Queue

coda.offer("Ciao da Anna");                 // offer: aggiunge in fondo
coda.offer("Sei libero?");
coda.offer("Ti chiamo dopo");

String prossimo;
while ((prossimo = coda.poll()) != null) {  // poll: toglie dalla testa; null se la coda è vuota
    System.out.println("Letto: " + prossimo);
}

System.out.println(coda.poll());            // null: nessuna eccezione
```

- `offer` aggiunge in coda e `poll` toglie dalla testa: è FIFO.
- Il ciclo termina quando `poll` restituisce `null`, cioè quando la coda è vuota.
- Con `remove()` su una coda vuota otterremmo un'eccezione, non `null`.

### Avanzato

Cosa mostra: verifica di parentesi bilanciate con una pila. Ogni chiusura deve corrispondere all'ultima apertura. (Import omessi.)

```java
static boolean bilanciata(String testo) {
    Deque<Character> pila = new ArrayDeque<>();     // pila dei simboli aperti

    for (char c : testo.toCharArray()) {
        if (c == '(' || c == '[' || c == '{') {
            pila.push(c);                           // apertura: entra in pila
        } else if (c == ')' || c == ']' || c == '}') {
            if (pila.isEmpty()) return false;       // chiusura senza apertura
            char aperta = pila.pop();               // confronta con l'ultima apertura
            if (!corrisponde(aperta, c)) return false;
        }
    }
    return pila.isEmpty();                          // restano aperture senza chiusura?
}

static boolean corrisponde(char aperta, char chiusa) {
    return (aperta == '(' && chiusa == ')')
        || (aperta == '[' && chiusa == ']')
        || (aperta == '{' && chiusa == '}');
}

// bilanciata("{[()]}") → true
// bilanciata("([)]")   → false
// bilanciata("(((")    → false
```

- La pila garantisce che la chiusura corrisponda sempre all'apertura più recente.
- Una chiusura con la pila vuota significa che manca un'apertura.
- Se la pila non è vuota alla fine, qualche apertura non è stata chiusa.

## Esercizi

### Facile

**Obiettivo:** usare una pila per annullare l'ultima modifica di un editor.

**Consegna:** un editor registra ogni modifica del testo in una pila di stringhe. Simula tre modifiche: `"Ciao"`, `"Ciao mondo"`, `"Ciao mondo!"`. Poi esegui due annullamenti, ciascuno con `pop`, e stampa il testo corrente dopo ogni annullamento. Il testo corrente è la cima della pila.

### Medio

**Obiettivo:** usare una coda con `offer`, `poll` e `peek`, gestendo il caso di coda vuota.

**Consegna:** una chat riceve questi messaggi, in quest'ordine: `"Ci vediamo alle 18?"`, `"Porto i documenti"`, `"Grazie!"`. Inseriscili in una coda. Stampa il primo messaggio con `peek` senza rimuoverlo, poi leggi tutti i messaggi con `poll` uno alla volta. Quando la coda è vuota, stampa `"Nessun messaggio da leggere"`.

### Difficile

**Obiettivo:** valutare un'espressione in notazione polacca inversa (RPN) con una pila.

**Consegna:** scrivi un metodo che riceve un'espressione con i token separati da spazi, per esempio `"3 4 + 2 *"`, e restituisce il risultato. Ogni numero viene messo in pila. Ogni operatore (`+`, `-`, `*`, `/`) toglie due valori, applica l'operazione e rimette il risultato. Alla fine la pila deve contenere un solo valore. Gestisci due errori: un token non valido e un'espressione incompleta, cioè con meno di due valori per un operatore.
