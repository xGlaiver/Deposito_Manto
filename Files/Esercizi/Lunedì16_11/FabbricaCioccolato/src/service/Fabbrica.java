package service;

import model.BaseCioccolato;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Fabbrica {

    public static final int MAX_PRODUCTION = 100;
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocalDate currentDate = LocalDate.now();
    private int usedToday = 0;
    private List<BaseCioccolato> todayProduction = new ArrayList<>();
    private Map<LocalDate, List<BaseCioccolato>> history = new TreeMap<>();

    public boolean produce(List<BaseCioccolato> products) {
        int cost = getTotalCost(products);

        if (usedToday + cost > MAX_PRODUCTION) {
            System.out.println("Richiesta rifiutata: servono " + cost + " di cioccolato, ne restano " + getRemaining());
            return false;
        }

        for (BaseCioccolato product : products) {
            product.produce();
            System.out.println();
        }
        todayProduction.addAll(products);
        usedToday += cost;
        return true;
    }

    public boolean isDayFinished() {
        return getRemaining() < BaseCioccolato.UNIT_COST;
    }

    public void closeDay() {
        System.out.println(createReport(currentDate, todayProduction));
        history.put(currentDate, new ArrayList<>(todayProduction));

        currentDate = currentDate.plusDays(1);
        usedToday = 0;
        todayProduction.clear();
    }

    public void printHistory() {
        System.out.println("===== STORICO =====");
        for (Map.Entry<LocalDate, List<BaseCioccolato>> entry : history.entrySet()) {
            System.out.println(createReport(entry.getKey(), entry.getValue()));
        }
    }

    public List<BaseCioccolato> getProduction(LocalDate date) {
        return history.getOrDefault(date, new ArrayList<>());
    }

    private String createReport(LocalDate date, List<BaseCioccolato> products) {
        Map<String, Integer> count = new LinkedHashMap<>();
        for (BaseCioccolato product : products) {
            String name = product.getClass().getSimpleName();
            count.put(name, count.getOrDefault(name, 0) + 1);
        }

        String report = "--- Resoconto del " + date.format(FORMAT) + " ---\n";
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            report += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        report += "Cioccolato usato: " + getTotalCost(products) + "/" + MAX_PRODUCTION;
        return report;
    }

    private int getTotalCost(List<BaseCioccolato> products) {
        int cost = 0;
        for (BaseCioccolato product : products) {
            cost += product.getCost();
        }
        return cost;
    }

    public int getRemaining() {
        return MAX_PRODUCTION - usedToday;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public int getUsedToday() {
        return usedToday;
    }
}
