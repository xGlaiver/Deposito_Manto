package EsercizioFacile;

public class VeicoloFactory {
    public static Veicolo crea(String tipo) {
        switch (tipo) {
            case "auto":
                return new Auto();
            case "moto":
                return new Moto();
            default:
                throw new IllegalArgumentException("Tipo veicolo non esistente");
        }
    }
}
