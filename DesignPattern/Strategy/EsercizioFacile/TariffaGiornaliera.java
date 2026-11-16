package EsercizioFacile;

public class TariffaGiornaliera implements Tariffa{

    @Override
    public int calcola(int ore) {
        return ((ore + 23) / 24) * 10;
    }
    
}
