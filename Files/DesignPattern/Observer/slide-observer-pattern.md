# Observer Pattern

## Spiegazione teorica

- Un **soggetto** (publisher) avvisa tutti gli **osservatori** (listener) quando succede qualcosa.
- Il soggetto non conosce gli osservatori: non sa chi sono né quanti sono. Ognuno si iscrive da solo.
- Vantaggio: aggiungere una reazione a un evento non richiede di modificare chi lo genera.
- In Spring si pubblica un evento con `ApplicationEventPublisher` e si ricevono gli eventi con `@EventListener`.
- Di default i listener sono **sincroni**: girano nello stesso thread di chi pubblica. Un'eccezione in un listener interrompe anche chi ha pubblicato.

### Parametri

**Annotazioni e metodi Spring**

| Elemento | Attributo | Significato |
|---|---|---|
| `@EventListener` | `classes` / `value` | Tipi di evento ascoltati (default: il tipo del parametro) |
| `@EventListener` | `condition` | Espressione SpEL: il metodo parte solo se è vera, es. `"#e.importo > 100"` |
| `@TransactionalEventListener` | `phase` | Quando scatta: `AFTER_COMMIT` (default), `BEFORE_COMMIT`, `AFTER_ROLLBACK`, `AFTER_COMPLETION` |
| `@TransactionalEventListener` | `fallbackExecution` | Se `true`, scatta anche senza transazione attiva |
| `@Async` | nessuno | Esegue il listener in un altro thread (serve `@EnableAsync`) |
| `ApplicationEventPublisher` | `publishEvent(Object)` | Pubblica un oggetto qualsiasi come evento |

## Esempi teorici di utilizzo

- **Ordine creato:** un listener invia la conferma via email, un altro aggiorna le statistiche. Chi crea l'ordine non sa di nessuno dei due.
- **Utente registrato:** dopo il salvataggio si invia il benvenuto. Lo si fa solo se la transazione è andata a buon fine.
- **Prodotto modificato:** la cache viene svuotata per quel prodotto, senza che il servizio prodotti la conosca.
- **Audit:** ogni modifica importante genera un evento, e un listener lo scrive nel registro delle operazioni.
- **Non usare per:** operazioni che devono avvenire nello stesso flusso e con esito garantito, come il calcolo di un totale da restituire subito.

## Esempi di codice

### Base

Cosa mostra: un publisher che tiene la lista degli osservatori, in Java puro, con lambda come osservatori.

```java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class Pubblicatore {
    private final List<Consumer<String>> osservatori = new ArrayList<>();

    void iscrivi(Consumer<String> o) {           // chi vuole ricevere eventi si iscrive
        osservatori.add(o);
    }

    void pubblica(String evento) {
        for (Consumer<String> o : osservatori) { // avvisa tutti, uno dopo l'altro
            o.accept(evento);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Pubblicatore p = new Pubblicatore();
        p.iscrivi(e -> System.out.println("Email: " + e));
        p.iscrivi(e -> System.out.println("Log: " + e));
        p.pubblica("Ordine 42 creato");
        // Email: Ordine 42 creato
        // Log: Ordine 42 creato
    }
}
```

- `iscrivi` è il punto in cui un osservatore si registra.
- `pubblica` non sa cosa fanno gli osservatori: chiama solo `accept`.
- Aggiungere un osservatore non cambia né `Pubblicatore` né il codice che pubblica.

### Intermedio

Cosa mostra: evento come `record` in Spring, con un publisher e due listener indipendenti. (Import omessi.)

```java
public record OrdineCreato(String id, double totale) { }   // l'evento è un record qualsiasi

@Service
class OrdineService {
    private final ApplicationEventPublisher publisher;

    OrdineService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    void crea(String id, double totale) {
        System.out.println("Ordine " + id + " salvato");
        publisher.publishEvent(new OrdineCreato(id, totale));   // non sa chi ascolta
    }
}

@Component
class EmailConferma {
    @EventListener                               // riceve ogni OrdineCreato
    void su(OrdineCreato e) {
        System.out.println("Email di conferma per " + e.id());
    }
}

@Component
class Statistiche {
    @EventListener
    void su(OrdineCreato e) {
        System.out.println("Statistiche aggiornate: +" + e.totale());
    }
}
```

- `OrdineService` pubblica l'evento e basta: aggiungere un listener non lo tocca.
- Ogni metodo `@EventListener` riceve l'evento per tipo del parametro.
- Il publisher resta sincrono: se `EmailConferma` lancia un'eccezione, `crea(...)` fallisce.

### Avanzato

Cosa mostra: listener con `condition` SpEL e listener che scatta solo dopo il commit della transazione. (Import omessi.)

