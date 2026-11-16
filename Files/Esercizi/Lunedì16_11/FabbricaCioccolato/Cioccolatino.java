public class Cioccolatino extends BaseCioccolato {

    public static final int COST = 2;

    private String shape;
    private String filling;

    public Cioccolatino(String type, String additions, int cocoaPercentage, String shape, String filling) {
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
        return COST;
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
