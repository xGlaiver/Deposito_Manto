package model;

import enums.Aggiunta;
import enums.TipoCioccolato;

public class Cioccolatino extends BaseCioccolato {

    private String shape;
    private String filling;

    public Cioccolatino(TipoCioccolato type, Aggiunta additions, int cocoaPercentage, String shape, String filling) {
        super(type, additions, cocoaPercentage);
        this.shape = shape;
        this.filling = filling;
    }

    @Override
    public void produce() {
        super.produce();
        System.out.println("Cioccolatino a forma di " + shape + " ripieno di " + filling);
        System.out.println("Cioccolato usato: " + getCost());
    }

    @Override
    public int getCost() {
        return UNIT_COST;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getFilling() {
        return filling;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }
}
