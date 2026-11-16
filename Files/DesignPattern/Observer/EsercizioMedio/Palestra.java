package EsercizioMedio;

import java.util.ArrayList;
import java.util.List;

public class Palestra implements Subject{
    List<Observer> osservatori = new ArrayList<>();

    @Override
    public void addObserver(Observer osservatore) {
        this.osservatori.add(osservatore);
    }

    @Override
    public void removeObserver(Observer osservatore) {
        this.osservatori.remove(osservatore);
    }

    @Override
    public void notifyObservers(IngressoSocio evento) {
        for(Observer osservatore: osservatori) {
            osservatore.update(evento);
        }
    }

    public void registraIngresso(String nome, boolean abbonamentoScaduto) {
        IngressoSocio evento = new IngressoSocio(nome, abbonamentoScaduto);
        notifyObservers(evento);
    }

    
    
}
