import java.util.ArrayList;
import java.util.List;

public class Magazzino {
    private static final Magazzino INSTANCE = new Magazzino();

    private final List<Prodotto> prodotti = new ArrayList<>();

    private Magazzino() {}

    public static Magazzino getInstance(){
        return INSTANCE;
    }

    List<Prodotto> elencoCopia(){
        return List.copyOf(prodotti);
    }

    void aggiungi (Prodotto p){
        prodotti.add(p);
    }
}
