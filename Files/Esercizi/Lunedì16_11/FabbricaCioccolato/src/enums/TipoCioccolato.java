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

    // cerca il tipo dal testo scritto, accetta sia "latte" che "al latte"; null se non esiste
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
