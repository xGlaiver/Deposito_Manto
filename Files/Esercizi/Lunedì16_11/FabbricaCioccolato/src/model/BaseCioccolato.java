package model;

import enums.Aggiunta;
import enums.TipoCioccolato;

public abstract class BaseCioccolato {

    public static final int UNIT_COST = 2;

    private TipoCioccolato type;
    private Aggiunta additions;
    private int cocoaPercentage;

    public BaseCioccolato(TipoCioccolato type, Aggiunta additions, int cocoaPercentage) {
        this.type = type;
        this.additions = additions;
        this.cocoaPercentage = cocoaPercentage;
    }

    public void produce() {
        System.out.println("Cioccolato " + type + " al " + cocoaPercentage + "% di cacao");
    }

    public abstract int getCost();

    public TipoCioccolato getType() {
        return type;
    }

    public void setType(TipoCioccolato type) {
        this.type = type;
    }

    public Aggiunta getAdditions() {
        return additions;
    }

    public void setAdditions(Aggiunta additions) {
        this.additions = additions;
    }

    public int getCocoaPercentage() {
        return cocoaPercentage;
    }

    public void setCocoaPercentage(int cocoaPercentage) {
        this.cocoaPercentage = cocoaPercentage;
    }
}
