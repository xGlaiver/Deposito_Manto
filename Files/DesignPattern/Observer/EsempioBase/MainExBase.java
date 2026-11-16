public class MainExBase {
    public static void main(String[] args) {
        Pubblicatore p = new Pubblicatore();
        p.iscrivi(e -> System.out.println("Email: " + e));
        p.iscrivi(e -> System.out.println("Log: " + e));
        p.pubblica("Ordine 42 creato");
        p.pubblica("Ordine 55 creato");

        p.iscrivi(e -> {
            System.out.println("info: " + e);
        });
        p.pubblica("Ordine 76 creato");

        p.iscriviInt(e -> System.out.println("evento: " + e));
        p.pubblica(10);
        // Email: Ordine 42 creato
        // Log: Ordine 42 creato
    }
}
