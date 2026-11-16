package factory;

import enums.Aggiunta;
import enums.TipoCioccolato;
import model.BaseCioccolato;
import model.Cioccolatino;
import model.CioccolatoCaldo;
import model.Tavoletta;
import java.util.ArrayList;
import java.util.List;

public class CioccolatoFactory {

    public static List<BaseCioccolato> createCioccolatini(TipoCioccolato type, Aggiunta additions, int cocoaPercentage,
            int quantity, String shape, String filling) {
        List<BaseCioccolato> products = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            products.add(new Cioccolatino(type, additions, cocoaPercentage, shape, filling));
        }
        return products;
    }

    public static List<BaseCioccolato> createTavolette(TipoCioccolato type, Aggiunta additions, int cocoaPercentage,
            int quantity, int numberOfChocolates) {
        List<BaseCioccolato> products = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            products.add(new Tavoletta(type, additions, cocoaPercentage, numberOfChocolates));
        }
        return products;
    }

    public static List<BaseCioccolato> createCioccolatoCaldo(TipoCioccolato type, Aggiunta additions, int cocoaPercentage,
            int quantity, double temperature, int density) {
        List<BaseCioccolato> products = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            products.add(new CioccolatoCaldo(type, additions, cocoaPercentage, temperature, density));
        }
        return products;
    }
}
