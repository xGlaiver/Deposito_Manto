package EsercizioFacile;

public class Termostato {
    private static final Termostato INSTANCE = new Termostato();
    
    private double temperatura = 20.0;

    private Termostato() {}

    public static Termostato getInstance() {
        return INSTANCE;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getTemperatura() {
        return this.temperatura;
    }
}


