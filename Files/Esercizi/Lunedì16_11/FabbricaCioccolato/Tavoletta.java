public class Tavoletta extends BaseCioccolato {

    // costo da 4 a 24 -> da 2 a 12 cioccolatini da 2
    public static final int MIN_CHOCOLATES = 2;
    public static final int MAX_CHOCOLATES = 12;
    public static final int GRAMS_PER_CHOCOLATE = 10;

    private int numberOfChocolates;

    public Tavoletta(String type, String additions, int cocoaPercentage, int numberOfChocolates) {
        super(type, additions, cocoaPercentage);
        setNumberOfChocolates(numberOfChocolates);
    }

    public boolean hasAdditions() {
        return getAdditions() != null && !getAdditions().isEmpty() && !getAdditions().equals("nessuna");
    }

    @Override
    public void produce() {
        super.produce();
        if (hasAdditions()) {
            System.out.println("Tavoletta da " + getWeight() + "g con " + getAdditions());
        } else {
            System.out.println("Tavoletta da " + getWeight() + "g senza aggiunte");
        }
        System.out.println("Composta da " + numberOfChocolates + " cioccolatini");
        System.out.println("Cioccolato usato: " + getCost());
    }

    @Override
    public int getCost() {
        return numberOfChocolates * Cioccolatino.COST;
    }

    public int getWeight() {
        return numberOfChocolates * GRAMS_PER_CHOCOLATE;
    }

    public int getNumberOfChocolates() {
        return numberOfChocolates;
    }

    // tiene il numero sempre tra 2 e 12
    public void setNumberOfChocolates(int numberOfChocolates) {
        if (numberOfChocolates < MIN_CHOCOLATES) {
            this.numberOfChocolates = MIN_CHOCOLATES;
        } else if (numberOfChocolates > MAX_CHOCOLATES) {
            this.numberOfChocolates = MAX_CHOCOLATES;
        } else {
            this.numberOfChocolates = numberOfChocolates;
        }
    }
}
