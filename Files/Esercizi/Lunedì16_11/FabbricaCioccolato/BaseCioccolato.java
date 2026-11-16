public abstract class BaseCioccolato {

    public static final int MAX_PRODUCTION = 100;

    private String type;
    private String additions;
    private int cocoaPercentage;

    public BaseCioccolato(String type, String additions, int cocoaPercentage) {
        this.type = type;
        this.additions = additions;
        this.cocoaPercentage = cocoaPercentage;
    }

    public void produce() {
        System.out.println("Cioccolato " + type + " al " + cocoaPercentage + "% di cacao");
    }

    public abstract int getCost();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdditions() {
        return additions;
    }

    public void setAdditions(String additions) {
        this.additions = additions;
    }

    public int getCocoaPercentage() {
        return cocoaPercentage;
    }

    public void setCocoaPercentage(int cocoaPercentage) {
        this.cocoaPercentage = cocoaPercentage;
    }
}
