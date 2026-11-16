package EsercizioMedio;

public interface Subject {
    void addObserver(Observer osservatore);
    void removeObserver(Observer osservatore);
    void notifyObservers(IngressoSocio evento);
}
