package EsercizioFacile;

public class TariffaOraria implements Tariffa {

    @Override
    public int calcola(int ore) {
        return ore * 2;
    }
    
}
