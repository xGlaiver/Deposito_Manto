import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class Pubblicatore {
    private final List<Consumer<String>> osservatori = new ArrayList<>();
    private final List<Consumer<Integer>> obsInt = new ArrayList<>();

    void iscrivi(Consumer<String> o) {           // chi vuole ricevere eventi si iscrive
        osservatori.add(o);
    }

    void iscriviInt(Consumer<Integer> o){
        obsInt.add(o);
    }

    void pubblica(String evento) {
        for (Consumer<String> o : osservatori) { // avvisa tutti, uno dopo l'altro
            o.accept(evento);
        }
    }

    void pubblica(Integer evento){
        obsInt.forEach(o -> o.accept(evento));
    }
}