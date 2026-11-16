package EsercizioMedio;

/**
 * Distributore
 */
public enum Distributore {
    INSTANCE;
    
    private double credito;

    public synchronized void inserisci(double credito) {
        this.credito += credito;
    }

    public synchronized boolean eroga(double prezzo) {
        if(prezzo > this.credito) return false;
        this.credito -= prezzo;
        return true;
    } 
}