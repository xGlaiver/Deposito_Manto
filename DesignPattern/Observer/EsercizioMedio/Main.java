package EsercizioMedio;

public class Main {
    public static void main(String[] args) {
        Palestra palestra = new Palestra();

        Observer log = new LogObserver();
        Observer reception = new ReceptionObserver();

        palestra.addObserver(log);
        palestra.addObserver(reception);

        palestra.registraIngresso("Marco", false);
        palestra.registraIngresso("Luigi", true);
        palestra.registraIngresso("Franco", false);

        palestra.removeObserver(reception);
        palestra.registraIngresso("Luca", true);


    }
}
