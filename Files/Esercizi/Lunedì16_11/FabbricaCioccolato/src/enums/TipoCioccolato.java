package enums;

public enum TipoCioccolato {
    FONDENTE("fondente"),
    LATTE("al latte"),
    BIANCO("bianco");

    private String label;

    TipoCioccolato(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TipoCioccolato fromString(String text) {
        for (TipoCioccolato tipo : values()) {
            if (tipo.name().equalsIgnoreCase(text) || tipo.label.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return label;
    }
}
