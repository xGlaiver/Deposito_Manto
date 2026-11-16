package EsercizioFacile;

public class Main {
    public static void main(String[] args) {
        Termostato termostato = Termostato.getInstance();

        termostato.setTemperatura(28.3);

        System.out.println("Temperatura: " + termostato.getTemperatura());
    }
}
