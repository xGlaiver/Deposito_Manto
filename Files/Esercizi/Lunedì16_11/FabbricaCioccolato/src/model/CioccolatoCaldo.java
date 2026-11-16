package model;

import enums.Aggiunta;
import enums.TipoCioccolato;

public class CioccolatoCaldo extends BaseCioccolato {

    public static final int MIN_COST = 20;
    public static final int COST_PER_DENSITY = 5;
    public static final int MIN_DENSITY = 1;
    public static final int MAX_DENSITY = 5;

    private double temperature;
    private int density;

    public CioccolatoCaldo(TipoCioccolato type, Aggiunta additions, int cocoaPercentage, double temperature, int density) {
        super(type, additions, cocoaPercentage);
        this.temperature = temperature;
        setDensity(density);
    }

    @Override
    public void produce() {
        super.produce();
        System.out.println("Cioccolato caldo a " + temperature + "°C con densità " + density);
        System.out.println("Cioccolato usato: " + getCost());
    }

    @Override
    public int getCost() {
        return MIN_COST + (density - MIN_DENSITY) * COST_PER_DENSITY;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        if (density < MIN_DENSITY) {
            this.density = MIN_DENSITY;
        } else if (density > MAX_DENSITY) {
            this.density = MAX_DENSITY;
        } else {
            this.density = density;
        }
    }
}