```java
public record UtenteRegistrato(String email, String piano) { }

@Service
class RegistrazioneService {
    private final ApplicationEventPublisher publisher;

    RegistrazioneService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Transactional
    public void registra(String email, String piano) {
        // qui il salvataggio nel database
        publisher.publishEvent(new UtenteRegistrato(email, piano));
    }
}

@Component
class BenvenutoEmail {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)   // solo se il salvataggio è riuscito
    void su(UtenteRegistrato e) {
        System.out.println("Benvenuto, " + e.email());
    }
}

@Component
class OffertaPremium {
    @EventListener(condition = "#e.piano() == 'premium'")   // scatta solo per il piano premium
    void su(UtenteRegistrato e) {
        System.out.println("Offerta riservata a " + e.email());
    }
}
```

- `@TransactionalEventListener` non parte se la transazione va in rollback: niente email a utenti non salvati.
- `condition` filtra gli eventi con SpEL. Qui il listener riceve solo i piani `premium`.
- Il metodo `registra` è `public` e viene chiamato da un altro bean, così il proxy di `@Transactional` funziona.

### Extra - Subject

Cosa mostra: un'interfaccia `Subject` affiancata a `Observer`. La classe concreta `NewsLetter` implementa `Subject` e avvisa i propri osservatori quando cambia il topic; `NewsApplication` è un osservatore concreto che reagisce all'aggiornamento.

```java
import java.util.ArrayList;
import java.util.List;

interface Observer{
    void update(String topic);
}

interface Subject{
    void addObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObservers();
}

//Concrete Subject
class NewsLetter implements Subject{
    private List<Observer> observers = new ArrayList<>();
    private String topic = "";
    
    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update(topic);
        }
    }

    public void setTopic(String newTopic){
        this.topic = newTopic;
        notifyObservers();
    }
}

//Concrete Observers
class NewsApplication implements Observer{
    private String topic;

    @Override
    public void update(String topic){
        this.topic = topic;
        display();
    }

    private void display(){
        System.out.println("App News: Topic updated - " + topic);
    }
}

//Utilizzo
public class Main{
    public static void main(String[] args) {
        NewsLetter newsLetter = new NewsLetter();
        Observer newsApp = new NewsApplication();

        newsLetter.addObserver(newsApp);

        newsLetter.setTopic("Politica");
    }
}
```

- `Subject` e `Observer` sono interfacce: `NewsLetter` conosce solo `Observer`, non la classe `NewsApplication`.
- `addObserver` e `removeObserver` gestiscono le iscrizioni. Un nuovo osservatore si aggiunge senza modificare `NewsLetter`.
- `setTopic` salva il nuovo topic e chiama `notifyObservers()`, quindi ogni osservatore iscritto riceve l'aggiornamento.

## Esercizi

### Facile

**Obiettivo:** implementare un publisher con lista di osservatori in Java puro.

**Consegna:** una serra ha un termometro che, a ogni nuova lettura, avvisa due dispositivi: un ventilatore, che si accende sopra 28 gradi, e un display, che mostra sempre il valore. Scrivi la classe `Termometro` con `iscrivi(...)` e `registra(double temperatura)`. Gli osservatori sono lambda. Registra tre letture: 25, 29 e 27 gradi.

### Medio

**Obiettivo:** implementare `Subject` e `Observer` in Java puro, e filtrare gli eventi dentro un osservatore.

**Consegna:** una palestra registra ogni ingresso di un socio. Crea l'interfaccia `Observer`, con il metodo `update(IngressoSocio evento)`, e l'interfaccia `Subject`, con `addObserver`, `removeObserver` e `notifyObservers(IngressoSocio evento)`. L'evento è il record `IngressoSocio(String nome, boolean abbonamentoScaduto)`. Scrivi la classe `Palestra` che implementa `Subject` e ha il metodo `registraIngresso(String nome, boolean abbonamentoScaduto)`: crea l'evento e avvisa gli osservatori. Scrivi due osservatori concreti: `Log`, che stampa ogni ingresso, e `Reception`, che stampa un avviso solo se l'abbonamento è scaduto (il filtro si fa con un `if` dentro `update`). Nel `main` iscrivi entrambi e registra tre ingressi: un socio regolare, un socio con abbonamento scaduto, un altro socio regolare. Poi rimuovi `Reception` con `removeObserver` e registra un altro socio con abbonamento scaduto: deve comparire solo il messaggio di `Log`.

### Difficile

**Obiettivo:** eseguire un listener in un altro thread con `@Async`.

**Consegna:** un impianto di allarme di un magazzino pubblica l'evento `Intrusione(String zona)`. Un listener invia una notifica sul telefono del responsabile e deve essere asincrono, così la registrazione dell'evento non si blocca. Un secondo listener, sincrono, scrive nel registro della zona. Abilita l'elaborazione asincrona con `@EnableAsync` e stampa il nome del thread in ogni listener, per dimostrare che il primo è diverso dal secondo.
