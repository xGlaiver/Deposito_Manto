package EsercizioFacile;

public class Main {
    public static void main(String[] args) {
        Veicolo auto = VeicoloFactory.crea("auto");
        Veicolo moto = VeicoloFactory.crea("moto");

        System.out.println("Ruote per l'auto: " + auto.ruote());
        System.out.println("Ruote per la moto: " + moto.ruote());
    }
}
