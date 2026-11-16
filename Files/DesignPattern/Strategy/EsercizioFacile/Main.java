package EsercizioFacile;

public class Main {
    public static void main(String[] args) {

        Tariffa tariffaOraria = new TariffaOraria();
        Tariffa tariffaGiornaliera = new TariffaGiornaliera();

        System.out.println("Tariffa Oraria: " + tariffaOraria.calcola(5));
        System.out.println("Tariffa Giornaliera: " + tariffaGiornaliera.calcola(5));

    }
}
