package Esercizio3;

public record Ordine(String cliente, String prodotto, int quantita, double prezzoUnitario) {
    double totale() { return quantita * prezzoUnitario; }
}
