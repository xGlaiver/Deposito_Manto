package enums;

public enum Aggiunta {
    NOCCIOLE("nocciole"),
    MANDORLE("mandorle"),
    BISCOTTI("biscotti"),
    NESSUNA("nessuna");

    private String label;

    Aggiunta(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Aggiunta fromString(String text) {
        for (Aggiunta aggiunta : values()) {
            if (aggiunta.label.equalsIgnoreCase(text)) {
                return aggiunta;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return label;
    }
}
