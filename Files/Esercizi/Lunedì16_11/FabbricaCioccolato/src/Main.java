import enums.Aggiunta;
import enums.TipoCioccolato;
import factory.CioccolatoFactory;
import model.BaseCioccolato;
import model.Cioccolatino;
import model.CioccolatoCaldo;
import model.Tavoletta;
import service.Fabbrica;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Fabbrica fabbrica = new Fabbrica();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Scelta: ", 0, 5);

            switch (choice) {
                case 1:
                    produceCioccolatini();
                    break;
                case 2:
                    produceTavolette();
                    break;
                case 3:
                    produceCioccolatoCaldo();
                    break;
                case 4:
                    running = endDay();
                    break;
                case 5:
                    fabbrica.printHistory();
                    break;
                case 0:
                    running = false;
                    break;
            }

            if (running && fabbrica.isDayFinished()) {
                System.out.println("Cioccolato finito per oggi!");
                running = endDay();
            }
        }

        System.out.println("Fabbrica chiusa, arrivederci!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== FABBRICA DI CIOCCOLATO - " + fabbrica.getCurrentDate().format(Fabbrica.FORMAT) + " =====");
        System.out.println("Cioccolato rimasto oggi: " + fabbrica.getRemaining() + "/" + BaseCioccolato.MAX_PRODUCTION);
        System.out.println("1. Produci cioccolatini (costo " + Cioccolatino.COST + " l'uno)");
        System.out.println("2. Produci tavolette (costo da 4 a 24)");
        System.out.println("3. Produci cioccolato caldo (costo da 20 a 40)");
        System.out.println("4. Chiudi la giornata");
        System.out.println("5. Mostra lo storico");
        System.out.println("0. Esci");
    }

    private static void produceCioccolatini() {
        TipoCioccolato type = readType();
        Aggiunta additions = readAddition();
        int cocoa = readInt("Percentuale di cacao: ", 0, 100);
        String shape = readString("Forma: ");
        String filling = readString("Ripieno: ");
        int quantity = readInt("Quanti cioccolatini? ", 1, BaseCioccolato.MAX_PRODUCTION);

        List<BaseCioccolato> products = CioccolatoFactory.createCioccolatini(type, additions, cocoa, quantity, shape, filling);
        fabbrica.produce(products);
    }

    private static void produceTavolette() {
        TipoCioccolato type = readType();
        Aggiunta additions = readAddition();
        int cocoa = readInt("Percentuale di cacao: ", 0, 100);
        int size = readInt("Da quanti cioccolatini è fatta ogni tavoletta (2-12)? ",
                Tavoletta.MIN_CHOCOLATES, Tavoletta.MAX_CHOCOLATES);
        int quantity = readInt("Quante tavolette? ", 1, BaseCioccolato.MAX_PRODUCTION);

        List<BaseCioccolato> products = CioccolatoFactory.createTavolette(type, additions, cocoa, quantity, size);
        fabbrica.produce(products);
    }

    private static void produceCioccolatoCaldo() {
        TipoCioccolato type = readType();
        Aggiunta additions = readAddition();
        int cocoa = readInt("Percentuale di cacao: ", 0, 100);
        double temperature = readInt("Temperatura in °C: ", 0, 100);
        int density = readInt("Densità (1 liquida - 5 molto densa): ",
                CioccolatoCaldo.MIN_DENSITY, CioccolatoCaldo.MAX_DENSITY);
        int quantity = readInt("Quante cioccolate calde? ", 1, BaseCioccolato.MAX_PRODUCTION);

        List<BaseCioccolato> products = CioccolatoFactory.createCioccolatoCaldo(type, additions, cocoa, quantity, temperature, density);
        fabbrica.produce(products);
    }

    // chiude la giornata, stampa il resoconto e chiede se continuare
    private static boolean endDay() {
        fabbrica.closeDay();
        String answer = readString("Vuoi continuare con il giorno successivo? (s/n): ");
        return answer.equalsIgnoreCase("s");
    }

    // continua a chiedere finché il tipo non corrisponde all'enum
    private static TipoCioccolato readType() {
        while (true) {
            String input = readString("Tipo di cioccolato (fondente, latte, bianco): ");
            TipoCioccolato type = TipoCioccolato.fromString(input);
            if (type != null) {
                return type;
            }
            System.out.println("Tipo \"" + input + "\" non valido, riprova");
        }
    }

    // continua a chiedere finché l'aggiunta non corrisponde all'enum
    private static Aggiunta readAddition() {
        while (true) {
            String input = readString("Aggiunte (nocciole, mandorle, biscotti, nessuna): ");
            Aggiunta addition = Aggiunta.fromString(input);
            if (addition != null) {
                return addition;
            }
            System.out.println("Aggiunta \"" + input + "\" non valida, riprova");
        }
    }

    private static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    // continua a chiedere finché non viene inserito un numero valido
    private static int readInt(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Inserisci un numero tra " + min + " e " + max);
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido");
            }
        }
    }
}
