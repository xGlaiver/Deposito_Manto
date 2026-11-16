import java.util.ArrayList;
import java.util.List;

public class Termometro implements SerraSubject {

    List<DispositivoObserver> dispositiviObserver = new ArrayList<>();
    double temperatura;

    @Override
    public void iscrivi(DispositivoObserver dispositivoObserver) {
        this.dispositiviObserver.add(dispositivoObserver);
    }

    @Override
    public void disiscrivi(DispositivoObserver dispositivoObserver) {
        this.dispositiviObserver.remove(dispositivoObserver);
    }

    @Override
    public void notificaTutti() {
       for(DispositivoObserver dispositivo : dispositiviObserver) {
            dispositivo.avvisa(this.temperatura);
       }
    }

    public void registra(double temperatura) {
        this.temperatura = temperatura;
        notificaTutti();
    }

    public double getTemperatura() {
        return this.temperatura;
    }
    
}
